package com.infamous.aptitude.registry;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class VanillaBiPredicateMakers {

    private static final DeferredRegister<Codec<? extends BiPredicateMaker<?,?>>> BIPREDICATE_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.BIPREDICATE_MAKER_SERIALIZERS_KEY, "minecraft");

    private static <BM extends BiPredicateMaker<?,?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return BIPREDICATE_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
