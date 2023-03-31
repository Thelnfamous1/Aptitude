package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.mixin.SetEntityLookTargetSometimesAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

public record SetEntityLookTargetSometimesMaker(float maxDistance, UniformInt interval, PredicateMaker<LivingEntity> targetPredicate) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetEntityLookTargetSometimesAccessor.callCreate(this.maxDistance, this.interval, this.targetPredicate.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SET_ENTITY_LOOK_TARGET_SOMETIMES.get();
    }
}
