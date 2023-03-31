package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;

public record GoToWantedItemMaker<E extends LivingEntity>(PredicateMaker<E> selfPredicate, float speedModifier, boolean overrideWalkTarget, int closeEnough) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return GoToWantedItem.create(this.selfPredicate.make(), this.speedModifier, this.overrideWalkTarget, this.closeEnough);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.GO_TO_WANTED_ITEM.get();
    }
}
