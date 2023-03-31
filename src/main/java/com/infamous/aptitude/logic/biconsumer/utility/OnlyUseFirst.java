package com.infamous.aptitude.logic.biconsumer.utility;

import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.consumer.ConsumerMaker;
import com.mojang.serialization.Codec;

import java.util.function.BiConsumer;

public record OnlyUseFirst<T, U>(ConsumerMaker<T> consumer) implements BiConsumerMaker<T, U> {
    @Override
    public BiConsumer<T, U> make() {
        return (o1, o2) -> this.consumer.make().accept(o1);
    }

    @Override
    public Codec<? extends BiConsumerMaker<T, U>> getCodec() {
        return null;
    }
}
