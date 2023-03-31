package com.infamous.aptitude.logic.biconsumer.utility;

import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.function.BiConsumer;

public record Composite<T, U>(List<BiConsumerMaker<T, U>> biconsumers) implements BiConsumerMaker<T, U>{
    @Override
    public BiConsumer<T, U> make() {
        return (o1, o2) -> BiConsumerMaker.makeList(this.biconsumers).forEach(c -> c.accept(o1, o2));
    }

    @Override
    public Codec<? extends BiConsumerMaker<T, U>> getCodec() {
        return null;
    }
}
