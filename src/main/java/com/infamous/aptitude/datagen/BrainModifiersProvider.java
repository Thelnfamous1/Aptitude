package com.infamous.aptitude.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.stock.MakeBrainModifier;
import com.infamous.aptitude.codec.ActivityDefinition;
import com.infamous.aptitude.codec.MemoryCheck;
import com.infamous.aptitude.mixin.PiglinAccessor;
import com.infamous.aptitude.registry.AptitudeRegistries;
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
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
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
                PiglinAccessor.getMEMORY_TYPES(),
                (List<SensorType<?>>) (List<?>) PiglinAccessor.getSENSOR_TYPES(),
                ImmutableMap.of(
                        Activity.CORE, ActivityDefinition.of(PiglinAiDefinition.initCoreActivity(), ImmutableSet.of(), Sets.newHashSet()),
                        Activity.IDLE, ActivityDefinition.of(PiglinAiDefinition.initIdleActivity(), ImmutableSet.of(), Sets.newHashSet()),
                        Activity.FIGHT, ActivityDefinition.of(PiglinAiDefinition.initFightActivity(), ImmutableSet.of(MemoryCheck.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)), ImmutableSet.of(MemoryModuleType.ATTACK_TARGET)),
                        Activity.CELEBRATE, ActivityDefinition.of(PiglinAiDefinition.initCelebrateActivity(), ImmutableSet.of(MemoryCheck.of(MemoryModuleType.CELEBRATE_LOCATION, MemoryStatus.VALUE_PRESENT)), ImmutableSet.of(MemoryModuleType.CELEBRATE_LOCATION)),
                        Activity.ADMIRE_ITEM, ActivityDefinition.of(PiglinAiDefinition.initAdmireItemActivity(), ImmutableSet.of(MemoryCheck.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_PRESENT)), ImmutableSet.of(MemoryModuleType.ADMIRING_ITEM)),
                        Activity.AVOID, ActivityDefinition.of(PiglinAiDefinition.initRetreatActivity(), ImmutableSet.of(MemoryCheck.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_PRESENT)), ImmutableSet.of(MemoryModuleType.AVOID_TARGET)),
                        Activity.RIDE, ActivityDefinition.of(PiglinAiDefinition.initRideHoglinActivity(), ImmutableSet.of(MemoryCheck.of(MemoryModuleType.RIDE_TARGET, MemoryStatus.VALUE_PRESENT)), ImmutableSet.of(MemoryModuleType.RIDE_TARGET))
                ),
                ImmutableSet.of(Activity.CORE),
                Activity.IDLE,
                Schedule.EMPTY
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