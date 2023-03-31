package com.infamous.aptitude.logic.function;

import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface FunctionMaker<T, R> {
    Codec<FunctionMaker<?, ?>> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> AptitudeRegistries.FUNCTION_MAKER_SERIALIZERS.get().getCodec())
            .dispatch(FunctionMaker::getCodec, Function.identity());

    Codec<Holder<FunctionMaker<?, ?>>> REFERENCE_CODEC = RegistryFileCodec.create(AptitudeRegistries.Keys.FUNCTION_MAKERS, DIRECT_CODEC);

    Codec<HolderSet<FunctionMaker<?, ?>>> LIST_CODEC = RegistryCodecs.homogeneousList(AptitudeRegistries.Keys.FUNCTION_MAKERS, DIRECT_CODEC);

    Function<T, R> make();

    Codec<? extends FunctionMaker<T, R>> getCodec();
}
