package com.infamous.aptitude.logic.function.predicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

public class PredicateToFloat<T> extends PredicateToValue<T, Float> {
    public PredicateToFloat(PredicateMaker<T> predicate, Float primary, Float secondary) {
        super(predicate, primary, secondary);
    }

    @Override
    public Codec<? extends FunctionMaker<T, Float>> getCodec() {
        return null;
    }
}
