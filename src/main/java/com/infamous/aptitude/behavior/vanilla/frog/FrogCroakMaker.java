package com.infamous.aptitude.behavior.vanilla.frog;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.Croak;

public record FrogCroakMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new Croak();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.FROG_CROAK.get();
    }
}
