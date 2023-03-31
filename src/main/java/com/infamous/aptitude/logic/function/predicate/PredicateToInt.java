package com.infamous.aptitude.logic.function.predicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

public class PredicateToInt<T> extends PredicateToValue<T, Integer> {
    public PredicateToInt(PredicateMaker<T> predicate, Integer primary, Integer secondary) {
        super(predicate, primary, secondary);
    }

    @Override
    public Codec<? extends FunctionMaker<T, Integer>> getCodec() {
        return null;
    }
}
