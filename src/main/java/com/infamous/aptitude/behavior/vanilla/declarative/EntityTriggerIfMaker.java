package com.infamous.aptitude.behavior.vanilla.declarative;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;

public record EntityTriggerIfMaker<E extends LivingEntity>(PredicateMaker<E> entityPredicate) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return BehaviorBuilder.triggerIf(this.entityPredicate.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.ENTITY_TRIGGER_IF.get();
    }
}
