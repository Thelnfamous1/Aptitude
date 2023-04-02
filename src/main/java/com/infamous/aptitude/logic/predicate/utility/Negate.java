package com.infamous.aptitude.logic.predicate.utility;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;

import java.util.function.Predicate;

public record Negate<T>(PredicateMaker<T> predicate) implements PredicateMaker<T>{
    @Override
    public Predicate<T> make() {
        return this.predicate.make().negate();
    }

    @Override
    public Codec<? extends PredicateMaker<T>> getCodec() {
        return (Codec<? extends PredicateMaker<T>>) (Codec<?>) AptitudePredicateMakers.NEGATE.get();
    }
}
