package com.infamous.aptitude.behavior.custom;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.biconsumer.utility.NothingBiConsumerMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.bipredicate.util.AlwaysFalseBiPredicateMaker;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public record CustomStopAttackingIfTargetInvalidMaker<E extends LivingEntity>(BiPredicateMaker<E, LivingEntity> canAttackTarget, BiConsumerMaker<E, LivingEntity> onTargetErased, boolean canTire, int timeout) implements BehaviorMaker {


    public static <E extends Mob> CustomStopAttackingIfTargetInvalidMaker<E> eraseOnly(BiConsumerMaker<E, LivingEntity> p_260165_) {
        return new CustomStopAttackingIfTargetInvalidMaker<>(new AlwaysFalseBiPredicateMaker<>(), p_260165_, true, 200);
    }

    public static <E extends Mob> CustomStopAttackingIfTargetInvalidMaker<E> predicateOnly(BiPredicateMaker<E, LivingEntity> p_259762_) {
        return new CustomStopAttackingIfTargetInvalidMaker<>(p_259762_, new NothingBiConsumerMaker<>(), true, 200);
    }

    public static <E extends Mob> CustomStopAttackingIfTargetInvalidMaker<E> simple() {
        return new CustomStopAttackingIfTargetInvalidMaker<>(new AlwaysFalseBiPredicateMaker<>(), new NothingBiConsumerMaker<>(), true, 200);
    }

    public static <E extends LivingEntity> BehaviorControl<E> makeBehavior(BiPredicate<E, LivingEntity> canAttackTarget, BiConsumer<E, LivingEntity> onTargetErased, boolean canTire, int timeout) {
        return BehaviorBuilder.create((instance) -> instance
                .group(instance.present(MemoryModuleType.ATTACK_TARGET), instance.registered(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE))
                .apply(instance, (targetMemory, cantReachSinceMemory) -> (level, entity, gameTime) -> {
                    LivingEntity target = instance.get(targetMemory);
                    Optional<Long> cantReachSince = instance.tryGet(cantReachSinceMemory);
                    if (!entity.canAttack(target)
                            || (canTire && cantReachSince.isPresent() && isTiredOfTryingToReachTarget(entity, cantReachSince.get(), timeout))
                            || !target.isAlive()
                            || target.level != entity.level
                            || canAttackTarget.test(entity, target)) {
                        onTargetErased.accept(entity, target);
                        targetMemory.erase();
                    }
                    return true;
                }));
    }

    private static boolean isTiredOfTryingToReachTarget(LivingEntity entity, long since, int timeout) {
        return entity.level.getGameTime() - since > timeout;
    }

    @Override
    public BehaviorControl<?> make() {
        return makeBehavior(this.canAttackTarget.make(), this.onTargetErased.make(), this.canTire, this.timeout);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_STOP_ATTACKING_IF_TARGET_INVALID.get();
    }
}
