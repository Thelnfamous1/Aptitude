package com.infamous.aptitude.logic.function;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.Function;

public record GetTargetFlyPosition(int maxXZDistance, int maxYDistance, int yOffset) implements FunctionMaker<PathfinderMob, Vec3> {
    private static final int MAX_XZ_DIST = 10;
    private static final int MAX_Y_DIST = 7;

    public static GetTargetFlyPosition simple(){
        return new GetTargetFlyPosition(MAX_XZ_DIST, MAX_Y_DIST, -2);
    }

    @Nullable
    private static Vec3 getTargetFlyPos(PathfinderMob pathfinderMob, int maxXZDistance, int maxYDistance, int yOffset) {
        Vec3 viewVector = pathfinderMob.getViewVector(0.0F);
        return AirAndWaterRandomPos.getPos(pathfinderMob, maxXZDistance, maxYDistance, yOffset, viewVector.x, viewVector.z, (float)Math.PI / 2F);
    }

    @Override
    public Function<PathfinderMob, Vec3> make() {
        return pf -> getTargetFlyPos(pf, this.maxXZDistance, this.maxYDistance, this.yOffset);
    }

    @Override
    public Codec<? extends FunctionMaker<PathfinderMob, Vec3>> getCodec() {
        return null;
    }
}
