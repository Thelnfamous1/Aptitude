package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GoToClosestVillage;

public record VillagerGoToClosestVillageMaker(float speedModifier, int closeEnough) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return GoToClosestVillage.create(this.speedModifier, this.closeEnough);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_GO_TO_CLOSEST_VILLAGE.get();
    }
}
