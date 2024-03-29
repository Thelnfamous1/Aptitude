package com.infamous.aptitude.logic.function;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public record PiglinBrutelikeTargetFinder(MemoryModuleType<UUID> primaryTargetUuidMemory, MemoryModuleType<? extends LivingEntity> secondaryTargetMemory, int secondaryTargetCloseEnough, MemoryModuleType<? extends LivingEntity> tertiaryTargetMemory) implements FunctionMaker<LivingEntity, Optional<? extends LivingEntity>> {

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(LivingEntity entity, MemoryModuleType<UUID> primaryTargetUuidMemory, MemoryModuleType<? extends LivingEntity> entityMemory, int closeEnough, MemoryModuleType<? extends LivingEntity> tertiaryTargetMemory) {
        Optional<LivingEntity> primaryTarget = BehaviorUtils.getLivingEntityFromUUIDMemory(entity, primaryTargetUuidMemory);
        if (primaryTarget.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(entity, primaryTarget.get())) {
            return primaryTarget;
        } else {
            Optional<? extends LivingEntity> secondaryTarget = entity.getBrain().getMemory(entityMemory).filter((e) -> e.closerThan(entity, closeEnough));
            return secondaryTarget.isPresent() ? secondaryTarget : entity.getBrain().getMemory(tertiaryTargetMemory);
        }
    }

    @Override
    public Function<LivingEntity, Optional<? extends LivingEntity>> make() {
        return le -> findNearestValidAttackTarget(le, this.primaryTargetUuidMemory, this.secondaryTargetMemory, this.secondaryTargetCloseEnough, this.tertiaryTargetMemory);
    }

    @Override
    public Codec<? extends FunctionMaker<LivingEntity, Optional<? extends LivingEntity>>> getCodec() {
        return null;
    }
}
