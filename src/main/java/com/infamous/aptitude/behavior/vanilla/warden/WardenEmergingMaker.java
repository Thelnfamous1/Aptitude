package com.infamous.aptitude.behavior.vanilla.warden;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.warden.Emerging;

public record WardenEmergingMaker(int duration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new Emerging<>(this.duration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.WARDEN_EMERGING.get();
    }
}
