package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;

public record VillagerHarvestFarmlandMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new HarvestFarmland();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_HARVEST_FARMLAND.get();
    }
}
