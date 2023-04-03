package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.codec.WeightedBehavior;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.TriggerGate;

import java.util.List;

public record TriggerGateMaker(List<WeightedBehavior> weightedTriggers, GateBehavior.OrderPolicy orderPolicy, GateBehavior.RunningPolicy runningPolicy) implements BehaviorMaker {

    public static TriggerGateMaker triggerOneShuffled(List<WeightedBehavior> weightedTriggers) {
        return new TriggerGateMaker(weightedTriggers, GateBehavior.OrderPolicy.SHUFFLED, GateBehavior.RunningPolicy.RUN_ONE);
    }

    @Override
    public BehaviorControl<?> make() {
        return TriggerGate.triggerGate(BehaviorMaker.makeWeightedTriggers(this.weightedTriggers), this.orderPolicy, this.runningPolicy);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.TRIGGER_GATE.get();
    }
}
