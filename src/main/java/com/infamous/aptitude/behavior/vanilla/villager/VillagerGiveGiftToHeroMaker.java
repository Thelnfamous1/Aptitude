package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;

public record VillagerGiveGiftToHeroMaker(int duration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new GiveGiftToHero(this.duration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_GIVE_GIFT_TO_HERO.get();
    }
}
