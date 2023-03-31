package com.infamous.aptitude;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

// The duration here should match an entry in the META-INF/mods.toml file
@Mod(Aptitude.MODID)
public class Aptitude
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "aptitude";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public Aptitude()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Aptitude-provided datapack registries
        modEventBus.addListener((DataPackRegistryEvent.NewRegistry event) -> {
            event.dataPackRegistry(AptitudeRegistries.Keys.BEHAVIOR_MAKERS, BehaviorMaker.DIRECT_CODEC);
            event.dataPackRegistry(AptitudeRegistries.Keys.BICONSUMER_MAKERS, BiConsumerMaker.DIRECT_CODEC);
            event.dataPackRegistry(AptitudeRegistries.Keys.BIPREDICATE_MAKERS, BiPredicateMaker.DIRECT_CODEC);
            event.dataPackRegistry(AptitudeRegistries.Keys.BRAIN_MODIFIERS, BrainModifier.DIRECT_CODEC);
            event.dataPackRegistry(AptitudeRegistries.Keys.FUNCTION_MAKERS, FunctionMaker.DIRECT_CODEC);
            event.dataPackRegistry(AptitudeRegistries.Keys.PREDICATE_MAKERS, PredicateMaker.DIRECT_CODEC);
        });
    }
}
