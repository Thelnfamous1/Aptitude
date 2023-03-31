package com.infamous.aptitude.logic.predicate.utility;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.function.Predicate;

public record AnyOf<T>(List<PredicateMaker<T>> predicates) implements PredicateMaker<T>{
    @Override
    public Predicate<T> make() {
        return o -> PredicateMaker.makeList(this.predicates).stream().anyMatch(p -> p.test(o));
    }

    @Override
    public Codec<? extends PredicateMaker<T>> getCodec() {
        return null;
    }
}
