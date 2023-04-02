package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.codec.CustomCodecs;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.predicate.*;
import com.infamous.aptitude.logic.predicate.utility.*;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AptitudePredicateMakers {

    public static final DeferredRegister<Codec<? extends PredicateMaker<?>>> PREDICATE_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.PREDICATE_MAKER_SERIALIZERS_KEY, Aptitude.MODID);

    public static final RegistryObject<Codec<AllOfPredicateMaker<?>>> ALL_OF = register("all_of", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(CustomCodecs.PREDICATE_MAKER_LIST, "predicates", AllOfPredicateMaker::predicates)
            ).apply(builder, AllOfPredicateMaker::new)));

    public static final RegistryObject<Codec<AnyOfPredicateMaker<?>>> ANY_OF = register("any_of", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(CustomCodecs.PREDICATE_MAKER_LIST, "predicates", AnyOfPredicateMaker::predicates)
            ).apply(builder, AnyOfPredicateMaker::new)));

    @SuppressWarnings("rawtypes")
    public static final RegistryObject<Codec<AlwaysTrue>> ALWAYS_TRUE = register("always_true", () ->
            Codec.unit(AlwaysTrue::new));

    @SuppressWarnings("rawtypes")
    public static final RegistryObject<Codec<AlwaysFalse>> ALWAYS_FALSE = register("always_false", () ->
            Codec.unit(AlwaysFalse::new));

    public static final RegistryObject<Codec<EntityIsBaby>> ENTITY_IS_BABY = register("entity_is_baby", () ->
            Codec.unit(EntityIsBaby::new));

    public static final RegistryObject<Codec<EntityIsInWaterOrBubble>> ENTITY_IS_IN_WATER_OR_BUBBLE = register("entity_is_in_water_or_bubble", () ->
            Codec.unit(EntityIsInWaterOrBubble::new));

    public static final RegistryObject<Codec<EntityIsPassenger>> ENTITY_IS_PASSENGER = register("entity_is_passenger", () ->
            Codec.unit(EntityIsPassenger::new));

    public static final RegistryObject<Codec<EntityIsMobCategory>> ENTITY_IS_MOB_CATEGORY = register("entity_is_mob_category", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(MobCategory.CODEC, "mob_category", EntityIsMobCategory::mobCategory)
            ).apply(builder, EntityIsMobCategory::new)));

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final RegistryObject<Codec<Negate>> NEGATE = register("negate", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "predicate", Negate::predicate)
            ).apply(builder, Negate::new)));

    public static final RegistryObject<Codec<EntityTickerTickDownAndCheckMaker>> ENTITY_TICKER_TICK_DOWN_AND_CHECK = register("entity_ticker_tick_down_and_check", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.TICKER, "ticker", EntityTickerTickDownAndCheckMaker::ticker)
            ).apply(builder, EntityTickerTickDownAndCheckMaker::new)));

    public static final RegistryObject<Codec<EntityCheckItemInSlot>> ENTITY_CHECK_ITEM_IN_SLOT = register("entity_check_item_in_slot", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(CustomCodecs.EQUIPMENT_SLOT, "slot", EntityCheckItemInSlot::slot),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "predicate", EntityCheckItemInSlot::itemPredicate)
            ).apply(builder, EntityCheckItemInSlot::new)));

    public static final RegistryObject<Codec<EntityIsHolding>> ENTITY_IS_HOLDING = register("entity_is_holding", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "predicate", EntityIsHolding::itemPredicate)
            ).apply(builder, EntityIsHolding::new)));

    public static final RegistryObject<Codec<ItemIsCrossbow>> ITEM_IS_CROSSBOW = register("item_is_crossbow", () ->
            Codec.unit(ItemIsCrossbow::new));

    public static final RegistryObject<Codec<ItemIsEmpty>> ITEM_IS_EMPTY = register("item_is_empty", () ->
            Codec.unit(ItemIsEmpty::new));

    public static final RegistryObject<Codec<ItemIsTag>> ITEM_IS_TAG = register("item_is_tag", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(TagKey.codec(Registries.ITEM), "item_tag", ItemIsTag::itemTag)
            ).apply(builder, ItemIsTag::new)));

    public static final RegistryObject<Codec<EntityIsEntityType>> ENTITY_IS_ENTITY_TYPE = register("entity_is_entity_type", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(ForgeRegistries.ENTITY_TYPES.getCodec(), "entity_type", EntityIsEntityType::entityType)
            ).apply(builder, EntityIsEntityType::new)));

    public static final RegistryObject<Codec<EntityCheckMemory>> ENTITY_CHECK_MEMORY = register("entity_check_memory", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "memory", EntityCheckMemory::memory),
                    CodecUtil.defineField(CustomCodecs.MEMORY_STATUS, "status", EntityCheckMemory::status)
            ).apply(builder, EntityCheckMemory::new)));

    public static final RegistryObject<Codec<EntityIsNearEntityMemory>> ENTITY_IS_NEAR_ENTITY_MEMORY = register("entity_is_near_entity_memory", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "entity_memory", EntityIsNearEntityMemory::entityMemory),
                    CodecUtil.defineField(Codec.INT, "close_enough", EntityIsNearEntityMemory::closeEnough)
            ).apply(builder, EntityIsNearEntityMemory::new)));

    public static final RegistryObject<Codec<EntityIsOutnumbered>> ENTITY_IS_OUTNUMBERED = register("entity_is_outnumbered", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "ally_count_memory", EntityIsOutnumbered::allyCountMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "enemy_count_memory", EntityIsOutnumbered::enemyCountMemory)
            ).apply(builder, EntityIsOutnumbered::new)));

    public static final RegistryObject<Codec<PiglinCanHunt>> PIGLIN_CAN_HUNT = register("piglin_can_hunt", () ->
            Codec.unit(PiglinCanHunt::new));

    public static final RegistryObject<Codec<PiglinIsDancing>> PIGLIN_IS_DANCING = register("piglin_is_dancing", () ->
            Codec.unit(PiglinIsDancing::new));

    public static final RegistryObject<Codec<PiglinlikeStopFleeing>> PIGLINLIKE_STOP_FLEEING = register("piglinlike_stop_fleeing", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "target_memory", PiglinlikeStopFleeing::targetMemory),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "target_predicate", PiglinlikeStopFleeing::targetPredicate),
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "backup_bipredicate", PiglinlikeStopFleeing::backupBiPredicate)
            ).apply(builder, PiglinlikeStopFleeing::new)));

    private static <BM extends PredicateMaker<?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return PREDICATE_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
