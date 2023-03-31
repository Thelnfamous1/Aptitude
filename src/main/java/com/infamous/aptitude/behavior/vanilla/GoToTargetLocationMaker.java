package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GoToTargetLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record GoToTargetLocationMaker(MemoryModuleType<BlockPos> targetMemory, int closeEnough, float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return GoToTargetLocation.create(this.targetMemory, this.closeEnough, this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.GO_TO_TARGET_LOCATION.get();
    }
}
