package com.infamous.aptitude.logic.function.predicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;

import java.util.function.Function;

public abstract class PredicateToValue<T, R> implements FunctionMaker<T, R> {

    private final PredicateMaker<T> predicate;
    private final R primary;
    private final R secondary;

    protected PredicateToValue(PredicateMaker<T> predicate, R primary, R secondary){
        this.predicate = predicate;
        this.primary = primary;
        this.secondary = secondary;
    }

    public PredicateMaker<T> getPredicate() {
        return predicate;
    }

    public R getPrimary() {
        return primary;
    }

    public R getSecondary() {
        return secondary;
    }

    @Override
    public Function<T, R> make() {
        return o -> this.predicate.make().test(o) ? this.primary : this.secondary;
    }
}
