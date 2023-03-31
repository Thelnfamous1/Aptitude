package com.infamous.aptitude.behavior.vanilla.warden;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.warden.Digging;

public record WardenDiggingMaker(int duration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new Digging<>(this.duration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.WARDEN_DIGGING.get();
    }
}
