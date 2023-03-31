package com.infamous.aptitude.logic.bipredicate;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;

import java.util.function.BiPredicate;

public class LongJumpAcceptableLandingSpot implements BiPredicateMaker<Mob, BlockPos> {

    @Override
    public BiPredicate<Mob, BlockPos> make() {
        return LongJumpToRandomPos::defaultAcceptableLandingSpot;
    }

    @Override
    public Codec<? extends BiPredicateMaker<Mob, BlockPos>> getCodec() {
        return null;
    }
}
