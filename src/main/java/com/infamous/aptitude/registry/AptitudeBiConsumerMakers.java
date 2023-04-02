package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.biconsumer.utility.NothingBiConsumerMaker;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AptitudeBiConsumerMakers {

    public static final DeferredRegister<Codec<? extends BiConsumerMaker<?,?>>> BICONSUMER_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.BICONSUMER_MAKER_SERIALIZERS_KEY, Aptitude.MODID);

    public static final RegistryObject<Codec<NothingBiConsumerMaker<?, ?>>> NOTHING = register("nothing", () ->
            Codec.unit(NothingBiConsumerMaker::new));

    private static <BM extends BiConsumerMaker<?,?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return BICONSUMER_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
