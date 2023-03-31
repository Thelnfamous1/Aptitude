package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.axolotl.ValidatePlayDead;

public record ValidatePlayDeadMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return ValidatePlayDead.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.VALIDATE_PLAY_DEAD.get();
    }
}
