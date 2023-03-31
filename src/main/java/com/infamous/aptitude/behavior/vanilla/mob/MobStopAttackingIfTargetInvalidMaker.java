package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;

public record MobStopAttackingIfTargetInvalidMaker<E extends Mob>(PredicateMaker<LivingEntity> targetPredicate, BiConsumerMaker<E, LivingEntity> onTargetErased, boolean canTire) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StopAttackingIfTargetInvalid.create(this.targetPredicate.make(), this.onTargetErased.make(), this.canTire);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_STOP_ATTACKING_IF_TARGET_INVALID.get();
    }
}
