package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BecomePassiveIfMemoryPresent;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record BecomePassiveIfMemoryPresentMaker(MemoryModuleType<?> memory, int duration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return BecomePassiveIfMemoryPresent.create(this.memory, this.duration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.BECOME_PASSIVE_IF_MEMORY_PRESENT.get();
    }
}
