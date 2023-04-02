package com.infamous.aptitude.logic.bipredicate;

import com.infamous.aptitude.registry.AptitudeBiPredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.function.BiPredicate;

public record EntityIsValueMemoryValue<U>(MemoryModuleType<U> memory) implements BiPredicateMaker<LivingEntity, U> {
    @Override
    public BiPredicate<LivingEntity, U> make() {
        return (le, u) -> le.getBrain().isMemoryValue(this.memory, u);
    }

    @Override
    public Codec<? extends BiPredicateMaker<LivingEntity, U>> getCodec() {
        return (Codec<? extends BiPredicateMaker<LivingEntity, U>>) (Codec<?>) AptitudeBiPredicateMakers.ENTITY_IS_VALUE_MEMORY_VALUE.get();
    }
}
