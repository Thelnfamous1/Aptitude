package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.YieldJobSite;

public record VillagerYieldJobSiteMaker(float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return YieldJobSite.create(this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_YIELD_JOB_SITE.get();
    }
}
