package com.infamous.aptitude.logic.bipredicate.util;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.mojang.serialization.Codec;

import java.util.function.BiPredicate;

public class AlwaysFalseBiPredicateMaker<T, U> implements BiPredicateMaker<T, U> {
    @Override
    public BiPredicate<T, U> make() {
        return (o1, o2) -> false;
    }

    @Override
    public Codec<? extends BiPredicateMaker<T, U>> getCodec() {
        return null;
    }
}
