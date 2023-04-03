package com.infamous.aptitude.logic.function;

import com.infamous.aptitude.registry.AptitudeFunctionMakers;
import com.mojang.serialization.Codec;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public class BottomCenterOfBlockPosition implements FunctionMaker<Vec3i, Vec3> {
    @Override
    public Function<Vec3i, Vec3> make() {
        return Vec3::atBottomCenterOf;
    }

    @Override
    public Codec<? extends FunctionMaker<Vec3i, Vec3>> getCodec() {
        return AptitudeFunctionMakers.BOTTOM_CENTER_OF_BLOCK_POSITION.get();
    }
}
