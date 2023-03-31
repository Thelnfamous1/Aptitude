package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Predicate;

public record PoiIsResource(ResourceKey<PoiType> poiResource) implements PredicateMaker<Holder<PoiType>>{
    @Override
    public Predicate<Holder<PoiType>> make() {
        return poiTypeHolder -> poiTypeHolder.is(this.poiResource);
    }

    @Override
    public Codec<? extends PredicateMaker<Holder<PoiType>>> getCodec() {
        return null;
    }
}
