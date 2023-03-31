package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;

public record MobLongJumpMidJumpMaker(UniformInt timeBetweenLongJumps, SoundEvent landingSound) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new LongJumpMidJump(this.timeBetweenLongJumps, this.landingSound);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_LONG_JUMP_MID_JUMP.get();
    }
}
