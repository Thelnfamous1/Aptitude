package com.infamous.aptitude.brain;

import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;

import java.util.function.Function;

public interface BrainModifier {
    Codec<BrainModifier> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> AptitudeRegistries.BRAIN_MODIFIER_SERIALIZERS.get().getCodec())
            .dispatch(BrainModifier::getCodec, Function.identity());

    Codec<Holder<BrainModifier>> REFERENCE_CODEC = RegistryFileCodec.create(AptitudeRegistries.Keys.BRAIN_MODIFIERS, DIRECT_CODEC);

    Codec<HolderSet<BrainModifier>> LIST_CODEC = RegistryCodecs.homogeneousList(AptitudeRegistries.Keys.BRAIN_MODIFIERS, DIRECT_CODEC);
    void modify(Holder<EntityType<?>> entityType, Either<Brain<?>, Brain.Provider<?>> brainOrProvider, BrainModifier.Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder);

    Codec<? extends BrainModifier> getCodec();

    enum Phase
    {
        /**
         * Catch-all for anything that needs to run before standard phases.
         */
        BEFORE_EVERYTHING,
        /**
         * Additional memories, sensors, activities, etc.
         */
        ADD,
        /**
         * Removal of memories, sensors, activities, etc.
         */
        REMOVE,
        /**
         * Alteration of values such as behaviors, schedules, core activities, etc.
         */
        MODIFY,
        /**
         * Catch-all for anything that needs to run after standard phases.
         */
        AFTER_EVERYTHING
    }
}
