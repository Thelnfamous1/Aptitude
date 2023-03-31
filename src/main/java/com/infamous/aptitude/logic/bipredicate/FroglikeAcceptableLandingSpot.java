package com.infamous.aptitude.logic.bipredicate;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.function.BiPredicate;

public record FroglikeAcceptableLandingSpot(TagKey<Block> preferJumpTo, BlockPathTypes acceptableBlockPathType) implements BiPredicateMaker<Mob, BlockPos> {

    private static <E extends Mob> boolean isAcceptableLandingSpot(E mob, BlockPos pos, TagKey<Block> preferJumpTo, BlockPathTypes blockPathType) {
        Level level = mob.level;
        BlockPos belowPos = pos.below();
        if (level.getFluidState(pos).isEmpty() && level.getFluidState(belowPos).isEmpty() && level.getFluidState(pos.above()).isEmpty()) {
            BlockState stateAtPos = level.getBlockState(pos);
            BlockState stateAtBelowPos = level.getBlockState(belowPos);
            if (!stateAtPos.is(preferJumpTo) && !stateAtBelowPos.is(preferJumpTo)) {
                BlockPathTypes pathTypeAtPos = WalkNodeEvaluator.getBlockPathTypeStatic(level, pos.mutable());
                BlockPathTypes pathTypeAtBelowPos = WalkNodeEvaluator.getBlockPathTypeStatic(level, belowPos.mutable());
                return pathTypeAtPos == blockPathType || (stateAtPos.isAir() && pathTypeAtBelowPos == blockPathType) || LongJumpToRandomPos.defaultAcceptableLandingSpot(mob, pos);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public BiPredicate<Mob, BlockPos> make() {
        return (mob, blockPos) -> isAcceptableLandingSpot(mob, blockPos, this.preferJumpTo, this.acceptableBlockPathType);
    }

    @Override
    public Codec<? extends BiPredicateMaker<Mob, BlockPos>> getCodec() {
        return null;
    }
}
