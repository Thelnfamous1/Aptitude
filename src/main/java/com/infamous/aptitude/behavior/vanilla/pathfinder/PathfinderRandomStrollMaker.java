package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.function.GetRandomLandPosition;
import com.infamous.aptitude.logic.function.GetTargetFlyPosition;
import com.infamous.aptitude.logic.function.GetTargetSwimPosition;
import com.infamous.aptitude.logic.predicate.EntityIsInWaterOrBubble;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.AlwaysTrue;
import com.infamous.aptitude.logic.predicate.utility.Negate;
import com.infamous.aptitude.mixin.RandomStrollAccessor;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.phys.Vec3;

public record PathfinderRandomStrollMaker(float speedModifier, FunctionMaker<PathfinderMob, Vec3> positionFinder, PredicateMaker<PathfinderMob> selfPredicate) implements BehaviorMaker {

    private static final int MAX_XZ_DIST = 10;
    private static final int MAX_Y_DIST = 7;
    private static final int[][] SWIM_XY_DISTANCE_TIERS = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {10, 7}};

    public static PathfinderRandomStrollMaker stroll(float p_260304_) {
        return stroll(p_260304_, true);
    }

    public static PathfinderRandomStrollMaker stroll(float p_260303_, boolean p_259639_) {
        return new PathfinderRandomStrollMaker(p_260303_, new GetRandomLandPosition(MAX_XZ_DIST, MAX_Y_DIST), p_259639_ ? new AlwaysTrue<>() : new Negate(new EntityIsInWaterOrBubble()));
    }

    public static PathfinderRandomStrollMaker stroll(float speedModifier, int maxXZDist, int maxYDist) {
        return new PathfinderRandomStrollMaker(speedModifier, new GetRandomLandPosition(maxXZDist, maxYDist), new AlwaysTrue<>());
    }

    public static PathfinderRandomStrollMaker fly(float speedModifier) {
        return new PathfinderRandomStrollMaker(speedModifier, new GetTargetFlyPosition(MAX_XZ_DIST, MAX_Y_DIST, -2), new AlwaysTrue<>());
    }

    public static PathfinderRandomStrollMaker swim(float speedModifier) {
        return new PathfinderRandomStrollMaker(speedModifier, new GetTargetSwimPosition(SWIM_XY_DISTANCE_TIERS), (PredicateMaker<PathfinderMob>) (Object) new EntityIsInWaterOrBubble());
    }

    @Override
    public BehaviorControl<?> make() {
        return RandomStrollAccessor.callStrollFlyOrSwim(this.speedModifier, this.positionFinder.make(), this.selfPredicate.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_RANDOM_STROLL.get();
    }
}
