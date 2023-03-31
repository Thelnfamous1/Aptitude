package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.mixin.SetWalkTargetAwayFromAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

public record PathfinderSetWalkTargetAwayFromMaker<T>(MemoryModuleType<T> targetMemory, float speedModifier, int farEnough, boolean overrideWalkTarget, FunctionMaker<T, Vec3> positionGetter) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetWalkTargetAwayFromAccessor.callCreate(this.targetMemory, this.speedModifier, this.farEnough, this.overrideWalkTarget, this.positionGetter.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_SET_WALK_TARGET_AWAY_FROM.get();
    }
}
