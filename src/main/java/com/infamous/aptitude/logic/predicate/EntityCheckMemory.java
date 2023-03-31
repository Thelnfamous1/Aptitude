package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.function.Predicate;

public record EntityCheckMemory(MemoryModuleType<?> memory, MemoryStatus status) implements PredicateMaker<LivingEntity> {
    @Override
    public Predicate<LivingEntity> make() {
        return le -> le.getBrain().checkMemory(this.memory, this.status);
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return null;
    }
}
