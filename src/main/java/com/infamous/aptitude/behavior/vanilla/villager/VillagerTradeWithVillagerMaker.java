package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.TradeWithVillager;

public class VillagerTradeWithVillagerMaker implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new TradeWithVillager();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_TRADE_WITH_VILLAGER.get();
    }
}
