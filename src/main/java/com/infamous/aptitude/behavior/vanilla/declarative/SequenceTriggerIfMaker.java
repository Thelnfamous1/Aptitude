package com.infamous.aptitude.behavior.vanilla.declarative;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;

public record SequenceTriggerIfMaker<E extends LivingEntity>(PredicateMaker<E> predicate, BehaviorMaker oneShot) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return BehaviorBuilder.triggerIf(this.predicate.make(), BehaviorMaker.makeOneShot(this.oneShot));
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SEQUENCE_TRIGGER_IF.get();
    }
}
