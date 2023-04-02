package com.infamous.aptitude.behavior;

import com.google.common.collect.ImmutableList;
import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface BehaviorMaker
{
    Codec<BehaviorMaker> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> AptitudeRegistries.BEHAVIOR_MAKER_SERIALIZERS.get().getCodec())
            .dispatch(BehaviorMaker::getCodec, Function.identity());

    Codec<Holder<BehaviorMaker>> REFERENCE_CODEC = RegistryFileCodec.create(AptitudeRegistries.Keys.BEHAVIOR_MAKERS, DIRECT_CODEC);

    Codec<HolderSet<BehaviorMaker>> LIST_CODEC = RegistryCodecs.homogeneousList(AptitudeRegistries.Keys.BEHAVIOR_MAKERS, DIRECT_CODEC);

    BehaviorControl<?> make();

    Codec<? extends BehaviorMaker> getCodec();

    static <E extends LivingEntity> List<Pair<? extends BehaviorControl<? super E>, Integer>> makeWeightedBehaviors(List<Pair<BehaviorMaker, String>> weightedBehaviorMakers){
        List<Pair<? extends BehaviorControl<?>, Integer>> weightedBehaviors = new ArrayList<>(weightedBehaviorMakers.size());
        weightedBehaviorMakers.forEach(pair -> weightedBehaviors.add(Pair.of(pair.getFirst().make(), Integer.parseInt(pair.getSecond()))));
        return (List<Pair<? extends BehaviorControl<? super E>, Integer>>) (List<?>) weightedBehaviors;
    }

    static <E extends LivingEntity> ImmutableList<Pair<Integer, ? extends BehaviorControl<? super E>>> makePrioritizedBehaviors(List<Pair<String, BehaviorMaker>> prioritizedBehaviorMakers){
        ImmutableList.Builder<Pair<Integer, ? extends BehaviorControl<?>>> builder = ImmutableList.builder();
        prioritizedBehaviorMakers.forEach(pair -> builder.add(Pair.of(Integer.parseInt(pair.getFirst()), pair.getSecond().make())));
        return (ImmutableList<Pair<Integer, ? extends BehaviorControl<? super E>>>) (ImmutableList<?>) builder.build();
    }

    static <E extends LivingEntity> List<Pair<? extends Trigger<? super E>, Integer>> makeWeightedTriggers(List<Pair<BehaviorMaker, String>> weightedBehaviorMakers){
        List<Pair<? extends Trigger<?>, Integer>> weightedBehaviors = new ArrayList<>(weightedBehaviorMakers.size());
        weightedBehaviorMakers.forEach(pair -> weightedBehaviors.add(Pair.of(makeTrigger(pair.getFirst()), Integer.parseInt(pair.getSecond()))));
        return (List<Pair<? extends Trigger<? super E>, Integer>>) (List<?>) weightedBehaviors;
    }

    static <E extends LivingEntity> Trigger<E> makeTrigger(BehaviorMaker behaviorMaker){
        return (Trigger<E>) behaviorMaker.make();
    }

    static <E extends LivingEntity> OneShot<E> makeOneShot(BehaviorMaker behaviorMaker){
        return (OneShot<E>) behaviorMaker.make();
    }

}
