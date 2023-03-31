package com.infamous.aptitude.logic.bipredicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;

import java.util.Optional;
import java.util.function.BiPredicate;

public record OptionalResultOfFirstIsSecond<T, U, R>(FunctionMaker<T, Optional<R>> function) implements BiPredicateMaker<T, U> {
    @Override
    public BiPredicate<T, U> make() {
        return (o1, o2) -> this.function.make().apply(o1).filter(r -> r == o2).isPresent();
    }

    @Override
    public Codec<? extends BiPredicateMaker<T, U>> getCodec() {
        return null;
    }
}
