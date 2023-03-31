package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;

import java.util.function.BiPredicate;
import java.util.function.Function;

public record MobLongJumpToRandomPosMaker<E extends Mob>(UniformInt timeBetweenLongJumps, int maxLongJumpHeight, int maxLongJumpWidth, float maxJumpVelocity, Function<E, SoundEvent> getJumpSound, BiPredicate<E, BlockPos> acceptableLandingSpot) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new LongJumpToRandomPos<>(this.timeBetweenLongJumps, this.maxLongJumpHeight, this.maxLongJumpWidth, this.maxJumpVelocity, this.getJumpSound, this.acceptableLandingSpot);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_LONG_JUMP_TO_RANDOM_POS.get();
    }
}
