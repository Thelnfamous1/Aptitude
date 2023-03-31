package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StrollToPoiList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.List;

public record VillagerStrollToPoiListMaker(MemoryModuleType<List<GlobalPos>> poiListMemory, float speedModifier, int closeEnough, int strollDistance, MemoryModuleType<GlobalPos> poiMemory) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StrollToPoiList.create(this.poiListMemory, this.speedModifier, this.closeEnough, this.strollDistance, this.poiMemory);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_STROLL_TO_POI_LIST.get();
    }
}
