package com.infamous.aptitude.logic.function.util;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;

import java.util.Optional;
import java.util.function.Function;

public record ToOptionalOrOptional<T, R>(FunctionMaker<T, Optional<R>> primary, FunctionMaker<T, Optional<R>> secondary) implements FunctionMaker<T, Optional<R>> {
    @Override
    public Function<T, Optional<R>> make() {
        return o -> this.primary.make().apply(o).or(() -> this.secondary.make().apply(o));
    }

    @Override
    public Codec<? extends FunctionMaker<T, Optional<R>>> getCodec() {
        return null;
    }
}
