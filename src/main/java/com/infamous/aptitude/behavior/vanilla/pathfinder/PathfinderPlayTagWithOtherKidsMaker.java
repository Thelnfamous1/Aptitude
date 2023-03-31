package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.PlayTagWithOtherKids;

public record PathfinderPlayTagWithOtherKidsMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return PlayTagWithOtherKids.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_PLAY_TAG_WITH_OTHER_KIDS.get();
    }
}
