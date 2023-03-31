package com.infamous.aptitude.mixin;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(RandomStroll.class)
public interface RandomStrollAccessor {

    @Invoker
    static OneShot<PathfinderMob> callStrollFlyOrSwim(float speedModifier, Function<PathfinderMob, Vec3> posFinder, Predicate<PathfinderMob> selfPredicate){
        throw new AssertionError();
    }
}
