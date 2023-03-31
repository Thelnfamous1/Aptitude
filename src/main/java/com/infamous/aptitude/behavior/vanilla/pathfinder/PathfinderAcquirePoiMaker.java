package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.Optional;

public record PathfinderAcquirePoiMaker(PredicateMaker<Holder<PoiType>> poiPredicate, MemoryModuleType<GlobalPos> poiMemory, MemoryModuleType<GlobalPos> secondaryPoiMemory, boolean requireAdult, Optional<Byte> entityEventId) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return AcquirePoi.create(this.poiPredicate.make(), this.poiMemory, this.secondaryPoiMemory, this.requireAdult, this.entityEventId);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_ACQUIRE_POI.get();
    }
}
