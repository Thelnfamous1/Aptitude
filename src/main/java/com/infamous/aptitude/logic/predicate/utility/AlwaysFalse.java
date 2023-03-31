package com.infamous.aptitude.logic.predicate.utility;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

import java.util.function.Predicate;

public class AlwaysFalse<T> implements PredicateMaker<T> {
    @Override
    public Predicate<T> make() {
        return o -> false;
    }

    @Override
    public Codec<? extends PredicateMaker<T>> getCodec() {
        return null;
    }
}
