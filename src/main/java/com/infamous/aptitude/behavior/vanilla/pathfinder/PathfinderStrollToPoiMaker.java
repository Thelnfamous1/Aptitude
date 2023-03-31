package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StrollToPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record PathfinderStrollToPoiMaker(MemoryModuleType<GlobalPos> poiMemory, float speedModifier, int closeEnough, int strollDistance) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StrollToPoi.create(this.poiMemory, this.speedModifier, this.closeEnough, this.strollDistance);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_STROLL_TO_POI.get();
    }
}
