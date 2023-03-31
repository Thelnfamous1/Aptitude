package com.infamous.aptitude.util;

import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfoProvider;
import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;

import java.util.List;

public class AptitudeUtil {

    public static <E extends LivingEntity> void runBrainModifiers(MinecraftServer server, Holder<EntityType<E>> entityType, Either<Brain<E>, Brain.Provider<E>> brainOrProvider){
        final RegistryAccess registries = server.registryAccess();

        // The order of holders() is the order modifiers were loaded in.
        final List<BrainModifier> brainModifiers = registries.registryOrThrow(AptitudeRegistries.Keys.BRAIN_MODIFIERS)
                .holders()
                .map(Holder::value)
                .toList();

        // Apply sorted brain modifiers to either a Brain or a Brain.Provider.
        //noinspection unchecked
        brainOrProvider
                .map(ModifiableBrainInfoProvider.class::cast, ModifiableBrainInfoProvider.class::cast)
                .getModifiableBrainInfo()
                .applyBrainModifiers(entityType, brainOrProvider, brainModifiers);
    }
}
