package com.infamous.aptitude.registry;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.AlwaysFalse;
import com.infamous.aptitude.logic.predicate.utility.AlwaysTrue;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class VanillaPredicateMakers {

    private static final DeferredRegister<Codec<? extends PredicateMaker<?>>> PREDICATE_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.PREDICATE_MAKER_SERIALIZERS_KEY, "minecraft");

    @SuppressWarnings("rawtypes")
    public static final RegistryObject<Codec<AlwaysTrue>> ALWAYS_TRUE = register("always_true", () ->
            Codec.unit(AlwaysTrue::new));

    @SuppressWarnings("rawtypes")
    public static final RegistryObject<Codec<AlwaysFalse>> ALWAYS_FALSE = register("always_false", () ->
            Codec.unit(AlwaysFalse::new));

    private static <BM extends PredicateMaker<?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return PREDICATE_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
