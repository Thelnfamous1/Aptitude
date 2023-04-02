package com.infamous.aptitude.registry;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.behavior.vanilla.*;
import com.infamous.aptitude.behavior.vanilla.ageable.AgeableBabyFollowAdultMaker;
import com.infamous.aptitude.behavior.vanilla.animal.AnimalMakeLoveMaker;
import com.infamous.aptitude.behavior.vanilla.axolotl.AxolotlPlayDeadMaker;
import com.infamous.aptitude.behavior.vanilla.camel.CamelPanicMaker;
import com.infamous.aptitude.behavior.vanilla.camel.CamelRandomSittingMaker;
import com.infamous.aptitude.behavior.vanilla.declarative.EntityTriggerIfMaker;
import com.infamous.aptitude.behavior.vanilla.declarative.LevelEntityTriggerIfMaker;
import com.infamous.aptitude.behavior.vanilla.declarative.SequenceMaker;
import com.infamous.aptitude.behavior.vanilla.declarative.SequenceTriggerIfMaker;
import com.infamous.aptitude.behavior.vanilla.frog.FrogCroakMaker;
import com.infamous.aptitude.behavior.vanilla.frog.FrogShootTongueMaker;
import com.infamous.aptitude.behavior.vanilla.goat.GoatRamTargetMaker;
import com.infamous.aptitude.behavior.vanilla.inventory_carrier.GoAndGiveItemsToTargetMaker;
import com.infamous.aptitude.behavior.vanilla.mob.*;
import com.infamous.aptitude.behavior.vanilla.mob.crossbow_attack.MobCrossbowAttackMaker;
import com.infamous.aptitude.behavior.vanilla.pathfinder.*;
import com.infamous.aptitude.behavior.vanilla.piglin.PiglinStartHuntingHoglinMaker;
import com.infamous.aptitude.behavior.vanilla.piglin.PiglinStopHoldingItemIfNoLongerAdmiringMaker;
import com.infamous.aptitude.behavior.vanilla.villager.*;
import com.infamous.aptitude.behavior.vanilla.warden.*;
import com.infamous.aptitude.codec.CustomCodecs;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class VanillaBehaviorMakers {

    public static final DeferredRegister<Codec<? extends BehaviorMaker>> BEHAVIOR_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.BEHAVIOR_MAKER_SERIALIZERS_KEY, "minecraft");

    // AGEABLE
    public static final RegistryObject<Codec<AgeableBabyFollowAdultMaker>> AGEABLE_BABY_FOLLOW_ADULT = register("ageable_baby_follow_adult",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(UniformInt.CODEC, "follow_range", AgeableBabyFollowAdultMaker::followRange),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "speed_modifier", AgeableBabyFollowAdultMaker::speedModifier)
            ).apply(b, AgeableBabyFollowAdultMaker::new)));

    // ANIMAL
    public static final RegistryObject<Codec<AnimalMakeLoveMaker>> ANIMAL_MAKE_LOVE = register("animal_make_love",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.ENTITY_TYPES.getCodec(), "partner_type", AnimalMakeLoveMaker::partnerType),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", AnimalMakeLoveMaker::speedModifier)
            ).apply(b, AnimalMakeLoveMaker::new)));

    // AXOLOTL
    public static final RegistryObject<Codec<AxolotlPlayDeadMaker>> AXOLOTL_PLAY_DEAD = register("axolotl_play_dead",
            () -> Codec.unit(AxolotlPlayDeadMaker::new));

    public static final RegistryObject<Codec<BackUpIfTooCloseMaker>> BACK_UP_IF_TOO_CLOSE = register("back_up_if_too_close",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "too_close", BackUpIfTooCloseMaker::tooClose),
                    CodecUtil.defineField(Codec.FLOAT, "strafe", BackUpIfTooCloseMaker::strafe)
            ).apply(b, BackUpIfTooCloseMaker::new)));

    public static final RegistryObject<Codec<BecomePassiveIfMemoryPresentMaker>> BECOME_PASSIVE_IF_MEMORY_PRESENT = register("become_passive_if_memory_present",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "memory", BecomePassiveIfMemoryPresentMaker::memory),
                    CodecUtil.defineField(Codec.INT, "duration", BecomePassiveIfMemoryPresentMaker::duration)
            ).apply(b, BecomePassiveIfMemoryPresentMaker::new)));

    public static final RegistryObject<Codec<CalmDownMaker>> CALM_DOWN = register("calm_down",
            () -> Codec.unit(CalmDownMaker::new));

    // CAMEL
    public static final RegistryObject<Codec<CamelPanicMaker>> CAMEL_PANIC = register("camel_panic",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_multiplier", CamelPanicMaker::speedMultiplier)
            ).apply(b, CamelPanicMaker::new)));

    public static final RegistryObject<Codec<CamelRandomSittingMaker>> CAMEL_RANDOM_SITTING = register("camel_random_sitting",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "minimal_pose_time", CamelRandomSittingMaker::minimalPoseTime)
            ).apply(b, CamelRandomSittingMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<CopyMemoryWithExpiryMaker>> COPY_MEMORY_WITH_EXPIRY = register("copy_memory_with_expiry",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "copy_if", CopyMemoryWithExpiryMaker::copyIf),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "from", CopyMemoryWithExpiryMaker::from),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "to", CopyMemoryWithExpiryMaker::to),
                    CodecUtil.defineField(UniformInt.CODEC, "expiry", CopyMemoryWithExpiryMaker::expiry)
            ).apply(b, CopyMemoryWithExpiryMaker::new)));

    public static final RegistryObject<Codec<CountDownCooldownTicksMaker>> COUNT_DOWN_COOLDOWN_TICKS = register("count_down_cooldown_ticks",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "cooldown_ticks", CountDownCooldownTicksMaker::cooldownTicks)
            ).apply(b, CountDownCooldownTicksMaker::new)));

    public static final RegistryObject<Codec<DigCooldownSetterMaker>> DIG_COOLDOWN_SETTER = register("dig_cooldown_setter",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "cooldown", DigCooldownSetterMaker::cooldown)
            ).apply(b, DigCooldownSetterMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<DismountOrSkipMountingMaker>> DISMOUNT_OR_SKIP_MOUNTING = register("dismount_or_skip_mounting",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "close_enough", DismountOrSkipMountingMaker::closeEnough),
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "dismount_if", DismountOrSkipMountingMaker::dismountIf)
            ).apply(b, DismountOrSkipMountingMaker::new)));

    public static final RegistryObject<Codec<DoNothingMaker>> DO_NOTHING = register("do_nothing",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "min_duration", DoNothingMaker::minDuration),
                    CodecUtil.defineField(Codec.INT, "max_duration", DoNothingMaker::maxDuration)
            ).apply(b, DoNothingMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<EntityTriggerIfMaker>> ENTITY_TRIGGER_IF = register("entity_trigger_if",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "entity_predicate", EntityTriggerIfMaker::entityPredicate)
            ).apply(b, EntityTriggerIfMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<EraseMemoryIfMaker>> ERASE_MEMORY_IF = register("erase_memory_if",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "erase_if", EraseMemoryIfMaker::eraseIf),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "memory", EraseMemoryIfMaker::memory)
            ).apply(b, EraseMemoryIfMaker::new)));

    public static final RegistryObject<Codec<ForceUnmountMaker>> FORCE_UNMOUNT = register("force_unmount",
            () -> Codec.unit(ForceUnmountMaker::new));

    // FROG
    public static final RegistryObject<Codec<FrogCroakMaker>> FROG_CROAK = register("frog_croak",
            () -> Codec.unit(FrogCroakMaker::new));

    public static final RegistryObject<Codec<FrogShootTongueMaker>> FROG_SHOOT_TONGUE = register("frog_shoot_tongue",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(ForgeRegistries.SOUND_EVENTS.getCodec(), "tongue_sound", FrogShootTongueMaker::tongueSound),
                    CodecUtil.defineField(ForgeRegistries.SOUND_EVENTS.getCodec(), "eat_sound", FrogShootTongueMaker::eatSound)
            ).apply(b, FrogShootTongueMaker::new)));

    public static final RegistryObject<Codec<GateBehaviorMaker>> GATE_BEHAVIOR = register("gate_behavior",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(CustomCodecs.MEMORY_TO_STATUS_MAP, "entry_condition", GateBehaviorMaker::entryCondition),
                    CodecUtil.defineFieldUnchecked(CustomCodecs.MEMORY_SET, "exit_erased_memories", GateBehaviorMaker::exitErasedMemories),
                    CodecUtil.defineField(CustomCodecs.ORDER_POLICY, "order_policy", GateBehaviorMaker::orderPolicy),
                    CodecUtil.defineField(CustomCodecs.RUNNING_POLICY, "running_policy", GateBehaviorMaker::runningPolicy),
                    CodecUtil.defineFieldUnchecked(CustomCodecs.BEHAVIORS_TO_WEIGHT, "weighted_behaviors", GateBehaviorMaker::weightedBehaviors)
            ).apply(b, GateBehaviorMaker::new)));

    public static final RegistryObject<Codec<GoAndGiveItemsToTargetMaker>> GO_AND_GIVE_ITEMS_TO_TARGET = register("go_and_give_items_to_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "target_position_getter", GoAndGiveItemsToTargetMaker::targetPositionGetter),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", GoAndGiveItemsToTargetMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "duration", GoAndGiveItemsToTargetMaker::duration)
            ).apply(b, GoAndGiveItemsToTargetMaker::new)));

    public static final RegistryObject<Codec<GoToTargetLocationMaker>> GO_TO_TARGET_LOCATION = register("go_to_target_location",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "target_memory", GoToTargetLocationMaker::targetMemory),
                    CodecUtil.defineField(Codec.INT, "close_enough", GoToTargetLocationMaker::closeEnough),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", GoToTargetLocationMaker::speedModifier)
            ).apply(b, GoToTargetLocationMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<GoToWantedItemMaker>> GO_TO_WANTED_ITEM = register("go_to_wanted_item",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "self_predicate", GoToWantedItemMaker::selfPredicate),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", GoToWantedItemMaker::speedModifier),
                    CodecUtil.defineField(Codec.BOOL, "override_walk_target", GoToWantedItemMaker::overrideWalkTarget),
                    CodecUtil.defineField(Codec.INT, "close_enough", GoToWantedItemMaker::closeEnough)
            ).apply(b, GoToWantedItemMaker::new)));

    // GOAT
    public static final RegistryObject<Codec<GoatRamTargetMaker>> GOAT_RAM_TARGET = register("goat_ram_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "get_time_between_rams", GoatRamTargetMaker::getTimeBetweenRams),
                    CodecUtil.defineField(CustomCodecs.TARGETING_CONDITIONS, "ram_targeting", GoatRamTargetMaker::ramTargeting),
                    CodecUtil.defineField(Codec.FLOAT, "speed", GoatRamTargetMaker::speed),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "get_knockback_force", GoatRamTargetMaker::getKnockbackForce),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "get_impact_sound", GoatRamTargetMaker::getImpactSound),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "get_horn_break_sound", GoatRamTargetMaker::getHornBreakSound)
            ).apply(b, GoatRamTargetMaker::new)));

    public static final RegistryObject<Codec<InsideBrownianWalkMaker>> INSIDE_BROWNIAN_WALK = register("inside_brownian_walk",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", InsideBrownianWalkMaker::speedModifier)
            ).apply(b, InsideBrownianWalkMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<InteractWithMaker>> INTERACT_WITH = register("interact_with",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.ENTITY_TYPES.getCodec(), "target_type", InteractWithMaker::targetType),
                    CodecUtil.defineField(Codec.INT, "max_distance", InteractWithMaker::maxDistance),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "check_self", InteractWithMaker::checkSelf),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "check_target", InteractWithMaker::checkTarget),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "target_memory", InteractWithMaker::targetMemory),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", InteractWithMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "close_enough", InteractWithMaker::closeEnough)
            ).apply(b, InteractWithMaker::new)));

    public static final RegistryObject<Codec<InteractWithDoorMaker>> INTERACT_WITH_DOOR = register("interact_with_door",
            () -> Codec.unit(InteractWithDoorMaker::new));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<LevelEntityTriggerIfMaker>> LEVEL_ENTITY_TRIGGER_IF = register("level_entity_trigger_if",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "level_entity_bipredicate", LevelEntityTriggerIfMaker::levelEntityBiPredicate)
            ).apply(b, LevelEntityTriggerIfMaker::new)));

    public static final RegistryObject<Codec<LocateHidingPlaceMaker>> LOCATE_HIDING_PLACE = register("locate_hiding_place",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "range", LocateHidingPlaceMaker::range),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", LocateHidingPlaceMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "close_enough", LocateHidingPlaceMaker::closeEnough)
            ).apply(b, LocateHidingPlaceMaker::new)));

    // MOB
    public static final RegistryObject<Codec<MobCrossbowAttackMaker>> MOB_CROSSBOW_ATTACK = register("mob_crossbow_attack",
            () -> Codec.unit(MobCrossbowAttackMaker::new));
    public static final RegistryObject<Codec<MobJumpOnBedMaker>> MOB_JUMP_ON_BED = register("mob_jump_on_bed",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", MobJumpOnBedMaker::speedModifier)
            ).apply(b, MobJumpOnBedMaker::new)));

    public static final RegistryObject<Codec<MobLongJumpMidJumpMaker>> MOB_LONG_JUMP_MID_JUMP = register("mob_long_jump_mid_jump",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(UniformInt.CODEC, "time_between_long_jumps", MobLongJumpMidJumpMaker::timeBetweenLongJumps),
                    CodecUtil.defineField(ForgeRegistries.SOUND_EVENTS.getCodec(), "landing_sound", MobLongJumpMidJumpMaker::landingSound)
            ).apply(b, MobLongJumpMidJumpMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<MobLongJumpToPreferredBlockMaker>> MOB_LONG_JUMP_TO_PREFERRED_BLOCK = register("mob_long_jump_to_preferred_block",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(UniformInt.CODEC, "time_between_long_jumps", MobLongJumpToPreferredBlockMaker::timeBetweenLongJumps),
                    CodecUtil.defineField(Codec.INT, "max_long_jump_height", MobLongJumpToPreferredBlockMaker::maxLongJumpHeight),
                    CodecUtil.defineField(Codec.INT, "max_long_jump_width", MobLongJumpToPreferredBlockMaker::maxLongJumpWidth),
                    CodecUtil.defineField(Codec.FLOAT, "max_jump_velocity", MobLongJumpToPreferredBlockMaker::maxJumpVelocity),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "get_jump_sound", MobLongJumpToPreferredBlockMaker::getJumpSound),
                    CodecUtil.defineFieldUnchecked(TagKey.codec(Registries.BLOCK), "preferred_block_tag", MobLongJumpToPreferredBlockMaker::preferredBlockTag),
                    CodecUtil.defineField(Codec.FLOAT, "preferred_blocks_chance", MobLongJumpToPreferredBlockMaker::preferredBlocksChance),
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "acceptable_landing_spot", MobLongJumpToPreferredBlockMaker::acceptableLandingSpot)
            ).apply(b, MobLongJumpToPreferredBlockMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<MobLongJumpToRandomPosMaker>> MOB_LONG_JUMP_TO_RANDOM_POS = register("mob_long_jump_to_random_pos",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(UniformInt.CODEC, "time_between_long_jumps", MobLongJumpToRandomPosMaker::timeBetweenLongJumps),
                    CodecUtil.defineField(Codec.INT, "max_long_jump_height", MobLongJumpToRandomPosMaker::maxLongJumpHeight),
                    CodecUtil.defineField(Codec.INT, "max_long_jump_width", MobLongJumpToRandomPosMaker::maxLongJumpWidth),
                    CodecUtil.defineField(Codec.FLOAT, "max_jump_velocity", MobLongJumpToRandomPosMaker::maxJumpVelocity),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "get_jump_sound", MobLongJumpToRandomPosMaker::getJumpSound),
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "acceptable_landing_spot", MobLongJumpToRandomPosMaker::acceptableLandingSpot)
            ).apply(b, MobLongJumpToRandomPosMaker::new)));

    public static final RegistryObject<Codec<MobLookAtTargetSinkMaker>> MOB_LOOK_AT_TARGET_SINK = register("mob_look_at_target_sink",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "min_duration", MobLookAtTargetSinkMaker::minDuration),
                    CodecUtil.defineField(Codec.INT, "max_duration", MobLookAtTargetSinkMaker::maxDuration)
            ).apply(b, MobLookAtTargetSinkMaker::new)));

    public static final RegistryObject<Codec<MobMeleeAttackMaker>> MOB_MELEE_ATTACK = register("mob_melee_attack",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "cooldown", MobMeleeAttackMaker::cooldown)
            ).apply(b, MobMeleeAttackMaker::new)));

    public static final RegistryObject<Codec<MobMoveToTargetSinkMaker>> MOB_MOVE_TO_TARGET_SINK = register("mob_move_to_target_sink",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "min_duration", MobMoveToTargetSinkMaker::minDuration),
                    CodecUtil.defineField(Codec.INT, "max_duration", MobMoveToTargetSinkMaker::maxDuration)
            ).apply(b, MobMoveToTargetSinkMaker::new)));

    public static final RegistryObject<Codec<MobRandomLookAroundMaker>> MOB_RANDOM_LOOK_AROUND = register("mob_random_look_around",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(IntProvider.CODEC, "interval", MobRandomLookAroundMaker::interval),
                    CodecUtil.defineField(Codec.FLOAT, "max_yaw", MobRandomLookAroundMaker::maxYaw),
                    CodecUtil.defineField(Codec.FLOAT, "min_pitch", MobRandomLookAroundMaker::minPitch),
                    CodecUtil.defineField(Codec.FLOAT, "max_pitch", MobRandomLookAroundMaker::maxPitch)
            ).apply(b, MobRandomLookAroundMaker::new)));

    public static final RegistryObject<Codec<MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker>> MOB_SET_WALK_TARGET_FROM_ATTACK_TARGET_IF_TARGET_OUT_OF_REACH = register("mob_set_walk_target_from_attack_target_if_target_out_of_reach",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "speed_modifier", MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker::speedModifier)
            ).apply(b, MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<MobStartAttackingMaker>> MOB_START_ATTACKING = register("mob_start_attacking",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "self_predicate", MobStartAttackingMaker::selfPredicate),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "target_finder", MobStartAttackingMaker::targetFinder)
            ).apply(b, MobStartAttackingMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<MobStopAttackingIfTargetInvalidMaker>> MOB_STOP_ATTACKING_IF_TARGET_INVALID = register("mob_stop_attacking_if_target_invalid",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "stop_attacking_when", MobStopAttackingIfTargetInvalidMaker::stopAttackingWhen),
                    CodecUtil.defineFieldUnchecked(BiConsumerMaker.DIRECT_CODEC, "on_target_erased", MobStopAttackingIfTargetInvalidMaker::onTargetErased),
                    CodecUtil.defineField(Codec.BOOL, "can_tire", MobStopAttackingIfTargetInvalidMaker::canTire)
            ).apply(b, MobStopAttackingIfTargetInvalidMaker::new)));

    public static final RegistryObject<Codec<MobSwimMaker>> MOB_SWIM = register("mob_swim",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "chance", MobSwimMaker::chance)
            ).apply(b, MobSwimMaker::new)));

    public static final RegistryObject<Codec<MountMaker>> MOUNT = register("mount",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", MountMaker::speedModifier)
            ).apply(b, MountMaker::new)));

    public static final RegistryObject<Codec<MoveToSkySeeingSpotMaker>> MOVE_TO_SKY_SEEING_SPOT = register("move_to_sky_seeing_spot",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", MoveToSkySeeingSpotMaker::speedModifier)
            ).apply(b, MoveToSkySeeingSpotMaker::new)));

    // PATHFINDER
    public static final RegistryObject<Codec<PathfinderAcquirePoiMaker>> PATHFINDER_ACQUIRE_POI = register("pathfinder_acquire_poi",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "poi_predicate", PathfinderAcquirePoiMaker::poiPredicate),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "poi_memory", PathfinderAcquirePoiMaker::poiMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "secondary_poi_memory", PathfinderAcquirePoiMaker::secondaryPoiMemory),
                    CodecUtil.defineField(Codec.BOOL, "require_adult", PathfinderAcquirePoiMaker::requireAdult),
                    CodecUtil.defineOptionalField(Codec.BYTE, "entity_event_id", PathfinderAcquirePoiMaker::entityEventId)
            ).apply(b, PathfinderAcquirePoiMaker::new)));

    public static final RegistryObject<Codec<PathfinderFollowTemptationMaker>> PATHFINDER_FOLLOW_TEMPTATION = register("pathfinder_follow_temptation",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "speed_modifier", PathfinderFollowTemptationMaker::speedModifier)
            ).apply(b, PathfinderFollowTemptationMaker::new)));

    public static final RegistryObject<Codec<PathfinderPanicMaker>> PATHFINDER_PANIC = register("pathfinder_panic",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_multiplier", PathfinderPanicMaker::speedMultiplier)
            ).apply(b, PathfinderPanicMaker::new)));

    public static final RegistryObject<Codec<PathfinderPlayTagWithOtherKidsMaker>> PATHFINDER_PLAY_TAG_WITH_OTHER_KIDS = register("pathfinder_play_tag_with_other_kids",
            () -> Codec.unit(PathfinderPlayTagWithOtherKidsMaker::new));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<PathfinderPrepareRamNearestTargetMaker>> PATHFINDER_PREPARE_RAM_NEAREST_TARGET = register("pathfinder_prepare_ram_nearest_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "interval", PathfinderPrepareRamNearestTargetMaker::getCooldownOnFail),
                    CodecUtil.defineField(Codec.INT, "max_yaw", PathfinderPrepareRamNearestTargetMaker::minRamDistance),
                    CodecUtil.defineField(Codec.INT, "max_yaw", PathfinderPrepareRamNearestTargetMaker::maxRamDistance),
                    CodecUtil.defineField(Codec.FLOAT, "max_yaw", PathfinderPrepareRamNearestTargetMaker::walkSpeed),
                    CodecUtil.defineField(CustomCodecs.TARGETING_CONDITIONS, "ram_targeting", PathfinderPrepareRamNearestTargetMaker::ramTargeting),
                    CodecUtil.defineField(Codec.INT, "max_yaw", PathfinderPrepareRamNearestTargetMaker::ramPrepareTime),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "interval", PathfinderPrepareRamNearestTargetMaker::getPrepareRamSound)
            ).apply(b, PathfinderPrepareRamNearestTargetMaker::new)));

    public static final RegistryObject<Codec<PathfinderRandomStrollMaker>> PATHFINDER_RANDOM_STROLL = register("pathfinder_random_stroll",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderRandomStrollMaker::speedModifier),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "position_finder", PathfinderRandomStrollMaker::positionFinder),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "self_predicate", PathfinderRandomStrollMaker::selfPredicate)
            ).apply(b, PathfinderRandomStrollMaker::new)));

    public static final RegistryObject<Codec<PathfinderSetClosestHomeAsWalkTargetMaker>> PATHFINDER_SET_CLOSEST_HOME_AS_WALK_TARGET = register("pathfinder_set_closest_home_as_walk_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderSetClosestHomeAsWalkTargetMaker::speedModifier)
            ).apply(b, PathfinderSetClosestHomeAsWalkTargetMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<PathfinderSetWalkTargetAwayFromMaker>> PATHFINDER_SET_WALK_TARGET_AWAY_FROM = register("pathfinder_set_walk_target_away_from",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "block_memory", PathfinderSetWalkTargetAwayFromMaker::targetMemory),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderSetWalkTargetAwayFromMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "far_enough", PathfinderSetWalkTargetAwayFromMaker::farEnough),
                    CodecUtil.defineField(Codec.BOOL, "override_walk_target", PathfinderSetWalkTargetAwayFromMaker::overrideWalkTarget),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "position_getter", PathfinderSetWalkTargetAwayFromMaker::positionGetter)
            ).apply(b, PathfinderSetWalkTargetAwayFromMaker::new)));

    public static final RegistryObject<Codec<PathfinderStrollAroundPoiMaker>> PATHFINDER_STROLL_AROUND_POI = register("pathfinder_stroll_around_poi",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "poi_memory", PathfinderStrollAroundPoiMaker::poiMemory),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderStrollAroundPoiMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "stroll_distance", PathfinderStrollAroundPoiMaker::strollDistance)
            ).apply(b, PathfinderStrollAroundPoiMaker::new)));

    public static final RegistryObject<Codec<PathfinderStrollToPoiMaker>> PATHFINDER_STROLL_TO_POI = register("pathfinder_stroll_to_poi",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "poi_memory", PathfinderStrollToPoiMaker::poiMemory),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderStrollToPoiMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "close_enough", PathfinderStrollToPoiMaker::closeEnough),
                    CodecUtil.defineField(Codec.INT, "stroll_distance", PathfinderStrollToPoiMaker::strollDistance)
            ).apply(b, PathfinderStrollToPoiMaker::new)));

    public static final RegistryObject<Codec<PathfinderTryFindLandMaker>> PATHFINDER_TRY_FIND_LAND = register("pathfinder_try_find_land",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "max_distance", PathfinderTryFindLandMaker::maxDistance),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderTryFindLandMaker::speedModifier)
            ).apply(b, PathfinderTryFindLandMaker::new)));

    public static final RegistryObject<Codec<PathfinderTryFindLandNearWaterMaker>> PATHFINDER_TRY_FIND_LAND_NEAR_WATER = register("pathfinder_try_find_land_near_water",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "max_distance", PathfinderTryFindLandNearWaterMaker::maxDistance),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderTryFindLandNearWaterMaker::speedModifier)
            ).apply(b, PathfinderTryFindLandNearWaterMaker::new)));

    public static final RegistryObject<Codec<PathfinderTryFindWaterMaker>> PATHFINDER_TRY_FIND_WATER = register("pathfinder_try_find_water",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "max_distance", PathfinderTryFindWaterMaker::maxDistance),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderTryFindWaterMaker::speedModifier)
            ).apply(b, PathfinderTryFindWaterMaker::new)));

    public static final RegistryObject<Codec<PathfinderVillageBoundRandomStrollMaker>> PATHFINDER_VILLAGE_BOUND_RANDOM_STROLL = register("pathfinder_village_bound_random_stroll",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", PathfinderVillageBoundRandomStrollMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "max_xz_distance", PathfinderVillageBoundRandomStrollMaker::maxXZDistance),
                    CodecUtil.defineField(Codec.INT, "max_y_distance", PathfinderVillageBoundRandomStrollMaker::maxYDistance)
            ).apply(b, PathfinderVillageBoundRandomStrollMaker::new)));

    // PIGLIN
    public static final RegistryObject<Codec<PiglinStartHuntingHoglinMaker>> PIGLIN_START_HUNTING_HOGLIN = register("piglin_start_hunting_hoglin",
            () -> Codec.unit(PiglinStartHuntingHoglinMaker::new));

    public static final RegistryObject<Codec<PiglinStopHoldingItemIfNoLongerAdmiringMaker>> PIGLIN_STOP_HOLDING_ITEM_IF_NO_LONGER_ADMIRING = register("piglin_stop_holding_item_if_no_longer_admiring",
            () -> Codec.unit(PiglinStopHoldingItemIfNoLongerAdmiringMaker::new));

    public static final RegistryObject<Codec<ReactToBellMaker>> REACT_TO_BELL = register("react_to_bell",
            () -> Codec.unit(ReactToBellMaker::new));

    public static final RegistryObject<Codec<RememberIfHoglinWasKilledMaker>> REMEMBER_IF_HOGLIN_WAS_KILLED = register("remember_if_hoglin_was_killed",
            () -> Codec.unit(RememberIfHoglinWasKilledMaker::new));

    public static final RegistryObject<Codec<ResetRaidStatusMaker>> RESET_RAID_STATUS = register("reset_raid_status",
            () -> Codec.unit(ResetRaidStatusMaker::new));

    public static final RegistryObject<Codec<RingBellMaker>> RING_BELL = register("ring_bell",
            () -> Codec.unit(RingBellMaker::new));

    public static final RegistryObject<Codec<RunOneMaker>> RUN_ONE = register("run_one",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(CustomCodecs.MEMORY_TO_STATUS_MAP, "entry_condition", RunOneMaker::entryCondition),
                    CodecUtil.defineField(CustomCodecs.BEHAVIORS_TO_WEIGHT, "weighted_behaviors", RunOneMaker::weightedBehaviors)
            ).apply(b, RunOneMaker::new)));

    public static final RegistryObject<Codec<SetEntityLookTargetMaker>> SET_ENTITY_LOOK_TARGET = register("set_entity_look_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "target_predicate", SetEntityLookTargetMaker::targetPredicate),
                    CodecUtil.defineField(Codec.FLOAT, "max_distance", SetEntityLookTargetMaker::maxDistance)
            ).apply(b, SetEntityLookTargetMaker::new)));

    public static final RegistryObject<Codec<SetEntityLookTargetSometimesMaker>> SET_ENTITY_LOOK_TARGET_SOMETIMES = register("set_entity_look_target_sometimes",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "max_distance", SetEntityLookTargetSometimesMaker::maxDistance),
                    CodecUtil.defineField(UniformInt.CODEC, "interval", SetEntityLookTargetSometimesMaker::interval),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "target_predicate", SetEntityLookTargetSometimesMaker::targetPredicate)
            ).apply(b, SetEntityLookTargetSometimesMaker::new)));

    public static final RegistryObject<Codec<SetHiddenStateMaker>> SET_HIDDEN_STATE = register("set_hidden_state",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "time", SetHiddenStateMaker::time),
                    CodecUtil.defineField(Codec.INT, "close_enough", SetHiddenStateMaker::closeEnough)
            ).apply(b, SetHiddenStateMaker::new)));

    public static final RegistryObject<Codec<SetLookAndInteractMaker>> SET_LOOK_AND_INTERACT = register("set_look_and_interact",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(ForgeRegistries.ENTITY_TYPES.getCodec(), "target_type", SetLookAndInteractMaker::targetType),
                    CodecUtil.defineField(Codec.INT, "max_distance", SetLookAndInteractMaker::maxDistance)
            ).apply(b, SetLookAndInteractMaker::new)));

    public static final RegistryObject<Codec<SetRaidStatusMaker>> SET_RAID_STATUS = register("set_raid_status",
            () -> Codec.unit(SetRaidStatusMaker::new));

    public static final RegistryObject<Codec<SetWalkTargetFromLookTargetMaker>> SET_WALK_TARGET_FROM_LOOK_TARGET = register("set_walk_target_from_look_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "self_predicate", SetWalkTargetFromLookTargetMaker::selfPredicate),
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "speed_modifier", SetWalkTargetFromLookTargetMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "close_enough", SetWalkTargetFromLookTargetMaker::closeEnough)
            ).apply(b, SetWalkTargetFromLookTargetMaker::new)));

    public static final RegistryObject<Codec<SetWardenLookTargetMaker>> SET_WARDEN_LOOK_TARGET = register("set_warden_look_target",
            () -> Codec.unit(SetWardenLookTargetMaker::new));

    public static final RegistryObject<Codec<SequenceMaker>> SEQUENCE = register("sequence",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(BehaviorMaker.DIRECT_CODEC, "condition_trigger", SequenceMaker::conditionTrigger),
                    CodecUtil.defineFieldUnchecked(BehaviorMaker.DIRECT_CODEC, "result_trigger", SequenceMaker::resultTrigger)
            ).apply(b, SequenceMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<SequenceTriggerIfMaker>> SEQUENCE_TRIGGER_IF = register("sequence_trigger_if",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "predicate", SequenceTriggerIfMaker::predicate),
                    CodecUtil.defineFieldUnchecked(BehaviorMaker.DIRECT_CODEC, "one_shot", SequenceTriggerIfMaker::oneShot)
            ).apply(b, SequenceTriggerIfMaker::new)));

    public static final RegistryObject<Codec<SleepInBedMaker>> SLEEP_IN_BED = register("sleep_in_bed",
            () -> Codec.unit(SleepInBedMaker::new));

    public static final RegistryObject<Codec<SocializeAtBellMaker>> SOCIALIZE_AT_BELL = register("socialize_at_bell",
            () -> Codec.unit(SocializeAtBellMaker::new));
    public static final RegistryObject<Codec<StartAdmiringItemIfSeenMaker>> START_ADMIRING_ITEM_IF_SEEN = register("start_admiring_item_if_seen",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "duration", StartAdmiringItemIfSeenMaker::duration)
            ).apply(b, StartAdmiringItemIfSeenMaker::new)));

    public static final RegistryObject<Codec<StartCelebratingIfTargetDeadMaker>> START_CELEBRATING_IF_TARGET_DEAD = register("start_celebrating_if_target_dead",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "duration", StartCelebratingIfTargetDeadMaker::duration),
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "can_celebrate", StartCelebratingIfTargetDeadMaker::canCelebrate)
            ).apply(b, StartCelebratingIfTargetDeadMaker::new)));

    public static final RegistryObject<Codec<StayCloseToTargetMaker>> STAY_CLOSE_TO_TARGET = register("stay_close_to_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "position_tracker_getter", StayCloseToTargetMaker::positionTrackerGetter),
                    CodecUtil.defineField(Codec.INT, "close_enough", StayCloseToTargetMaker::closeEnough),
                    CodecUtil.defineField(Codec.INT, "too_far", StayCloseToTargetMaker::tooFar),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", StayCloseToTargetMaker::speedModifier)
            ).apply(b, StayCloseToTargetMaker::new)));

    public static final RegistryObject<Codec<StopAdmiringIfItemTooFarAwayMaker>> STOP_ADMIRING_IF_ITEM_TOO_FAR_AWAY = register("stop_admiring_if_item_too_far_away",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "close_enough", StopAdmiringIfItemTooFarAwayMaker::closeEnough)
            ).apply(b, StopAdmiringIfItemTooFarAwayMaker::new)));

    public static final RegistryObject<Codec<StopAdmiringIfTiredOfTryingToReachItemMaker>> STOP_ADMIRING_IF_TIRED_OF_TRYING_TO_REACH_ITEM = register("stop_admiring_if_tired_of_trying_to_reach_item",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "timeout", StopAdmiringIfTiredOfTryingToReachItemMaker::timeout),
                    CodecUtil.defineField(Codec.INT, "cooldown", StopAdmiringIfTiredOfTryingToReachItemMaker::cooldown)
            ).apply(b, StopAdmiringIfTiredOfTryingToReachItemMaker::new)));

    public static final RegistryObject<Codec<StopBeingAngryIfTargetDeadMaker>> STOP_BEING_ANGRY_IF_TARGET_DEAD = register("stop_being_angry_if_target_dead",
            () -> Codec.unit(StopBeingAngryIfTargetDeadMaker::new));

    public static final RegistryObject<Codec<TriggerGateMaker>> TRIGGER_GATE = register("trigger_gate",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(CustomCodecs.BEHAVIORS_TO_WEIGHT, "weighted_triggers", TriggerGateMaker::weightedTriggers),
                    CodecUtil.defineField(CustomCodecs.ORDER_POLICY, "order_policy", TriggerGateMaker::orderPolicy),
                    CodecUtil.defineField(CustomCodecs.RUNNING_POLICY, "running_policy", TriggerGateMaker::runningPolicy)
            ).apply(b, TriggerGateMaker::new)));

    public static final RegistryObject<Codec<TryLaySpawnOnWaterNearLandMaker>> TRY_LAY_SPAWN_ON_WATER_NEAR_LAND = register("try_lay_spawn_on_water_near_land",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(ForgeRegistries.BLOCKS.getCodec(), "spawn_block", TryLaySpawnOnWaterNearLandMaker::spawnBlock)
            ).apply(b, TryLaySpawnOnWaterNearLandMaker::new)));

    public static final RegistryObject<Codec<TryToSniffMaker>> TRY_TO_SNIFF = register("try_to_sniff",
            () -> Codec.unit(TryToSniffMaker::new));

    public static final RegistryObject<Codec<UpdateActivityFromScheduleMaker>> UPDATE_ACTIVITY_FROM_SCHEDULE = register("update_activity_from_schedule",
            () -> Codec.unit(UpdateActivityFromScheduleMaker::new));

    public static final RegistryObject<Codec<ValidateNearbyPoiMaker>> VALIDATE_NEARBY_POI = register("validate_nearby_poi",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "poi_predicate", ValidateNearbyPoiMaker::poiPredicate),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "poi_memory", ValidateNearbyPoiMaker::poiMemory)
            ).apply(b, ValidateNearbyPoiMaker::new)));

    public static final RegistryObject<Codec<ValidatePlayDeadMaker>> VALIDATE_PLAY_DEAD = register("validate_play_dead",
            () -> Codec.unit(ValidatePlayDeadMaker::new));

    // VILLAGER
    public static final RegistryObject<Codec<VillagerAssignProfessionFromJobSiteMaker>> VILLAGER_ASSIGN_PROFESSION_FROM_JOB_SITE = register("villager_assign_profession_from_job_site",
            () -> Codec.unit(VillagerAssignProfessionFromJobSiteMaker::new));

    public static final RegistryObject<Codec<VillagerCelebrateVillagersSurviveRaidMaker>> VILLAGER_CELEBRATE_VILLAGERS_SURVIVE_RAID = register("villager_celebrate_villagers_survive_raid",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "min_duration", VillagerCelebrateVillagersSurviveRaidMaker::minDuration),
                    CodecUtil.defineField(Codec.INT, "max_duration", VillagerCelebrateVillagersSurviveRaidMaker::maxDuration)
            ).apply(b, VillagerCelebrateVillagersSurviveRaidMaker::new)));

    public static final RegistryObject<Codec<VillagerGiveGiftToHeroMaker>> VILLAGER_GIVE_GIFT_TO_HERO = register("villager_give_gift_to_hero",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "duration", VillagerGiveGiftToHeroMaker::duration)
            ).apply(b, VillagerGiveGiftToHeroMaker::new)));

    public static final RegistryObject<Codec<VillagerGoToClosestVillageMaker>> VILLAGER_GO_TO_CLOSEST_VILLAGE = register("villager_go_to_closest_village",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", VillagerGoToClosestVillageMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "close_enough", VillagerGoToClosestVillageMaker::closeEnough)
            ).apply(b, VillagerGoToClosestVillageMaker::new)));

    public static final RegistryObject<Codec<VillagerGoToPotentialJobSiteMaker>> VILLAGER_GO_TO_POTENTIAL_JOB_SITE = register("villager_go_to_potential_job_site",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", VillagerGoToPotentialJobSiteMaker::speedModifier)
            ).apply(b, VillagerGoToPotentialJobSiteMaker::new)));

    public static final RegistryObject<Codec<VillagerHarvestFarmlandMaker>> VILLAGER_HARVEST_FARMLAND = register("villager_harvest_farmland",
            () -> Codec.unit(VillagerHarvestFarmlandMaker::new));

    public static final RegistryObject<Codec<VillagerLookAndFollowTradingPlayerSinkMaker>> VILLAGER_LOOK_AND_FOLLOW_TRADING_PLAYER = register("villager_look_and_follow_trading_player",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", VillagerLookAndFollowTradingPlayerSinkMaker::speedModifier)
            ).apply(b, VillagerLookAndFollowTradingPlayerSinkMaker::new)));

    public static final RegistryObject<Codec<VillagerMakeLoveMaker>> VILLAGER_MAKE_LOVE = register("villager_make_love",
            () -> Codec.unit(VillagerMakeLoveMaker::new));

    public static final RegistryObject<Codec<VillagerPanicTriggerMaker>> VILLAGER_PANIC_TRIGGER = register("villager_panic_trigger",
            () -> Codec.unit(VillagerPanicTriggerMaker::new));

    public static final RegistryObject<Codec<VillagerPoiCompetitorScanMaker>> VILLAGER_POI_COMPETITOR_SCAN = register("villager_poi_competitor_scan",
            () -> Codec.unit(VillagerPoiCompetitorScanMaker::new));

    public static final RegistryObject<Codec<VillagerResetProfessionMaker>> VILLAGER_RESET_PROFESSION = register("villager_reset_profession",
            () -> Codec.unit(VillagerResetProfessionMaker::new));

    public static final RegistryObject<Codec<VillagerSetWalkTargetFromBlockMemoryMaker>> VILLAGER_SET_WALK_TARGET_FROM_BLOCK_MEMORY = register("villager_set_walk_target_from_block_memory",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "block_memory", VillagerSetWalkTargetFromBlockMemoryMaker::blockMemory),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", VillagerSetWalkTargetFromBlockMemoryMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "close_enough", VillagerSetWalkTargetFromBlockMemoryMaker::closeEnough),
                    CodecUtil.defineField(Codec.INT, "too_far", VillagerSetWalkTargetFromBlockMemoryMaker::tooFar),
                    CodecUtil.defineField(Codec.INT, "timeout", VillagerSetWalkTargetFromBlockMemoryMaker::timeout)
            ).apply(b, VillagerSetWalkTargetFromBlockMemoryMaker::new)));

    public static final RegistryObject<Codec<VillagerShowTradesToPlayerMaker>> VILLAGER_SHOW_TRADES_TO_PLAYER = register("villager_show_trades_to_player",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "min_duration", VillagerShowTradesToPlayerMaker::minDuration),
                    CodecUtil.defineField(Codec.INT, "max_duration", VillagerShowTradesToPlayerMaker::maxDuration)
            ).apply(b, VillagerShowTradesToPlayerMaker::new)));

    public static final RegistryObject<Codec<VillagerStrollToPoiListMaker>> VILLAGER_STROLL_TO_POI_LIST = register("villager_stroll_to_poi_list",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "poi_list_memory", VillagerStrollToPoiListMaker::poiListMemory),
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", VillagerStrollToPoiListMaker::speedModifier),
                    CodecUtil.defineField(Codec.INT, "close_enough", VillagerStrollToPoiListMaker::closeEnough),
                    CodecUtil.defineField(Codec.INT, "stroll_distance", VillagerStrollToPoiListMaker::strollDistance),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "poi_memory", VillagerStrollToPoiListMaker::poiMemory)
            ).apply(b, VillagerStrollToPoiListMaker::new)));

    public static final RegistryObject<Codec<VillagerTradeWithVillagerMaker>> VILLAGER_TRADE_WITH_VILLAGER = register("villager_trade_with_villager",
            () -> Codec.unit(VillagerTradeWithVillagerMaker::new));

    public static final RegistryObject<Codec<VillagerUseBonemealMaker>> VILLAGER_USE_BONEMEAL = register("villager_use_bonemeal",
            () -> Codec.unit(VillagerUseBonemealMaker::new));

    public static final RegistryObject<Codec<VillagerWorkAtComposterMaker>> VILLAGER_WORK_AT_COMPOSTER = register("villager_work_at_composter",
            () -> Codec.unit(VillagerWorkAtComposterMaker::new));

    public static final RegistryObject<Codec<VillagerWorkAtPoiMaker>> VILLAGER_WORK_AT_POI = register("villager_work_at_poi",
            () -> Codec.unit(VillagerWorkAtPoiMaker::new));

    public static final RegistryObject<Codec<VillagerYieldJobSiteMaker>> VILLAGER_YIELD_JOB_SITE = register("villager_yield_job_site",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.FLOAT, "speed_modifier", VillagerYieldJobSiteMaker::speedModifier)
            ).apply(b, VillagerYieldJobSiteMaker::new)));

    public static final RegistryObject<Codec<WakeUpMaker>> WAKE_UP = register("wake_up",
            () -> Codec.unit(WakeUpMaker::new));

    // WARDEN
    public static final RegistryObject<Codec<WardenDiggingMaker>> WARDEN_DIGGING = register("warden_digging",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "duration", WardenDiggingMaker::duration)
            ).apply(b, WardenDiggingMaker::new)));

    public static final RegistryObject<Codec<WardenEmergingMaker>> WARDEN_EMERGING = register("warden_emerging",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "duration", WardenEmergingMaker::duration)
            ).apply(b, WardenEmergingMaker::new)));

    public static final RegistryObject<Codec<WardenRoarMaker>> WARDEN_ROAR = register("warden_roar",
            () -> Codec.unit(WardenRoarMaker::new));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<WardenSetRoarTargetMaker>> WARDEN_SET_ROAR_TARGET = register("warden_set_roar_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "target_getter", WardenSetRoarTargetMaker::targetGetter)
            ).apply(b, WardenSetRoarTargetMaker::new)));

    public static final RegistryObject<Codec<WardenSniffingMaker>> WARDEN_SNIFFING = register("warden_sniffing",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineField(Codec.INT, "duration", WardenSniffingMaker::duration)
            ).apply(b, WardenSniffingMaker::new)));

    public static final RegistryObject<Codec<WardenSonicBoomMaker>> WARDEN_SONIC_BOOM = register("warden_sonic_boom",
            () -> Codec.unit(WardenSonicBoomMaker::new));

    private static <BM extends BehaviorMaker> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return BEHAVIOR_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
