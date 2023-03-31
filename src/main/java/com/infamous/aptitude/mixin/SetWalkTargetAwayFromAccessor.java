package com.infamous.aptitude.mixin;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;

@Mixin(SetWalkTargetAwayFrom.class)
public interface SetWalkTargetAwayFromAccessor {

    @Invoker
    static <T> OneShot<PathfinderMob> callCreate(MemoryModuleType<T> targetMemory, float speedModifier, int farEnough, boolean overrideWalkTarget, Function<T, Vec3> posGetter){
        throw new AssertionError();
    }
}
