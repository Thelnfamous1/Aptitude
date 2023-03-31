package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.CelebrateVillagersSurvivedRaid;

public record VillagerCelebrateVillagersSurviveRaidMaker(int minDuration, int maxDuration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new CelebrateVillagersSurvivedRaid(this.minDuration, this.maxDuration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_CELEBRATE_VILLAGERS_SURVIVE_RAID.get();
    }
}
