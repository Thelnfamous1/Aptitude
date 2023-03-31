package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.monster.piglin.RememberIfHoglinWasKilled;

public record RememberIfHoglinWasKilledMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return RememberIfHoglinWasKilled.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.REMEMBER_IF_HOGLIN_WAS_KILLED.get();
    }
}
