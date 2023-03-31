package com.infamous.aptitude.logic.biconsumer;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface BiConsumerMaker<T, U> {
    Codec<BiConsumerMaker<?, ?>> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> AptitudeRegistries.BICONSUMER_MAKER_SERIALIZERS.get().getCodec())
            .dispatch(BiConsumerMaker::getCodec, Function.identity());

    Codec<Holder<BiConsumerMaker<?, ?>>> REFERENCE_CODEC = RegistryFileCodec.create(AptitudeRegistries.Keys.BICONSUMER_MAKERS, DIRECT_CODEC);

    Codec<HolderSet<BiConsumerMaker<?, ?>>> LIST_CODEC = RegistryCodecs.homogeneousList(AptitudeRegistries.Keys.BICONSUMER_MAKERS, DIRECT_CODEC);

    BiConsumer<T, U> make();

    Codec<? extends BiConsumerMaker<T, U>> getCodec();

    static <T, U> List<BiConsumer<T, U>> makeList(List<BiConsumerMaker<T, U>> predicateMakers){
        return predicateMakers.stream().map(BiConsumerMaker::make).collect(Collectors.toList());
    }
}
