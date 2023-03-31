package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.CopyMemoryWithExpiry;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record CopyMemoryWithExpiryMaker<E extends LivingEntity, T>(PredicateMaker<E> copyIf, MemoryModuleType<? extends T> from, MemoryModuleType<T> to, UniformInt expiry) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return CopyMemoryWithExpiry.create(this.copyIf.make(), this.from, this.to, this.expiry);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.COPY_MEMORY_WITH_EXPIRY.get();
    }
}
