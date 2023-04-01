package com.infamous.aptitude.behavior.vanilla;

import com.google.common.collect.ImmutableMap;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.List;
import java.util.Map;

public record RunOneMaker(Map<MemoryModuleType<?>, MemoryStatus> entryCondition, List<Pair<? extends BehaviorMaker, Integer>> weightedBehaviors) implements BehaviorMaker {


    public static RunOneMaker simple(List<Pair<? extends BehaviorMaker, Integer>> weightedBehaviors) {
        return new RunOneMaker(ImmutableMap.of(), weightedBehaviors);
    }
    @Override
    public BehaviorControl<?> make() {
        return new RunOne<>(this.entryCondition, BehaviorMaker.makeWeightedBehaviors(this.weightedBehaviors));
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.RUN_ONE.get();
    }
}
