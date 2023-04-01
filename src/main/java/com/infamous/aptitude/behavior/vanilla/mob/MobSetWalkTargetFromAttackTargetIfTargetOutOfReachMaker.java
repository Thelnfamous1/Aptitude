package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.function.util.ToFloat;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;

public record MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker(FunctionMaker<LivingEntity, Float> speedModifier) implements BehaviorMaker {

    public static MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker simple(float speedModifier) {
        return new MobSetWalkTargetFromAttackTargetIfTargetOutOfReachMaker(new ToFloat<>(speedModifier));
    }

    @Override
    public BehaviorControl<?> make() {
        return SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(this.speedModifier.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_SET_WALK_TARGET_FROM_ATTACK_TARGET_IF_TARGET_OUT_OF_REACH.get();
    }
}
