package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.VillageBoundRandomStroll;

public record PathfinderVillageBoundRandomStrollMaker(float speedModifier, int maxXZDistance, int maxYDistance) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return VillageBoundRandomStroll.create(this.speedModifier, this.maxXZDistance, this.maxYDistance);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_VILLAGE_BOUND_RANDOM_STROLL.get();
    }
}
