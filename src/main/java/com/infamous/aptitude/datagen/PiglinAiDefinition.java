package com.infamous.aptitude.datagen;

import com.google.common.collect.ImmutableList;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.behavior.custom.CustomStopAttackingIfTargetInvalidMaker;
import com.infamous.aptitude.behavior.vanilla.*;
import com.infamous.aptitude.behavior.vanilla.declarative.EntityTriggerIfMaker;
import com.infamous.aptitude.behavior.vanilla.declarative.SequenceMaker;
import com.infamous.aptitude.behavior.vanilla.declarative.SequenceTriggerIfMaker;
import com.infamous.aptitude.behavior.vanilla.mob.*;
import com.infamous.aptitude.behavior.vanilla.mob.crossbow_attack.MobCrossbowAttackMaker;
import com.infamous.aptitude.behavior.vanilla.pathfinder.PathfinderRandomStrollMaker;
import com.infamous.aptitude.behavior.vanilla.pathfinder.PathfinderSetWalkTargetAwayFromMaker;
import com.infamous.aptitude.behavior.vanilla.piglin.PiglinStartHuntingHoglinMaker;
import com.infamous.aptitude.behavior.vanilla.piglin.PiglinStopHoldingItemIfNoLongerAdmiringMaker;
import com.infamous.aptitude.logic.bipredicate.*;
import com.infamous.aptitude.logic.bipredicate.util.NegateBiPredicateMaker;
import com.infamous.aptitude.logic.function.PiglinlikeTargetFinder;
import com.infamous.aptitude.logic.predicate.*;
import com.infamous.aptitude.logic.predicate.utility.AllOfPredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.AlwaysTrue;
import com.infamous.aptitude.logic.predicate.utility.AnyOfPredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.Negate;
import com.mojang.datafixers.util.Pair;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.List;

public class PiglinAiDefinition {
    private static final int REPELLENT_DETECTION_RANGE_HORIZONTAL = 8;
    private static final int ADMIRE_DURATION = 120;
    private static final int MAX_DISTANCE_TO_WALK_TO_ITEM = 9;
    private static final int MAX_TIME_TO_WALK_TO_ITEM = 200;
    private static final int HOW_LONG_TIME_TO_DISABLE_ADMIRE_WALKING_IF_CANT_REACH_ITEM = 200;
    private static final int CELEBRATION_TIME = 300;
    private static final int MAX_WALK_DISTANCE_TO_START_RIDING = 8;
    private static final UniformInt RIDE_START_INTERVAL = TimeUtil.rangeOfSeconds(10, 40);
    private static final UniformInt RIDE_DURATION = TimeUtil.rangeOfSeconds(10, 30);
    private static final int MELEE_ATTACK_COOLDOWN = 20;
    private static final int DESIRED_DISTANCE_FROM_ENTITY_WHEN_AVOIDING = 12;
    private static final int MAX_LOOK_DIST = 8;
    private static final int MAX_LOOK_DIST_FOR_PLAYER_HOLDING_LOVED_ITEM = 14;
    private static final int INTERACTION_RANGE = 8;
    private static final int MIN_DESIRED_DIST_FROM_TARGET_WHEN_HOLDING_CROSSBOW = 5;
    private static final float SPEED_WHEN_STRAFING_BACK_FROM_TARGET = 0.75F;
    private static final int DESIRED_DISTANCE_FROM_ZOMBIFIED = 6;
    private static final UniformInt AVOID_ZOMBIFIED_DURATION = TimeUtil.rangeOfSeconds(5, 7);
    private static final UniformInt BABY_AVOID_NEMESIS_DURATION = TimeUtil.rangeOfSeconds(5, 7);
    private static final float SPEED_MULTIPLIER_WHEN_AVOIDING = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_RETREATING = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_MOUNTING = 0.8F;
    private static final float SPEED_MULTIPLIER_WHEN_GOING_TO_WANTED_ITEM = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_GOING_TO_CELEBRATE_LOCATION = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_DANCING = 0.6F;
    private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.6F;

