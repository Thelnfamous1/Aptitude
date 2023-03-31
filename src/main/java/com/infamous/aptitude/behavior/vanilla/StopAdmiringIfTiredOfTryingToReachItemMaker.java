package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.monster.piglin.StopAdmiringIfTiredOfTryingToReachItem;

public record StopAdmiringIfTiredOfTryingToReachItemMaker(int timeout, int cooldown) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StopAdmiringIfTiredOfTryingToReachItem.create(this.timeout, this.cooldown);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.STOP_ADMIRING_IF_TIRED_OF_TRYING_TO_REACH_ITEM.get();
    }
}
