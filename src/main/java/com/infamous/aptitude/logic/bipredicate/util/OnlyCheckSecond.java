package com.infamous.aptitude.logic.bipredicate.util;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

import java.util.function.BiPredicate;

public record OnlyCheckSecond<T, U>(PredicateMaker<U> predicate) implements BiPredicateMaker<T, U> {
    @Override
    public BiPredicate<T, U> make() {
        return (o1, o2) -> this.predicate.make().test(o2);
    }

    @Override
    public Codec<? extends BiPredicateMaker<T, U>> getCodec() {
        return null;
    }
}
