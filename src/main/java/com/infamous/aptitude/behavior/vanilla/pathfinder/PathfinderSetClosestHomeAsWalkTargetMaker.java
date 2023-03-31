package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetClosestHomeAsWalkTarget;

public record PathfinderSetClosestHomeAsWalkTargetMaker(float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetClosestHomeAsWalkTarget.create(this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_SET_CLOSEST_HOME_AS_WALK_TARGET.get();
    }
}
