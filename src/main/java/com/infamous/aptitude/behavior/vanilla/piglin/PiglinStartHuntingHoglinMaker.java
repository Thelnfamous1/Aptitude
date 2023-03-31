package com.infamous.aptitude.behavior.vanilla.piglin;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.monster.piglin.StartHuntingHoglin;

public record PiglinStartHuntingHoglinMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StartHuntingHoglin.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PIGLIN_START_HUNTING_HOGLIN.get();
    }
}