    static ImmutableList<Pair<Integer, ? extends BehaviorMaker>> initCoreActivity() {
        return ImmutableList.of(
                Pair.of(0, MobLookAtTargetSinkMaker.simple()),
                Pair.of(1, MobMoveToTargetSinkMaker.simple()),
                Pair.of(2, new InteractWithDoorMaker()),
                Pair.of(3, babyAvoidNemesis()),
                Pair.of(4, avoidZombified()),
                Pair.of(5, new PiglinStopHoldingItemIfNoLongerAdmiringMaker()),
                Pair.of(6, new StartAdmiringItemIfSeenMaker(ADMIRE_DURATION)),
                Pair.of(7, new StartCelebratingIfTargetDeadMaker(CELEBRATION_TIME, PiglinlikeCanDance.piglin())),
                Pair.of(8, new StopBeingAngryIfTargetDeadMaker()));
    }

    private static BehaviorMaker babyAvoidNemesis() {
        return new CopyMemoryWithExpiryMaker<>(new EntityIsBaby(), MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.AVOID_TARGET, BABY_AVOID_NEMESIS_DURATION);
    }

    private static BehaviorMaker avoidZombified() {
        return new CopyMemoryWithExpiryMaker<>(isNearZombified(), MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.AVOID_TARGET, AVOID_ZOMBIFIED_DURATION);
    }

