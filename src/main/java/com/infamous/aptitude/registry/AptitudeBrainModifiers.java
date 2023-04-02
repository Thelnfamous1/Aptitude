package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.stock.*;
import com.infamous.aptitude.codec.CustomCodecs;
import com.infamous.aptitude.codec.SetCodec;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AptitudeBrainModifiers {

    public static final DeferredRegister<Codec<? extends BrainModifier>> BRAIN_MODIFIER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.BRAIN_MODIFIER_SERIALIZERS_KEY, Aptitude.MODID);

    public static final RegistryObject<Codec<NoneBrainModifier>> NONE = BRAIN_MODIFIER_SERIALIZERS.register("none",
            () -> Codec.unit(NoneBrainModifier.INSTANCE));

    public static final RegistryObject<Codec<MakeBrainModifier>> MAKE_BRAIN = register("make_brain", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(Codec.BOOL, "replace", MakeBrainModifier::replace),
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", MakeBrainModifier::entityTypes),
                    Codec.simpleMap(ForgeRegistries.ACTIVITIES.getCodec(), CustomCodecs.PRIORITIES_TO_BEHAVIORS, BuiltInRegistries.ACTIVITY).fieldOf(  "prioritized_behaviors").forGetter(MakeBrainModifier::prioritizedBehaviors),
                    Codec.simpleMap(ForgeRegistries.ACTIVITIES.getCodec(), CustomCodecs.ACTIVITY_REQUIREMENTS, BuiltInRegistries.ACTIVITY).fieldOf(  "activity_requirements").forGetter(MakeBrainModifier::activityRequirements),
                    Codec.simpleMap(ForgeRegistries.ACTIVITIES.getCodec(), CustomCodecs.MEMORY_SET, BuiltInRegistries.ACTIVITY).fieldOf( "activity_memories_to_erase_when_stopped").forGetter(MakeBrainModifier::activityMemoriesToEraseWhenStopped),
                    CodecUtil.defineField(new SetCodec<>(ForgeRegistries.ACTIVITIES.getCodec()), "core_activities", MakeBrainModifier::coreActivities),
                    CodecUtil.defineField(ForgeRegistries.ACTIVITIES.getCodec(), "default_activity", MakeBrainModifier::defaultActivity)
            ).apply(builder, MakeBrainModifier::new)));

    public static final RegistryObject<Codec<AddMemoryTypesModifier>> ADD_MEMORY_TYPES = register("add_memory_types", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", AddMemoryTypesModifier::entityTypes),
                    CodecUtil.defineField(CustomCodecs.MEMORY_TYPE_LIST, "memory_types", AddMemoryTypesModifier::memoryTypes)
            ).apply(builder, AddMemoryTypesModifier::new)));

    public static final RegistryObject<Codec<AddSensorTypesModifier>> ADD_SENSOR_TYPES = register("add_sensor_types", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", AddSensorTypesModifier::entityTypes),
                    CodecUtil.defineField(CustomCodecs.SENSOR_TYPE_LIST, "sensor_types", AddSensorTypesModifier::sensorTypes)
            ).apply(builder, AddSensorTypesModifier::new)));

    public static final RegistryObject<Codec<AddActivityModifier>> ADD_ACTIVITY = register("add_activity", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", AddActivityModifier::entityTypes),
                    CodecUtil.defineField(ForgeRegistries.ACTIVITIES.getCodec(), "activity", AddActivityModifier::activity),
                    CodecUtil.defineField(CustomCodecs.PRIORITIES_TO_BEHAVIORS, "prioritized_behaviors", AddActivityModifier::prioritizedBehaviors),
                    CodecUtil.defineField(CustomCodecs.ACTIVITY_REQUIREMENTS, "activity_requirements", AddActivityModifier::activityRequirements),
                    CodecUtil.defineField(CustomCodecs.MEMORY_SET, "activity_memories_to_erase_when_stopped", AddActivityModifier::activityMemoriesToEraseWhenStopped)
            ).apply(builder, AddActivityModifier::new)));

    public static final RegistryObject<Codec<AddBehaviorsModifier>> ADD_BEHAVIORS = register("add_behaviors", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", AddBehaviorsModifier::entityTypes),
                    CodecUtil.defineField(ForgeRegistries.ACTIVITIES.getCodec(), "activity", AddBehaviorsModifier::activity),
                    CodecUtil.defineField(CustomCodecs.PRIORITIES_TO_BEHAVIORS, "prioritized_behaviors", AddBehaviorsModifier::prioritizedBehaviors)
            ).apply(builder, AddBehaviorsModifier::new)));

    public static final RegistryObject<Codec<RemoveMemoryTypesModifier>> REMOVE_MEMORY_TYPES = register("remove_memory_types", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", RemoveMemoryTypesModifier::entityTypes),
                    CodecUtil.defineField(CustomCodecs.MEMORY_TYPE_LIST, "memory_types", RemoveMemoryTypesModifier::memoryTypes)
            ).apply(builder, RemoveMemoryTypesModifier::new)));

    public static final RegistryObject<Codec<RemoveSensorTypesModifier>> REMOVE_SENSOR_TYPES = register("remove_sensor_types", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", RemoveSensorTypesModifier::entityTypes),
                    CodecUtil.defineField(CustomCodecs.SENSOR_TYPE_LIST, "sensor_types", RemoveSensorTypesModifier::sensorTypes)
            ).apply(builder, RemoveSensorTypesModifier::new)));

    public static final RegistryObject<Codec<RemoveActivityModifier>> REMOVE_ACTIVITY = register("remove_activity", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", RemoveActivityModifier::entityTypes),
                    CodecUtil.defineField(ForgeRegistries.ACTIVITIES.getCodec(), "activity", RemoveActivityModifier::activity)
            ).apply(builder, RemoveActivityModifier::new)));

    public static final RegistryObject<Codec<RemoveBehaviorsModifier>> REMOVE_BEHAVIORS = register("remove_behaviors", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.ENTITY_TYPE_LIST, "entity_types", RemoveBehaviorsModifier::entityTypes),
                    CodecUtil.defineField(ForgeRegistries.ACTIVITIES.getCodec(), "activity", RemoveBehaviorsModifier::activity),
                    CodecUtil.defineField(CustomCodecs.INTEGER_LIST, "priorities", RemoveBehaviorsModifier::priorities)
            ).apply(builder, RemoveBehaviorsModifier::new)));

    private static <BM extends BrainModifier> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return BRAIN_MODIFIER_SERIALIZERS.register(id, codecSupplier);
    }
}
