package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;

public record MobRandomLookAroundMaker(IntProvider interval, float maxYaw, float minPitch, float maxPitch) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new RandomLookAround(this.interval, this.maxYaw, this.minPitch, this.maxPitch);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_RANDOM_LOOK_AROUND.get();
    }
}