    private static EntityIsNearEntityMemory isNearZombified() {
        return new EntityIsNearEntityMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, DESIRED_DISTANCE_FROM_ZOMBIFIED);
    }


    static ImmutableList<Pair<Integer, ? extends BehaviorMaker>> initIdleActivity() {
        return ImmutableList.of(
                Pair.of(10, new SetEntityLookTargetMaker(isPlayerHoldingLovedItem(), MAX_LOOK_DIST_FOR_PLAYER_HOLDING_LOVED_ITEM)),
                Pair.of(11, new MobStartAttackingMaker(isAdult(), PiglinlikeTargetFinder.piglin())),
                Pair.of(12, new SequenceTriggerIfMaker<>(new PiglinCanHunt(), new PiglinStartHuntingHoglinMaker())),
                Pair.of(13, avoidRepellent()),
                Pair.of(14, babySometimesRideBabyHoglin()),
                Pair.of(15, createIdleLookBehaviors()),
                Pair.of(16, createIdleMovementBehaviors()),
                Pair.of(17, new SetLookAndInteractMaker(EntityType.PLAYER, 4)));
    }

    private static PredicateMaker<LivingEntity> isPlayerHoldingLovedItem() {
        return new AllOfPredicateMaker(List.of(new EntityIsEntityType(EntityType.PLAYER), new EntityIsHolding(new ItemIsTag(ItemTags.PIGLIN_LOVED))));
    }

    private static PredicateMaker<LivingEntity> isAdult(){
        return new Negate<>(new EntityIsBaby());
    }

    private static BehaviorMaker avoidRepellent() {
        return PathfinderSetWalkTargetAwayFromMaker.pos(MemoryModuleType.NEAREST_REPELLENT, SPEED_MULTIPLIER_WHEN_RETREATING, REPELLENT_DETECTION_RANGE_HORIZONTAL, false);
    }

    private static BehaviorMaker babySometimesRideBabyHoglin() {
        SetEntityLookTargetSometimes.Ticker ticker = new SetEntityLookTargetSometimes.Ticker(RIDE_START_INTERVAL);
        return new CopyMemoryWithExpiryMaker<>(new AllOfPredicateMaker(List.of(new EntityIsBaby(), new EntityTickerTickDownAndCheckMaker(ticker))), MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.RIDE_TARGET, RIDE_DURATION);
    }

    private static BehaviorMaker createIdleLookBehaviors() {
        return RunOneMaker.simple(ImmutableList.<Pair<? extends BehaviorMaker, Integer>>builder()
                .addAll(createLookBehaviors())
                .add(Pair.of(new DoNothingMaker(30, 60), 1))
                .build());
    }

    private static ImmutableList<Pair<BehaviorMaker, Integer>> createLookBehaviors() {
        return ImmutableList.of(
                Pair.of(SetEntityLookTargetMaker.forEntityType(EntityType.PLAYER, MAX_LOOK_DIST), 1),
                Pair.of(SetEntityLookTargetMaker.forEntityType(EntityType.PIGLIN, MAX_LOOK_DIST), 1),
                Pair.of(SetEntityLookTargetMaker.simple(MAX_LOOK_DIST), 1));
    }

    private static BehaviorMaker createIdleMovementBehaviors() {
        return RunOneMaker.simple(
                ImmutableList.of(
                        Pair.of(PathfinderRandomStrollMaker.stroll(SPEED_MULTIPLIER_WHEN_IDLING), 2),
                        Pair.of(InteractWithMaker.simple(EntityType.PIGLIN, INTERACTION_RANGE, MemoryModuleType.INTERACTION_TARGET, SPEED_MULTIPLIER_WHEN_IDLING, 2), 2),
                        Pair.of(new SequenceTriggerIfMaker<>(doesntSeeAnyPlayerHoldingLovedItem(), SetWalkTargetFromLookTargetMaker.simple(SPEED_MULTIPLIER_WHEN_IDLING, 3)), 2),
                        Pair.of(new DoNothingMaker(30, 60), 1)));
    }

    private static PredicateMaker<LivingEntity> doesntSeeAnyPlayerHoldingLovedItem() {
        return new Negate<>(seesPlayerHoldingLovedItem());
    }

    private static PredicateMaker<LivingEntity> seesPlayerHoldingLovedItem() {
        return new EntityCheckMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryStatus.VALUE_PRESENT);
    }

    static ImmutableList<Pair<Integer, ? extends BehaviorMaker>> initFightActivity() {
        return ImmutableList.of(
                Pair.of(10, CustomStopAttackingIfTargetInvalidMaker.predicateOnly(new NegateBiPredicateMaker<>(isNearestValidAttackTarget()))),
                Pair.of(11, new SequenceTriggerIfMaker<>(new EntityIsHolding(new ItemIsCrossbow()), new BackUpIfTooCloseMaker(MIN_DESIRED_DIST_FROM_TARGET_WHEN_HOLDING_CROSSBOW, SPEED_WHEN_STRAFING_BACK_FROM_TARGET))),
                Pair.of(12, MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker.simple(1.0F)),
                Pair.of(13, new MobMeleeAttackMaker(MELEE_ATTACK_COOLDOWN)),
                Pair.of(14, new MobCrossbowAttackMaker()),
                Pair.of(15, new RememberIfHoglinWasKilledMaker()),
                Pair.of(16, new EraseMemoryIfMaker<>(isNearZombified(), MemoryModuleType.ATTACK_TARGET)));
    }

    private static BiPredicateMaker<Mob, LivingEntity> isNearestValidAttackTarget() {
        return new OptionalResultOfFirstIsSecond(PiglinlikeTargetFinder.piglin());
    }

    static ImmutableList<Pair<Integer, ? extends BehaviorMaker>> initCelebrateActivity() {
        return ImmutableList.of(
                Pair.of(10, avoidRepellent()),
                Pair.of(11, new SetEntityLookTargetMaker(isPlayerHoldingLovedItem(), MAX_LOOK_DIST_FOR_PLAYER_HOLDING_LOVED_ITEM)),
                Pair.of(12, new MobStartAttackingMaker(isAdult(), PiglinlikeTargetFinder.piglin())),
                Pair.of(13, new SequenceTriggerIfMaker<>(new Negate<>(new PiglinIsDancing()), new GoToTargetLocationMaker(MemoryModuleType.CELEBRATE_LOCATION, 2, SPEED_MULTIPLIER_WHEN_GOING_TO_CELEBRATE_LOCATION))),
                Pair.of(14, new SequenceTriggerIfMaker<>(new PiglinIsDancing(), new GoToTargetLocationMaker(MemoryModuleType.CELEBRATE_LOCATION, 4, SPEED_MULTIPLIER_WHEN_GOING_TO_CELEBRATE_LOCATION))),
                Pair.of(15, RunOneMaker.simple(
                        ImmutableList.of(
                                Pair.of(SetEntityLookTargetMaker.forEntityType(EntityType.PIGLIN, MAX_LOOK_DIST), 1),
                                Pair.of(PathfinderRandomStrollMaker.stroll(SPEED_MULTIPLIER_WHEN_DANCING, 2, 1), 1),
                                Pair.of(new DoNothingMaker(10, 20), 1)))));
    }


    private static PredicateMaker<LivingEntity> isNotHoldingLovedItemInOffHand() {
        return new AnyOfPredicateMaker<>(List.of(new EntityCheckItemInSlot(EquipmentSlot.OFFHAND, new ItemIsEmpty()), new EntityCheckItemInSlot(EquipmentSlot.OFFHAND, new Negate<>(new ItemIsTag(ItemTags.PIGLIN_LOVED)))));
    }

    static ImmutableList<Pair<Integer, ? extends BehaviorMaker>> initAdmireItemActivity() {
        return ImmutableList.of(
                Pair.of(10, new GoToWantedItemMaker<>(isNotHoldingLovedItemInOffHand(), SPEED_MULTIPLIER_WHEN_GOING_TO_WANTED_ITEM, true, MAX_DISTANCE_TO_WALK_TO_ITEM)),
                Pair.of(11, new StopAdmiringIfItemTooFarAwayMaker(MAX_DISTANCE_TO_WALK_TO_ITEM)),
                Pair.of(12, new StopAdmiringIfTiredOfTryingToReachItemMaker(MAX_TIME_TO_WALK_TO_ITEM, HOW_LONG_TIME_TO_DISABLE_ADMIRE_WALKING_IF_CANT_REACH_ITEM)));
    }

    static ImmutableList<Pair<Integer, ? extends BehaviorMaker>> initRetreatActivity() {
        return ImmutableList.of(
                Pair.of(10, PathfinderSetWalkTargetAwayFromMaker.entity(MemoryModuleType.AVOID_TARGET, SPEED_MULTIPLIER_WHEN_AVOIDING, DESIRED_DISTANCE_FROM_ENTITY_WHEN_AVOIDING, true)),
                Pair.of(11, createIdleLookBehaviors()),
                Pair.of(12, createIdleMovementBehaviors()),
                Pair.of(13, new EraseMemoryIfMaker<>(PiglinlikeStopFleeing.piglin(), MemoryModuleType.AVOID_TARGET)));
    }

    static ImmutableList<Pair<Integer, ? extends BehaviorMaker>> initRideHoglinActivity() {
        return ImmutableList.of(
                Pair.of(10, new MountMaker(SPEED_MULTIPLIER_WHEN_MOUNTING)),
                Pair.of(11, new SetEntityLookTargetMaker(isPlayerHoldingLovedItem(), MAX_LOOK_DIST)),
                Pair.of(12, new SequenceMaker(new EntityTriggerIfMaker(new EntityIsPassenger()),
                        TriggerGateMaker.triggerOneShuffled(
                                ImmutableList.<Pair<? extends BehaviorMaker, Integer>>builder()
                                        .addAll(createLookBehaviors())
                                        .add(Pair.of(new EntityTriggerIfMaker<>(new AlwaysTrue<>()), 1))
                                        .build()))),
                Pair.of(13, new DismountOrSkipMountingMaker(MAX_WALK_DISTANCE_TO_START_RIDING, PiglinlikeStopRiding.piglin())));
    }
}
