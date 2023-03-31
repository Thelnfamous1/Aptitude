package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromBlockMemory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record VillagerSetWalkTargetFromBlockMemoryMaker(MemoryModuleType<GlobalPos> blockMemory, float speedModifier, int closeEnough, int tooFar, int timeout) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetWalkTargetFromBlockMemory.create(this.blockMemory, this.speedModifier, this.closeEnough, this.tooFar, this.timeout);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_SET_WALK_TARGET_FROM_BLOCK_MEMORY.get();
    }
}
