package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.PoiCompetitorScan;

public record VillagerPoiCompetitorScanMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return PoiCompetitorScan.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_POI_COMPETITOR_SCAN.get();
    }
}
