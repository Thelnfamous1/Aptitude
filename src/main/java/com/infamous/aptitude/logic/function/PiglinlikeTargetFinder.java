package com.infamous.aptitude.logic.function;

import com.infamous.aptitude.logic.predicate.EntityIsNearEntityMemory;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.registry.AptitudeFunctionMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

public record PiglinlikeTargetFinder(PredicateMaker<LivingEntity> selfPredicate, MemoryModuleType<UUID> primaryTargetUuidMemory, MemoryModuleType<Boolean> secondaryTargetEnabledMemory, MemoryModuleType<? extends LivingEntity> secondaryTargetMemory, MemoryModuleType<? extends LivingEntity> tertiaryTargetMemory, MemoryModuleType<? extends LivingEntity> quaternaryTargetMemory) implements FunctionMaker<LivingEntity, Optional<? extends LivingEntity>> {

    private static final int DESIRED_DISTANCE_FROM_ZOMBIFIED = 6;
    public static PiglinlikeTargetFinder piglin(){
        return new PiglinlikeTargetFinder(isNearZombified(), MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
    }

    private static EntityIsNearEntityMemory isNearZombified() {
        return new EntityIsNearEntityMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, DESIRED_DISTANCE_FROM_ZOMBIFIED);
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(LivingEntity entity, Predicate<LivingEntity> selfPredicate, MemoryModuleType<UUID> primaryTargetUUIDMemory, MemoryModuleType<Boolean> secondaryTargetEnabledMemory, MemoryModuleType<? extends LivingEntity> secondaryTargetMemory, MemoryModuleType<? extends LivingEntity> tertiaryTargetMemory, MemoryModuleType<? extends LivingEntity> quaternaryTargetMemory) {
        Brain<?> brain = entity.getBrain();
        if (selfPredicate.test(entity)) {
            return Optional.empty();
        } else {
            Optional<LivingEntity> primaryTarget = BehaviorUtils.getLivingEntityFromUUIDMemory(entity, primaryTargetUUIDMemory);
            if (primaryTarget.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(entity, primaryTarget.get())) {
                return primaryTarget;
            } else {
                if (brain.hasMemoryValue(secondaryTargetEnabledMemory)) {
                    Optional<? extends LivingEntity> secondaryTarget = brain.getMemory(secondaryTargetMemory);
                    if (secondaryTarget.isPresent()) {
                        return secondaryTarget;
                    }
                }

                Optional<? extends LivingEntity> tertiaryTarget = brain.getMemory(tertiaryTargetMemory);
                if (tertiaryTarget.isPresent()) {
                    return tertiaryTarget;
                } else {
                    Optional<? extends LivingEntity> quaternaryTarget = brain.getMemory(quaternaryTargetMemory);
                    return quaternaryTarget.isPresent() && Sensor.isEntityAttackable(entity, quaternaryTarget.get()) ? quaternaryTarget : Optional.empty();
                }
            }
        }
    }

    @Override
    public Function<LivingEntity, Optional<? extends LivingEntity>> make() {
        return le -> findNearestValidAttackTarget(le, this.selfPredicate.make(), this.primaryTargetUuidMemory, this.secondaryTargetEnabledMemory, this.secondaryTargetMemory, this.tertiaryTargetMemory, this.quaternaryTargetMemory);
    }

    @Override
    public Codec<? extends FunctionMaker<LivingEntity, Optional<? extends LivingEntity>>> getCodec() {
        return AptitudeFunctionMakers.PIGLINLIKE_TARGET_FINDER.get();
    }
}
