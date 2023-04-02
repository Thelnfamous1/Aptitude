package com.infamous.aptitude.registry;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.RegistryBuilder;

public class AptitudeGameData {

    static { init(); }

    public static void init(){
    }
    static RegistryBuilder<Codec<? extends BehaviorMaker>> getBehaviorMakerSerialziersRegistryBuilder() {
        return new RegistryBuilder<Codec<? extends BehaviorMaker>>().disableSaving().disableSync();
    }

    static RegistryBuilder<Codec<? extends PredicateMaker<?>>> getPredicateMakerSerializersRegistryBuilder() {
        return new RegistryBuilder<Codec<? extends PredicateMaker<?>>>().disableSaving().disableSync();
    }

    static RegistryBuilder<Codec<? extends FunctionMaker<?, ?>>> getFunctionMakerSerializersRegistryBuilder() {
        return new RegistryBuilder<Codec<? extends FunctionMaker<?, ?>>>().disableSaving().disableSync();
    }

    static RegistryBuilder<Codec<? extends BiPredicateMaker<?, ?>>> getBiPredicateMakerSerializersRegistryBuilder() {
        return new RegistryBuilder<Codec<? extends BiPredicateMaker<?, ?>>>().disableSaving().disableSync();
    }

    static RegistryBuilder<Codec<? extends BiConsumerMaker<?, ?>>> getBiConsumerMakerSerializersRegistryBuilder() {
        return new RegistryBuilder<Codec<? extends BiConsumerMaker<?, ?>>>().disableSaving().disableSync();
    }

    static RegistryBuilder<Codec<? extends BrainModifier>> getBrainModifierSerializersRegistryBuilder() {
        return new RegistryBuilder<Codec<? extends BrainModifier>>().disableSaving().disableSync();
    }
}
