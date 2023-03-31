package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

import java.util.function.Predicate;

public record PoiIsAcquirableJobSiteForProfession(VillagerProfession profession) implements PredicateMaker<Holder<PoiType>>{
    @Override
    public Predicate<Holder<PoiType>> make() {
        return poiTypeHolder -> this.profession.acquirableJobSite().test(poiTypeHolder);
    }

    @Override
    public Codec<? extends PredicateMaker<Holder<PoiType>>> getCodec() {
        return null;
    }
}
