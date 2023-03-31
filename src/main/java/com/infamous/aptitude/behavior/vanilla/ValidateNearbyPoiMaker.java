package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.ValidateNearbyPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public record ValidateNearbyPoiMaker(PredicateMaker<Holder<PoiType>> poiPredicate, MemoryModuleType<GlobalPos> poiMemory) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return ValidateNearbyPoi.create(this.poiPredicate.make(), this.poiMemory);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VALIDATE_NEARBY_POI.get();
    }
}
