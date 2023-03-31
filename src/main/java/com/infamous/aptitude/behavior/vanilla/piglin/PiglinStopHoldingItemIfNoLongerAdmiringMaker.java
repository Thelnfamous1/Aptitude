package com.infamous.aptitude.behavior.vanilla.piglin;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.monster.piglin.StopHoldingItemIfNoLongerAdmiring;

public record PiglinStopHoldingItemIfNoLongerAdmiringMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StopHoldingItemIfNoLongerAdmiring.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PIGLIN_STOP_HOLDING_ITEM_IF_NO_LONGER_ADMIRING.get();
    }
}
