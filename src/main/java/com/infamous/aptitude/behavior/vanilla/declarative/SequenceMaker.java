package com.infamous.aptitude.behavior.vanilla.declarative;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;

public record SequenceMaker(BehaviorMaker conditionTrigger, BehaviorMaker resultTrigger) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return BehaviorBuilder.sequence(BehaviorMaker.makeTrigger(this.conditionTrigger), BehaviorMaker.makeTrigger(this.resultTrigger));
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SEQUENCE.get();
    }
}
