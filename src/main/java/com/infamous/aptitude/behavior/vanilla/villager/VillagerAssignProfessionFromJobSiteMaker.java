package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.AssignProfessionFromJobSite;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

public record VillagerAssignProfessionFromJobSiteMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return AssignProfessionFromJobSite.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_ASSIGN_PROFESSION_FROM_JOB_SITE.get();
    }
}
