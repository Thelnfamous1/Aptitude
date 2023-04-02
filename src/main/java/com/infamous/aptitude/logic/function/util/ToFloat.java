package com.infamous.aptitude.logic.function.util;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.registry.AptitudeFunctionMakers;
import com.mojang.serialization.Codec;

import java.util.function.Function;

public record ToFloat<T>(float value) implements FunctionMaker<T, Float> {
    @Override
    public Function<T, Float> make() {
        return o -> this.value;
    }

    @Override
    public Codec<? extends FunctionMaker<T, Float>> getCodec() {
        return (Codec<? extends FunctionMaker<T, Float>>) (Codec<?>) AptitudeFunctionMakers.TO_FLOAT.get();
    }
}
