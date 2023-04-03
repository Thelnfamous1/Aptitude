package com.infamous.aptitude.logic.function;

import com.infamous.aptitude.registry.AptitudeFunctionMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public record GetRandomLandPosition(int maxXZDistance, int maxYDistance) implements FunctionMaker<PathfinderMob, Vec3> {
    @Override
    public Function<PathfinderMob, Vec3> make() {
        return pf -> LandRandomPos.getPos(pf, this.maxXZDistance, this.maxYDistance);
    }

    @Override
    public Codec<? extends FunctionMaker<PathfinderMob, Vec3>> getCodec() {
        return AptitudeFunctionMakers.GET_RANDOM_LAND_POSITION.get();
    }
}
