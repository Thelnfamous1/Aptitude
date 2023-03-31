package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.TryFindLandNearWater;

public record PathfinderTryFindLandNearWaterMaker(int maxDistance, float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return TryFindLandNearWater.create(this.maxDistance, this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_TRY_FIND_LAND_NEAR_WATER.get();
    }
}
