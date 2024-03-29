package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.codec.WeightedBehavior;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record GateBehaviorMaker(Map<MemoryModuleType<?>, MemoryStatus> entryCondition, Set<MemoryModuleType<?>> exitErasedMemories, GateBehavior.OrderPolicy orderPolicy, GateBehavior.RunningPolicy runningPolicy, List<WeightedBehavior> weightedBehaviors) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new GateBehavior<>(this.entryCondition, this.exitErasedMemories, this.orderPolicy, this.runningPolicy, BehaviorMaker.makeWeightedBehaviors(this.weightedBehaviors));
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.GATE_BEHAVIOR.get();
    }
}
