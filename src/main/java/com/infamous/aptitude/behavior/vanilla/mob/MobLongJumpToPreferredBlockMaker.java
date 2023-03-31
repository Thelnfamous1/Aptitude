package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LongJumpToPreferredBlock;
import net.minecraft.world.level.block.Block;

public record MobLongJumpToPreferredBlockMaker<E extends Mob>(UniformInt timeBetweenLongJumps, int maxLongJumpHeight, int maxLongJumpWidth, float maxJumpVelocity, FunctionMaker<E, SoundEvent> getJumpSound, TagKey<Block> preferredBlockTag, float preferredBlocksChance, BiPredicateMaker<E, BlockPos> acceptableLandingSpot) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new LongJumpToPreferredBlock<>(this.timeBetweenLongJumps, this.maxLongJumpHeight, this.maxLongJumpWidth, this.maxJumpVelocity, this.getJumpSound.make(), this.preferredBlockTag, this.preferredBlocksChance, this.acceptableLandingSpot.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_LONG_JUMP_TO_PREFERRED_BLOCK.get();
    }
}
