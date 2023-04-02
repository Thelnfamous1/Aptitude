package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class AptitudeRegistries {
    static { init(); } // This must be above the fields, so we guarantee it's run before getRegistry is called.
    static final DeferredRegister<Codec<? extends BehaviorMaker>> DEFERRED_BEHAVIOR_MAKER_SERIALIZERS =
            DeferredRegister.create(Keys.BEHAVIOR_MAKER_SERIALIZERS_KEY, Keys.BEHAVIOR_MAKER_SERIALIZERS_KEY.location().getNamespace());

    public static final Supplier<IForgeRegistry<Codec<? extends BehaviorMaker>>> BEHAVIOR_MAKER_SERIALIZERS =
            DEFERRED_BEHAVIOR_MAKER_SERIALIZERS.makeRegistry(AptitudeGameData::getBehaviorMakerSerialziersRegistryBuilder);

    static final DeferredRegister<Codec<? extends BiConsumerMaker<?, ?>>> DEFERRED_BICONSUMER_MAKER_SERIALIZERS =
            DeferredRegister.create(Keys.BICONSUMER_MAKER_SERIALIZERS_KEY, Keys.BICONSUMER_MAKER_SERIALIZERS_KEY.location().getNamespace());

    public static final Supplier<IForgeRegistry<Codec<? extends BiConsumerMaker<?, ?>>>> BICONSUMER_MAKER_SERIALIZERS =
            DEFERRED_BICONSUMER_MAKER_SERIALIZERS.makeRegistry(AptitudeGameData::getBiConsumerMakerSerializersRegistryBuilder);

    static final DeferredRegister<Codec<? extends BiPredicateMaker<?, ?>>> DEFERRED_BIPREDICATE_MAKER_SERIALIZERS =
            DeferredRegister.create(Keys.BIPREDICATE_MAKER_SERIALIZERS_KEY, Keys.BIPREDICATE_MAKER_SERIALIZERS_KEY.location().getNamespace());

    public static final Supplier<IForgeRegistry<Codec<? extends BiPredicateMaker<?, ?>>>> BIPREDICATE_MAKER_SERIALIZERS =
            DEFERRED_BIPREDICATE_MAKER_SERIALIZERS.makeRegistry(AptitudeGameData::getBiPredicateMakerSerializersRegistryBuilder);

    static final DeferredRegister<Codec<? extends BrainModifier>> DEFERRED_BRAIN_MODIFIER_SERIALIZERS =
            DeferredRegister.create(Keys.BRAIN_MODIFIER_SERIALIZERS_KEY, Keys.BRAIN_MODIFIER_SERIALIZERS_KEY.location().getNamespace());

    public static final Supplier<IForgeRegistry<Codec<? extends BrainModifier>>> BRAIN_MODIFIER_SERIALIZERS =
            DEFERRED_BRAIN_MODIFIER_SERIALIZERS.makeRegistry(AptitudeGameData::getBrainModifierSerializersRegistryBuilder);

    static final DeferredRegister<Codec<? extends FunctionMaker<?, ?>>> DEFERRED_FUNCTION_MAKER_SERIALIZERS =
            DeferredRegister.create(Keys.FUNCTION_MAKER_SERIALIZERS_KEY, Keys.FUNCTION_MAKER_SERIALIZERS_KEY.location().getNamespace());

    public static final Supplier<IForgeRegistry<Codec<? extends FunctionMaker<?, ?>>>> FUNCTION_MAKER_SERIALIZERS =
            DEFERRED_FUNCTION_MAKER_SERIALIZERS.makeRegistry(AptitudeGameData::getFunctionMakerSerializersRegistryBuilder);

    static final DeferredRegister<Codec<? extends PredicateMaker<?>>> DEFERRED_PREDICATE_MAKER_SERIALIZERS =
            DeferredRegister.create(Keys.PREDICATE_MAKER_SERIALIZERS_KEY, Keys.PREDICATE_MAKER_SERIALIZERS_KEY.location().getNamespace());

    public static final Supplier<IForgeRegistry<Codec<? extends PredicateMaker<?>>>> PREDICATE_MAKER_SERIALIZERS =
            DEFERRED_PREDICATE_MAKER_SERIALIZERS.makeRegistry(AptitudeGameData::getPredicateMakerSerializersRegistryBuilder);

    public static class Keys{
        public static final ResourceKey<Registry<BehaviorMaker>> BEHAVIOR_MAKERS =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "behavior_makers"));
        public static final ResourceKey<Registry<Codec<? extends BehaviorMaker>>> BEHAVIOR_MAKER_SERIALIZERS_KEY =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "behavior_maker_serializers"));
        public static final ResourceKey<Registry<BiConsumerMaker<?, ?>>> BICONSUMER_MAKERS =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "biconsumer_makers"));
        public static final ResourceKey<Registry<Codec<? extends BiConsumerMaker<?, ?>>>> BICONSUMER_MAKER_SERIALIZERS_KEY =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "biconsumer_maker_serializers"));
        public static final ResourceKey<Registry<BiPredicateMaker<?, ?>>> BIPREDICATE_MAKERS =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "bipredicate_makers"));
        public static final ResourceKey<Registry<Codec<? extends BiPredicateMaker<?, ?>>>> BIPREDICATE_MAKER_SERIALIZERS_KEY =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "bipredicate_maker_serializers"));
        public static final ResourceKey<Registry<BrainModifier>> BRAIN_MODIFIERS =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "brain_modifiers"));
        public static final ResourceKey<Registry<Codec<? extends BrainModifier>>> BRAIN_MODIFIER_SERIALIZERS_KEY =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "brain_modifier_serializers"));
        public static final ResourceKey<Registry<FunctionMaker<?, ?>>> FUNCTION_MAKERS =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "function_makers"));
        public static final ResourceKey<Registry<Codec<? extends FunctionMaker<?, ?>>>> FUNCTION_MAKER_SERIALIZERS_KEY =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "function_maker_serializers"));
        public static final ResourceKey<Registry<PredicateMaker<?>>> PREDICATE_MAKERS =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "predicate_makers"));
        public static final ResourceKey<Registry<Codec<? extends PredicateMaker<?>>>> PREDICATE_MAKER_SERIALIZERS_KEY =
                ResourceKey.createRegistryKey(new ResourceLocation(Aptitude.MODID, "predicate_maker_serializers"));

        private static void init() {}
    }

    /**
     * This function is just to make sure static initializers in other classes have run and setup their registries before we query them.
     */
    private static void init()
    {
        AptitudeRegistries.Keys.init();
        AptitudeGameData.init();
    }

}
