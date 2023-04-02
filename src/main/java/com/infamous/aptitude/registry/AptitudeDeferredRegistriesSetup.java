package com.infamous.aptitude.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class AptitudeDeferredRegistriesSetup
{
    private static boolean setup = false;

    public static void setup(IEventBus modEventBus)
    {
        if (setup)
            throw new IllegalStateException("Setup has already been called!");

        AptitudeRegistries.DEFERRED_BEHAVIOR_MAKER_SERIALIZERS.register(modEventBus);
        AptitudeRegistries.DEFERRED_BICONSUMER_MAKER_SERIALIZERS.register(modEventBus);
        AptitudeRegistries.DEFERRED_BIPREDICATE_MAKER_SERIALIZERS.register(modEventBus);
        AptitudeRegistries.DEFERRED_BRAIN_MODIFIER_SERIALIZERS.register(modEventBus);
        AptitudeRegistries.DEFERRED_FUNCTION_MAKER_SERIALIZERS.register(modEventBus);
        AptitudeRegistries.DEFERRED_PREDICATE_MAKER_SERIALIZERS.register(modEventBus);

        setup = true;
    }
}