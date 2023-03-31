package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public record BlockIsTag(TagKey<Block> blockTag) implements PredicateMaker<BlockState> {
    @Override
    public Predicate<BlockState> make() {
        return bs -> bs.is(this.blockTag);
    }

    @Override
    public Codec<? extends PredicateMaker<BlockState>> getCodec() {
        return null;
    }
}
