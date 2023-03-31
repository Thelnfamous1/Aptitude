package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.monster.piglin.StopAdmiringIfItemTooFarAway;

public record StopAdmiringIfItemTooFarAwayMaker(int closeEnough) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StopAdmiringIfItemTooFarAway.create(this.closeEnough);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.STOP_ADMIRING_IF_ITEM_TOO_FAR_AWAY.get();
    }
}
