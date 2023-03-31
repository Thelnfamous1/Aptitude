package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetHiddenState;

public record SetHiddenStateMaker(int time, int closeEnough) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetHiddenState.create(this.time, this.closeEnough);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SET_HIDDEN_STATE.get();
    }
}
