package com.infamous.aptitude.behavior.vanilla.inventory_carrier;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GoAndGiveItemsToTarget;
import net.minecraft.world.entity.ai.behavior.PositionTracker;

import java.util.Optional;

public record GoAndGiveItemsToTargetMaker(FunctionMaker<LivingEntity, Optional<PositionTracker>> targetPositionGetter, float speedModifier, int duration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new GoAndGiveItemsToTarget<>(this.targetPositionGetter.make(), this.speedModifier, this.duration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.GO_AND_GIVE_ITEMS_TO_TARGET.get();
    }
}
