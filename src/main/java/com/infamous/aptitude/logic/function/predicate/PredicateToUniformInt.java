package com.infamous.aptitude.logic.function.predicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.UniformInt;

public class PredicateToUniformInt<T> extends PredicateToValue<T, UniformInt> {
    public PredicateToUniformInt(PredicateMaker<T> predicate, UniformInt primary, UniformInt secondary) {
        super(predicate, primary, secondary);
    }

    @Override
    public Codec<? extends FunctionMaker<T, UniformInt>> getCodec() {
        return null;
    }
}
