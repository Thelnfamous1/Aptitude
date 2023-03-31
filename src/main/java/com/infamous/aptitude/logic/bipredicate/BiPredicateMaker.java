package com.infamous.aptitude.logic.bipredicate;

import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.function.BiPredicate;
import java.util.function.Function;

public interface BiPredicateMaker<T, U> {
    Codec<BiPredicateMaker<?, ?>> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> AptitudeRegistries.BIPREDICATE_MAKER_SERIALIZERS.get().getCodec())
            .dispatch(BiPredicateMaker::getCodec, Function.identity());

    Codec<Holder<BiPredicateMaker<?, ?>>> REFERENCE_CODEC = RegistryFileCodec.create(AptitudeRegistries.Keys.BIPREDICATE_MAKERS, DIRECT_CODEC);

    Codec<HolderSet<BiPredicateMaker<?, ?>>> LIST_CODEC = RegistryCodecs.homogeneousList(AptitudeRegistries.Keys.BIPREDICATE_MAKERS, DIRECT_CODEC);

    BiPredicate<T, U> make();

    Codec<? extends BiPredicateMaker<T, U>> getCodec();
}
