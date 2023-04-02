package com.infamous.aptitude.behavior.custom;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.registry.AptitudeBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record CustomSetEntityLookTargetMaker(BiPredicateMaker<LivingEntity, LivingEntity> canLookAt, float maxDistance) implements BehaviorMaker {

    public static OneShot<LivingEntity> makeBehavior(BiPredicate<LivingEntity, LivingEntity> canLookAt, float maxDistance) {
        float maxDistanceSqr = maxDistance * maxDistance;
        return BehaviorBuilder.create((instance) -> instance
                .group(instance.absent(MemoryModuleType.LOOK_TARGET), instance.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES))
                .apply(instance, (posTrackerMemory, entityList) -> (level, entity, gameTime) -> {
                    Optional<LivingEntity> target = instance
                            .get(entityList)
                            .findClosest(toPredicate(canLookAt, entity).and(e -> isOtherEntityCloseAndNotPassengerOf(maxDistanceSqr, entity, e)));
                    if (target.isEmpty()) {
                        return false;
                    } else {
                        posTrackerMemory.set(new EntityTracker(target.get(), true));
                        return true;
                    }
        }));
    }

    private static Predicate<LivingEntity> toPredicate(BiPredicate<LivingEntity, LivingEntity> biPredicate, LivingEntity entity) {
        return e -> biPredicate.test(entity, e);
    }

    private static boolean isOtherEntityCloseAndNotPassengerOf(double maxDistanceSqr, LivingEntity entity, LivingEntity other) {
        return other.distanceToSqr(entity) <= maxDistanceSqr && !entity.hasPassenger(other);
    }

    @Override
    public BehaviorControl<?> make() {
        return makeBehavior(this.canLookAt.make(), this.maxDistance);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return AptitudeBehaviorMakers.SET_ENTITY_LOOK_TARGET.get();
    }
}
