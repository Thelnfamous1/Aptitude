package com.infamous.aptitude.behavior.vanilla.villager;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.ShowTradesToPlayer;

public record VillagerShowTradesToPlayerMaker(int minDuration, int maxDuration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new ShowTradesToPlayer(this.minDuration, this.maxDuration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VILLAGER_SHOW_TRADES_TO_PLAYER.get();
    }
}
