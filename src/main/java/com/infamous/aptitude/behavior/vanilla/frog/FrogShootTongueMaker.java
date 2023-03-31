package com.infamous.aptitude.behavior.vanilla.frog;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.frog.ShootTongue;

public record FrogShootTongueMaker(SoundEvent tongueSound, SoundEvent eatSound) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new ShootTongue(this.tongueSound, this.eatSound);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.FROG_SHOOT_TONGUE.get();
    }
}
