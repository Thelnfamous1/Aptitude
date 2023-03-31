package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record EraseMemoryIfMaker<E extends LivingEntity>(PredicateMaker<E> eraseIf, MemoryModuleType<?> memory) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return EraseMemoryIf.create(this.eraseIf.make(), this.memory);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.ERASE_MEMORY_IF.get();
    }
}
