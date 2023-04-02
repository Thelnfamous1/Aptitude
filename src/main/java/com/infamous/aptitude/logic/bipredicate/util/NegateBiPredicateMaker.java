package com.infamous.aptitude.logic.bipredicate.util;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.registry.AptitudeBiPredicateMakers;
import com.mojang.serialization.Codec;

import java.util.function.BiPredicate;

public record NegateBiPredicateMaker<T, U>(BiPredicateMaker<T, U> biPredicate) implements BiPredicateMaker<T, U> {
    @Override
    public BiPredicate<T, U> make() {
        return this.biPredicate.make().negate();
    }

    @Override
    public Codec<? extends BiPredicateMaker<T, U>> getCodec() {
        return (Codec<? extends BiPredicateMaker<T, U>>) (Codec<?>) AptitudeBiPredicateMakers.NEGATE.get();
    }
}
