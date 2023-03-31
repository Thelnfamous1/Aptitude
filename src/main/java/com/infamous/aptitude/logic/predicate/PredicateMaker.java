package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface PredicateMaker<T> {
    Codec<PredicateMaker<?>> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> AptitudeRegistries.PREDICATE_MAKER_SERIALIZERS.get().getCodec())
            .dispatch(PredicateMaker::getCodec, Function.identity());

    Codec<Holder<PredicateMaker<?>>> REFERENCE_CODEC = RegistryFileCodec.create(AptitudeRegistries.Keys.PREDICATE_MAKERS, DIRECT_CODEC);

    Codec<HolderSet<PredicateMaker<?>>> LIST_CODEC = RegistryCodecs.homogeneousList(AptitudeRegistries.Keys.PREDICATE_MAKERS, DIRECT_CODEC);

    Predicate<T> make();

    Codec<? extends PredicateMaker<T>> getCodec();

    static <T> List<Predicate<T>> makeList(List<PredicateMaker<T>> predicateMakers){
        return predicateMakers.stream().map(PredicateMaker::make).collect(Collectors.toList());
    }
}
