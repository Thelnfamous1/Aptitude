package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.mixin.RandomStrollAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.phys.Vec3;

public record PathfinderRandomStrollMaker(float speedModifier, FunctionMaker<PathfinderMob, Vec3> positionFinder, PredicateMaker<PathfinderMob> selfPredicate) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return RandomStrollAccessor.callStrollFlyOrSwim(this.speedModifier, this.positionFinder.make(), this.selfPredicate.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_RANDOM_STROLL.get();
    }
}
