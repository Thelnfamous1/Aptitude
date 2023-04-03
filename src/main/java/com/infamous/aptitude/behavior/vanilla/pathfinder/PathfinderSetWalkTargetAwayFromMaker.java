package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.EntityToSelfPosition;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.function.BottomCenterOfBlockPosition;
import com.infamous.aptitude.mixin.SetWalkTargetAwayFromAccessor;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

public record PathfinderSetWalkTargetAwayFromMaker<T>(MemoryModuleType<T> targetMemory, float speedModifier, int farEnough, boolean overrideWalkTarget, FunctionMaker<T, Vec3> targetPositionGetter) implements BehaviorMaker {


    public static PathfinderSetWalkTargetAwayFromMaker<BlockPos> pos(MemoryModuleType<BlockPos> targetMemory, float speedModifier, int farEnough, boolean overrideWalkTarget) {
        return new PathfinderSetWalkTargetAwayFromMaker<>(targetMemory, speedModifier, farEnough, overrideWalkTarget, (FunctionMaker<BlockPos, Vec3>) (Object) new BottomCenterOfBlockPosition());
    }

    public static <E extends Entity> PathfinderSetWalkTargetAwayFromMaker<E> entity(MemoryModuleType<E> targetMemory, float speedModifier, int farEnough, boolean overrideWalkTarget) {
        return new PathfinderSetWalkTargetAwayFromMaker<>(targetMemory, speedModifier, farEnough, overrideWalkTarget, (FunctionMaker<E, Vec3>) new EntityToSelfPosition());
    }
    @Override
    public BehaviorControl<?> make() {
        return SetWalkTargetAwayFromAccessor.callCreate(this.targetMemory, this.speedModifier, this.farEnough, this.overrideWalkTarget, this.targetPositionGetter.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_SET_WALK_TARGET_AWAY_FROM.get();
    }
}
