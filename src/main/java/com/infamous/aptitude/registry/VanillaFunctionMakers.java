package com.infamous.aptitude.registry;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class VanillaFunctionMakers {

    private static final DeferredRegister<Codec<? extends FunctionMaker<?, ?>>> FUNCTION_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.FUNCTION_MAKER_SERIALIZERS_KEY, "minecraft");

    private static <BM extends FunctionMaker<?, ?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return FUNCTION_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
