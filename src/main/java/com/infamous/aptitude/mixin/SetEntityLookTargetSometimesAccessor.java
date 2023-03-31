package com.infamous.aptitude.mixin;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Predicate;

@Mixin(SetEntityLookTargetSometimes.class)
public interface SetEntityLookTargetSometimesAccessor {

    @Invoker
    static BehaviorControl<LivingEntity> callCreate(float maxDistance, UniformInt interval, Predicate<LivingEntity> targetPredicate){
        throw new AssertionError();
    }
}
