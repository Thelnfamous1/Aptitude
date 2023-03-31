package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.WorkAtComposter;

public class VillagerWorkAtComposterMaker implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new WorkAtComposter();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_WORK_AT_COMPOSTER.get();
    }
}
