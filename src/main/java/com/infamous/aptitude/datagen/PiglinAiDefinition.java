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
import com.infamous.aptitude.codec.PrioritizedBehavior;
import com.infamous.aptitude.codec.WeightedBehavior;
import com.infamous.aptitude.logic.bipredicate.*;
import com.infamous.aptitude.logic.bipredicate.util.NegateBiPredicateMaker;
import com.infamous.aptitude.logic.function.PiglinlikeFindNearestValidAttackTarget;
import com.infamous.aptitude.logic.predicate.*;
import com.infamous.aptitude.logic.predicate.utility.AllOfPredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.AlwaysTrue;
import com.infamous.aptitude.logic.predicate.utility.AnyOfPredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.Negate;
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

    static ImmutableList<PrioritizedBehavior> initCoreActivity() {
        return DataGenUtil.createPriorityPairs(0, ImmutableList.of(
                MobLookAtTargetSinkMaker.simple(),
                MobMoveToTargetSinkMaker.simple(),
                new InteractWithDoorMaker(),
                babyAvoidNemesis(),
                avoidZombified(),
                new PiglinStopHoldingItemIfNoLongerAdmiringMaker(),
                new StartAdmiringItemIfSeenMaker(ADMIRE_DURATION),
                new StartCelebratingIfTargetDeadMaker(CELEBRATION_TIME, PiglinlikeCanDance.piglin()),
                new StopBeingAngryIfTargetDeadMaker()));
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


    static ImmutableList<PrioritizedBehavior> initIdleActivity() {
        return DataGenUtil.createPriorityPairs(10, ImmutableList.of(
                new SetEntityLookTargetMaker(isPlayerHoldingLovedItem(), MAX_LOOK_DIST_FOR_PLAYER_HOLDING_LOVED_ITEM),
                new MobStartAttackingMaker(isAdult(), PiglinlikeFindNearestValidAttackTarget.piglin()),
                new SequenceTriggerIfMaker<>(new PiglinCanHunt(), new PiglinStartHuntingHoglinMaker()),
                avoidRepellent(),
                babySometimesRideBabyHoglin(),
                createIdleMovementBehaviors(),
                new SetLookAndInteractMaker(EntityType.PLAYER, 4)
                ));
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
        return RunOneMaker.simple(ImmutableList.<WeightedBehavior>builder()
                .addAll(createLookBehaviors())
                .add(WeightedBehavior.of(new DoNothingMaker(30, 60), convertWeight(1)))
                .build());
    }

    private static ImmutableList<WeightedBehavior> createLookBehaviors() {
        return ImmutableList.of(
                WeightedBehavior.of(SetEntityLookTargetMaker.forEntityType(EntityType.PLAYER, MAX_LOOK_DIST), convertWeight(1)),
                WeightedBehavior.of(SetEntityLookTargetMaker.forEntityType(EntityType.PIGLIN, MAX_LOOK_DIST), convertWeight(1)),
                WeightedBehavior.of(SetEntityLookTargetMaker.simple(MAX_LOOK_DIST), convertWeight(1)));
    }

    private static BehaviorMaker createIdleMovementBehaviors() {
        return RunOneMaker.simple(
                ImmutableList.of(
                        WeightedBehavior.of(PathfinderRandomStrollMaker.stroll(SPEED_MULTIPLIER_WHEN_IDLING), convertWeight(2)),
                        WeightedBehavior.of(InteractWithMaker.simple(EntityType.PIGLIN, INTERACTION_RANGE, MemoryModuleType.INTERACTION_TARGET, SPEED_MULTIPLIER_WHEN_IDLING, 2), convertWeight(2)),
                        WeightedBehavior.of(new SequenceTriggerIfMaker<>(doesntSeeAnyPlayerHoldingLovedItem(), SetWalkTargetFromLookTargetMaker.simple(SPEED_MULTIPLIER_WHEN_IDLING, 3)), convertWeight(2)),
                        WeightedBehavior.of(new DoNothingMaker(30, 60), convertWeight(1))));
    }

    private static PredicateMaker<LivingEntity> doesntSeeAnyPlayerHoldingLovedItem() {
        return new Negate<>(seesPlayerHoldingLovedItem());
    }

    private static PredicateMaker<LivingEntity> seesPlayerHoldingLovedItem() {
        return new EntityCheckMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryStatus.VALUE_PRESENT);
    }

    static ImmutableList<PrioritizedBehavior> initFightActivity() {
        return DataGenUtil.createPriorityPairs(10, ImmutableList.of(
                CustomStopAttackingIfTargetInvalidMaker.predicateOnly(new NegateBiPredicateMaker<>(isNearestValidAttackTarget())),
                new SequenceTriggerIfMaker<>(new EntityIsHolding(new ItemIsCrossbow()), new BackUpIfTooCloseMaker(MIN_DESIRED_DIST_FROM_TARGET_WHEN_HOLDING_CROSSBOW, SPEED_WHEN_STRAFING_BACK_FROM_TARGET)),
                MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker.simple(1.0F),
                new MobMeleeAttackMaker(MELEE_ATTACK_COOLDOWN),
                new MobCrossbowAttackMaker(),
                new RememberIfHoglinWasKilledMaker(),
                new EraseMemoryIfMaker<>(isNearZombified(), MemoryModuleType.ATTACK_TARGET)));
    }

    private static BiPredicateMaker<Mob, LivingEntity> isNearestValidAttackTarget() {
        return new OptionalResultOfFirstIsSecond(PiglinlikeFindNearestValidAttackTarget.piglin());
    }

    static ImmutableList<PrioritizedBehavior> initCelebrateActivity() {
        return DataGenUtil.createPriorityPairs(10, ImmutableList.of(
                avoidRepellent(),
                new SetEntityLookTargetMaker(isPlayerHoldingLovedItem(), MAX_LOOK_DIST_FOR_PLAYER_HOLDING_LOVED_ITEM),
                new MobStartAttackingMaker(isAdult(), PiglinlikeFindNearestValidAttackTarget.piglin()),
                new SequenceTriggerIfMaker<>(new Negate<>(new PiglinIsDancing()), new GoToTargetLocationMaker(MemoryModuleType.CELEBRATE_LOCATION, 2, SPEED_MULTIPLIER_WHEN_GOING_TO_CELEBRATE_LOCATION)),
                new SequenceTriggerIfMaker<>(new PiglinIsDancing(), new GoToTargetLocationMaker(MemoryModuleType.CELEBRATE_LOCATION, 4, SPEED_MULTIPLIER_WHEN_GOING_TO_CELEBRATE_LOCATION)),
                RunOneMaker.simple(
                        ImmutableList.of(
                                WeightedBehavior.of(SetEntityLookTargetMaker.forEntityType(EntityType.PIGLIN, MAX_LOOK_DIST), convertWeight(1)),
                                WeightedBehavior.of(PathfinderRandomStrollMaker.stroll(SPEED_MULTIPLIER_WHEN_DANCING, 2, 1), convertWeight(1)),
                                WeightedBehavior.of(new DoNothingMaker(10, 20), convertWeight(1))))
                 ));
    }


    private static PredicateMaker<LivingEntity> isNotHoldingLovedItemInOffHand() {
        return new AnyOfPredicateMaker<>(List.of(new EntityCheckItemInSlot(EquipmentSlot.OFFHAND, new ItemIsEmpty()), new EntityCheckItemInSlot(EquipmentSlot.OFFHAND, new Negate<>(new ItemIsTag(ItemTags.PIGLIN_LOVED)))));
    }

    static ImmutableList<PrioritizedBehavior> initAdmireItemActivity() {
        return DataGenUtil.createPriorityPairs(10, ImmutableList.of(
                new GoToWantedItemMaker<>(isNotHoldingLovedItemInOffHand(), SPEED_MULTIPLIER_WHEN_GOING_TO_WANTED_ITEM, true, MAX_DISTANCE_TO_WALK_TO_ITEM),
                new StopAdmiringIfItemTooFarAwayMaker(MAX_DISTANCE_TO_WALK_TO_ITEM),
                new StopAdmiringIfTiredOfTryingToReachItemMaker(MAX_TIME_TO_WALK_TO_ITEM, HOW_LONG_TIME_TO_DISABLE_ADMIRE_WALKING_IF_CANT_REACH_ITEM)));
    }

    static ImmutableList<PrioritizedBehavior> initRetreatActivity() {
        return DataGenUtil.createPriorityPairs(10, ImmutableList.of(
                PathfinderSetWalkTargetAwayFromMaker.entity(MemoryModuleType.AVOID_TARGET, SPEED_MULTIPLIER_WHEN_AVOIDING, DESIRED_DISTANCE_FROM_ENTITY_WHEN_AVOIDING, true),
                createIdleLookBehaviors(),
                createIdleMovementBehaviors(),
                new EraseMemoryIfMaker<>(PiglinlikeWantsToStopFleeing.piglin(), MemoryModuleType.AVOID_TARGET)));
    }

    static ImmutableList<PrioritizedBehavior> initRideHoglinActivity() {
        return DataGenUtil.createPriorityPairs(10, ImmutableList.of(
                new MountMaker(SPEED_MULTIPLIER_WHEN_MOUNTING),
                new SetEntityLookTargetMaker(isPlayerHoldingLovedItem(), MAX_LOOK_DIST),
                new SequenceMaker(new EntityTriggerIfMaker(new EntityIsPassenger()),
                        TriggerGateMaker.triggerOneShuffled(
                                ImmutableList.<WeightedBehavior>builder()
                                        .addAll(createLookBehaviors())
                                        .add(WeightedBehavior.of(new EntityTriggerIfMaker<>(new AlwaysTrue<>()), convertWeight(1)))
                                        .build())),
                new DismountOrSkipMountingMaker(MAX_WALK_DISTANCE_TO_START_RIDING, PiglinlikeStopRiding.piglin())));
    }

    private static int convertWeight(int i) {
        return i;
    }
}
