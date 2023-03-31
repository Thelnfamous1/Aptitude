package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;

public record PathfinderFollowTemptationMaker(FunctionMaker<LivingEntity, Float> speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new FollowTemptation(this.speedModifier.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_FOLLOW_TEMPTATION.get();
    }
}
