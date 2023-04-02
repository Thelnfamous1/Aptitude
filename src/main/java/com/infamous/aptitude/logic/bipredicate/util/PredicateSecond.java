package com.infamous.aptitude.logic.bipredicate.util;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.registry.AptitudeBiPredicateMakers;
import com.mojang.serialization.Codec;

import java.util.function.BiPredicate;

public record PredicateSecond<T, U>(PredicateMaker<U> predicate) implements BiPredicateMaker<T, U> {
    @Override
    public BiPredicate<T, U> make() {
        return (o1, o2) -> this.predicate.make().test(o2);
    }

    @Override
    public Codec<? extends BiPredicateMaker<T, U>> getCodec() {
        return (Codec<? extends BiPredicateMaker<T, U>>) (Codec<?>) AptitudeBiPredicateMakers.PREDICATE_SECOND.get();
    }
}
