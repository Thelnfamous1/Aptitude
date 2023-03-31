package com.infamous.aptitude.logic.function.util;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public record ToFloat<T>(float value) implements FunctionMaker<T, Float> {
    @Override
    public Function<T, Float> make() {
        return o -> this.value;
    }

    @Override
    public Codec<? extends FunctionMaker<T, Float>> getCodec() {
        return null;
    }
}
