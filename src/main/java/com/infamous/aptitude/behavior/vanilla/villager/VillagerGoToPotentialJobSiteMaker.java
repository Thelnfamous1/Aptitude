package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GoToPotentialJobSite;

public record VillagerGoToPotentialJobSiteMaker(float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new GoToPotentialJobSite(this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_GO_TO_POTENTIAL_JOB_SITE.get();
    }
}
