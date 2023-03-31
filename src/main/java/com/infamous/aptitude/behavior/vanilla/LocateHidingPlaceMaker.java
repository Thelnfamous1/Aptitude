package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LocateHidingPlace;

public record LocateHidingPlaceMaker(int range, float speedModifier, int closeEnough) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return LocateHidingPlace.create(this.range, this.speedModifier, this.closeEnough);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.LOCATE_HIDING_PLACE.get();
    }
}
