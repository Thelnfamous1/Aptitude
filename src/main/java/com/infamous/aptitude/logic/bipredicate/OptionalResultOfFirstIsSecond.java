package com.infamous.aptitude.logic.bipredicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.registry.AptitudeBiPredicateMakers;
import com.mojang.serialization.Codec;

import java.util.Optional;
import java.util.function.BiPredicate;

public record OptionalResultOfFirstIsSecond<T, U, R>(FunctionMaker<T, Optional<R>> optionalGetter) implements BiPredicateMaker<T, U> {
    @Override
    public BiPredicate<T, U> make() {
        return (o1, o2) -> this.optionalGetter.make().apply(o1).filter(r -> r == o2).isPresent();
    }

    @Override
    public Codec<? extends BiPredicateMaker<T, U>> getCodec() {
        return (Codec<? extends BiPredicateMaker<T, U>>) (Codec<?>) AptitudeBiPredicateMakers.OPTIONAL_RESULT_OF_FIRST_IS_SECOND.get();
    }
}
