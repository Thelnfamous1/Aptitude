package com.infamous.aptitude.behavior.vanilla.camel;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.camel.CamelAi;

public record CamelRandomSittingMaker(int minimalPoseTime) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new CamelAi.RandomSitting(this.minimalPoseTime);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.CAMEL_RANDOM_SITTING.get();
    }
}
