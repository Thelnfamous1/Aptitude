package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.function.Predicate;

public record EntityIsOutnumbered(MemoryModuleType<Integer> allyCountMemory, MemoryModuleType<Integer> enemyCountMemory) implements PredicateMaker<LivingEntity> {

    private static boolean hoglinsOutnumberPiglins(LivingEntity entity, MemoryModuleType<Integer> allyCountMemory, MemoryModuleType<Integer> enemyCountMemory) {
        int allyCount = entity.getBrain().getMemory(allyCountMemory).orElse(0) + 1;
        int enemyCount = entity.getBrain().getMemory(enemyCountMemory).orElse(0);
        return enemyCount > allyCount;
    }

    @Override
    public Predicate<LivingEntity> make() {
        return e -> hoglinsOutnumberPiglins(e, this.allyCountMemory, this.enemyCountMemory);
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return null;
    }
}
