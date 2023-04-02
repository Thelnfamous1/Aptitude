package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.function.Predicate;

public record EntityIsNearEntityMemory(MemoryModuleType<LivingEntity> entityMemory, int closeEnough) implements PredicateMaker<LivingEntity> {

    private static boolean check(LivingEntity entity, MemoryModuleType<LivingEntity> entityMemory, int closeEnough) {
        Brain<?> brain = entity.getBrain();
        if (brain.hasMemoryValue(entityMemory)) {
            LivingEntity otherEntity = brain.getMemory(entityMemory).get();
            return entity.closerThan(otherEntity, closeEnough);
        } else {
            return false;
        }
    }

    @Override
    public Predicate<LivingEntity> make() {
        return le -> check(le, this.entityMemory, this.closeEnough);
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return AptitudePredicateMakers.ENTITY_IS_NEAR_ENTITY_MEMORY.get();
    }
}
