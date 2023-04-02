package com.infamous.aptitude.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.stock.MakeBrainModifier;
import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BrainModifiersProvider extends DatapackBuiltinEntriesProvider
{
    private static final ResourceKey<EntityType<?>> PIGLIN = ResourceKey.create(ForgeRegistries.Keys.ENTITY_TYPES, new ResourceLocation("piglin"));

    private static final ResourceKey<BrainModifier> MAKE_PIGLIN_BRAIN_MODIFIER = ResourceKey.create(AptitudeRegistries.Keys.BRAIN_MODIFIERS, new ResourceLocation(Aptitude.MODID, "make_piglin_brain"));

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(AptitudeRegistries.Keys.BRAIN_MODIFIERS, BrainModifiersProvider::bootstrap);

    private static void bootstrap(BootstapContext<BrainModifier> context) {
        MakeBrainModifier makePiglinBrain = new MakeBrainModifier(
                true,
                HolderSet.direct(context.lookup(Registries.ENTITY_TYPE).getOrThrow(PIGLIN)),
                ImmutableMap.of(
                        Activity.CORE, PiglinAiDefinition.initCoreActivity(),
                        //Activity.IDLE, PiglinAiDefinition.initIdleActivity(),
                        Activity.FIGHT, PiglinAiDefinition.initFightActivity(),
                        //Activity.CELEBRATE, PiglinAiDefinition.initCelebrateActivity(),
                        Activity.ADMIRE_ITEM, PiglinAiDefinition.initAdmireItemActivity(),
                        //Activity.AVOID, PiglinAiDefinition.initRetreatActivity(),
                        Activity.RIDE, PiglinAiDefinition.initRideHoglinActivity()
                ),
                ImmutableMap.of(
                        Activity.FIGHT, Set.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)),
                        Activity.CELEBRATE, Set.of(Pair.of(MemoryModuleType.CELEBRATE_LOCATION, MemoryStatus.VALUE_PRESENT)),
                        Activity.ADMIRE_ITEM, Set.of(Pair.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_PRESENT)),
                        Activity.AVOID, Set.of(Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_PRESENT)),
                        Activity.RIDE, Set.of(Pair.of(MemoryModuleType.RIDE_TARGET, MemoryStatus.VALUE_PRESENT))
                ),
                ImmutableMap.of(
                        Activity.FIGHT, Set.of(MemoryModuleType.ATTACK_TARGET),
                        Activity.CELEBRATE, Set.of(MemoryModuleType.CELEBRATE_LOCATION),
                        Activity.ADMIRE_ITEM, Set.of(MemoryModuleType.ADMIRING_ITEM),
                        Activity.AVOID, Set.of(MemoryModuleType.AVOID_TARGET),
                        Activity.RIDE, Set.of(MemoryModuleType.RIDE_TARGET)
                ),
                ImmutableSet.of(Activity.CORE),
                Activity.IDLE
        );
        context.register(MAKE_PIGLIN_BRAIN_MODIFIER, makePiglinBrain);
    }

    public BrainModifiersProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries, BUILDER, Set.of(Aptitude.MODID));
    }

    @Override
    public String getName()
        {
            return "Brain Modifier Registries: " + Aptitude.MODID;
        }
}