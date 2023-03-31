package com.infamous.aptitude.logic.consumer.utility;

import com.infamous.aptitude.logic.consumer.ConsumerMaker;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.function.Consumer;

public record Composite<T>(List<ConsumerMaker<T>> consumers) implements ConsumerMaker<T>{
    @Override
    public Consumer<T> make() {
        return o -> ConsumerMaker.makeList(this.consumers).forEach(c -> c.accept(o));
    }

    @Override
    public Codec<? extends ConsumerMaker<T>> getCodec() {
        return null;
    }
}
