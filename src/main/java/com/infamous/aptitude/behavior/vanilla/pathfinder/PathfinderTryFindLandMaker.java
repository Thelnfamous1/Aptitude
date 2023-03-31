package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.TryFindLand;

public record PathfinderTryFindLandMaker(int maxDistance, float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return TryFindLand.create(this.maxDistance, this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_TRY_FIND_LAND.get();
    }
}
