package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.warden.SetWardenLookTarget;

public record SetWardenLookTargetMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetWardenLookTarget.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SET_WARDEN_LOOK_TARGET.get();
    }
}
