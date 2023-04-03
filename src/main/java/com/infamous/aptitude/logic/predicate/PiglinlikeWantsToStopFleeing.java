package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.bipredicate.EntityIsValueMemoryValue;
import com.infamous.aptitude.logic.bipredicate.util.AllOfBiPredicateMaker;
import com.infamous.aptitude.logic.bipredicate.util.PredicateSecond;
import com.infamous.aptitude.logic.predicate.utility.AllOfPredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.AnyOfPredicateMaker;
import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record PiglinlikeWantsToStopFleeing(MemoryModuleType<? extends LivingEntity> targetMemory, PredicateMaker<LivingEntity> targetPredicate, BiPredicateMaker<LivingEntity, LivingEntity> fallbackEntityTargetBiPredicate) implements PredicateMaker<LivingEntity> {

    public static PiglinlikeWantsToStopFleeing piglin() {
        return new PiglinlikeWantsToStopFleeing(
                MemoryModuleType.AVOID_TARGET,
                isTargetHoglinAndOutnumberedByHoglins(),
                isTargetZombifiedAndNearestVisibleZombified());
    }

    private static PredicateMaker<LivingEntity> isTargetHoglinAndOutnumberedByHoglins() {
        return new AllOfPredicateMaker(
                List.of(
                        new EntityIsEntityType(EntityType.HOGLIN),
                        new EntityIsOutnumbered(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT))
        );
    }

    private static BiPredicateMaker<LivingEntity, LivingEntity> isTargetZombifiedAndNearestVisibleZombified() {
        return new AllOfBiPredicateMaker(
                List.of(
                        new PredicateSecond<>(
                                new AnyOfPredicateMaker<>(List.of(
                                        new EntityIsEntityType(EntityType.ZOMBIFIED_PIGLIN),
                                        new EntityIsEntityType(EntityType.ZOGLIN)))),
                        new EntityIsValueMemoryValue<>(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED)
                )
        );
    }

    private static boolean wantsToStopFleeing(LivingEntity entity, MemoryModuleType<? extends LivingEntity> targetMemory, Predicate<LivingEntity> targetPredicate, BiPredicate<LivingEntity, LivingEntity> fallbackEntityTargetBipredicate) {
        Brain<?> brain = entity.getBrain();
        if (!brain.hasMemoryValue(targetMemory)) {
            return true;
        } else {
            //noinspection OptionalGetWithoutIsPresent
            LivingEntity target = brain.getMemory(targetMemory).get();
            if (targetPredicate.test(target)) {
                return true;
            } else return fallbackEntityTargetBipredicate.test(entity, target);
        }
    }

    @Override
    public Predicate<LivingEntity> make() {
        return le -> wantsToStopFleeing(le, this.targetMemory, this.targetPredicate.make(), this.fallbackEntityTargetBiPredicate.make());
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return AptitudePredicateMakers.PIGLINLIKE_WANTS_TO_STOP_FLEEING.get();
    }
}
