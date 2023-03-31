package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.DoNothing;

public record DoNothingMaker(int minDuration, int maxDuration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new DoNothing(this.minDuration, this.maxDuration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.DO_NOTHING.get();
    }
}
