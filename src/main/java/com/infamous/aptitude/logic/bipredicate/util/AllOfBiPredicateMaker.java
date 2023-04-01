package com.infamous.aptitude.logic.bipredicate.util;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.function.BiPredicate;

public record AllOfBiPredicateMaker<T, U>(List<BiPredicateMaker<T, U>> biPredicates) implements BiPredicateMaker<T, U>{
    @Override
    public BiPredicate<T, U> make() {
        return (o1, o2) -> BiPredicateMaker.makeList(this.biPredicates).stream().allMatch(p -> p.test(o1, o2));
    }

    @Override
    public Codec<? extends BiPredicateMaker<T, U>> getCodec() {
        return null;
    }
}
