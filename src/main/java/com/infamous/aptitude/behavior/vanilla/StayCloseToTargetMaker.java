package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;

import java.util.Optional;

public record StayCloseToTargetMaker(FunctionMaker<LivingEntity, Optional<PositionTracker>> targetPositionTrackerGetter, int closeEnough, int tooFar, float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StayCloseToTarget.create(this.targetPositionTrackerGetter.make(), this.closeEnough, this.tooFar, this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.STAY_CLOSE_TO_TARGET.get();
    }
}
