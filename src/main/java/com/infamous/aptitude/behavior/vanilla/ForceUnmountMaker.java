package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.warden.ForceUnmount;

public record ForceUnmountMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new ForceUnmount();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.FORCE_UNMOUNT.get();
    }
}
