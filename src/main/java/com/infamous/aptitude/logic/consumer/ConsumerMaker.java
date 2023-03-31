package com.infamous.aptitude.logic.consumer;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface ConsumerMaker<T> {

    Consumer<T> make();

    Codec<? extends ConsumerMaker<T>> getCodec();

    static <T> List<Consumer<T>> makeList(List<ConsumerMaker<T>> consumerMakers){
        return consumerMakers.stream().map(ConsumerMaker::make).collect(Collectors.toList());
    }
}
