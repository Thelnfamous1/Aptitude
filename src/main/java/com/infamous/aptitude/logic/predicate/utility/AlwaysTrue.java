package com.infamous.aptitude.logic.predicate.utility;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

import java.util.function.Predicate;

public class AlwaysTrue<T> implements PredicateMaker<T> {
    @Override
    public Predicate<T> make() {
        return o -> true;
    }

    @Override
    public Codec<? extends PredicateMaker<T>> getCodec() {
        return null;
    }
}
