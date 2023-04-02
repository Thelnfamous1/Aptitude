package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.biconsumer.utility.NothingBiConsumerMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.logic.predicate.utility.AlwaysFalse;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;

public record MobStopAttackingIfTargetInvalidMaker<E extends Mob>(PredicateMaker<LivingEntity> stopAttackingWhen, BiConsumerMaker<E, LivingEntity> onTargetErased, boolean canTire) implements BehaviorMaker {

    public static <E extends Mob> MobStopAttackingIfTargetInvalidMaker<E> eraseOnly(BiConsumerMaker<E, LivingEntity> onTargetErased) {
        return new MobStopAttackingIfTargetInvalidMaker<>(new AlwaysFalse<>(), onTargetErased, true);
    }

    public static <E extends Mob> MobStopAttackingIfTargetInvalidMaker<E> predicateOnly(PredicateMaker<LivingEntity> stopAttackingWhen) {
        return new MobStopAttackingIfTargetInvalidMaker<>(stopAttackingWhen, new NothingBiConsumerMaker<>(), true);
    }

    public static <E extends Mob> MobStopAttackingIfTargetInvalidMaker<E> simple() {
        return new MobStopAttackingIfTargetInvalidMaker<>(new AlwaysFalse<>(), new NothingBiConsumerMaker<>(), true);
    }

    @Override
    public BehaviorControl<?> make() {
        return StopAttackingIfTargetInvalid.create(this.stopAttackingWhen.make(), this.onTargetErased.make(), this.canTire);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_STOP_ATTACKING_IF_TARGET_INVALID.get();
    }
}
