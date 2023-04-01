package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record PiglinlikeStopFleeing(MemoryModuleType<? extends LivingEntity> targetMemory, PredicateMaker<LivingEntity> primaryTargetPredicate, BiPredicateMaker<LivingEntity, LivingEntity> secondaryTargetPredicate) implements PredicateMaker<LivingEntity> {

    private static boolean wantsToStopFleeing(LivingEntity entity, MemoryModuleType<? extends LivingEntity> targetMemory, Predicate<LivingEntity> primaryTargetPredicate, BiPredicate<LivingEntity, LivingEntity> secondaryTargetPredicate) {
        Brain<?> brain = entity.getBrain();
        if (!brain.hasMemoryValue(targetMemory)) {
            return true;
        } else {
            //noinspection OptionalGetWithoutIsPresent
            LivingEntity target = brain.getMemory(targetMemory).get();
            if (primaryTargetPredicate.test(target)) {
                return true;
            } else return secondaryTargetPredicate.test(entity, target);
        }
    }

    @Override
    public Predicate<LivingEntity> make() {
        return le -> wantsToStopFleeing(le, this.targetMemory, this.primaryTargetPredicate.make(), this.secondaryTargetPredicate.make());
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return null;
    }
}
