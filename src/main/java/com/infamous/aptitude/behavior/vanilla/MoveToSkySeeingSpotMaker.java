package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.MoveToSkySeeingSpot;

public record MoveToSkySeeingSpotMaker(float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return MoveToSkySeeingSpot.create(this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOVE_TO_SKY_SEEING_SPOT.get();
    }
}
