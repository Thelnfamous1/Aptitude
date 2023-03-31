package com.infamous.aptitude.logic.function.predicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

public class PredicateToDouble<T> extends PredicateToValue<T, Double> {
    public PredicateToDouble(PredicateMaker<T> predicate, Double primary, Double secondary) {
        super(predicate, primary, secondary);
    }

    @Override
    public Codec<? extends FunctionMaker<T, Double>> getCodec() {
        return null;
    }
}
