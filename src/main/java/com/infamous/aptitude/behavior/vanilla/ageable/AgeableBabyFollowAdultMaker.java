package com.infamous.aptitude.behavior.vanilla.ageable;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

public record AgeableBabyFollowAdultMaker(UniformInt followRange, FunctionMaker<LivingEntity, Float> speedModifier) implements BehaviorMaker {

    @Override
    public BehaviorControl<?> make() {
        return BabyFollowAdult.create(this.followRange, this.speedModifier.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.AGEABLE_BABY_FOLLOW_ADULT.get();
    }
}
