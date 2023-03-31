package com.infamous.aptitude.logic.function;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.Function;

public record EntityGetEntityMemory(MemoryModuleType<? extends LivingEntity> entityMemory) implements FunctionMaker<LivingEntity, Optional<? extends LivingEntity>> {
    @Override
    public Function<LivingEntity, Optional<? extends LivingEntity>> make() {
        return le -> le.getBrain().getMemory(this.entityMemory);
    }

    @Override
    public Codec<? extends FunctionMaker<LivingEntity, Optional<? extends LivingEntity>>> getCodec() {
        return null;
    }
}
