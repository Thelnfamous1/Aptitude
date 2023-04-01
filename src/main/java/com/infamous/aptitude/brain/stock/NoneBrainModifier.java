package com.infamous.aptitude.brain.stock;

import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;

public class NoneBrainModifier implements BrainModifier
{
    public static final NoneBrainModifier INSTANCE = new NoneBrainModifier();


    @Override
    public void modify(Holder<EntityType<?>> entityType, Either<Brain<?>, Brain.Provider<?>> brainOrProvider, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        // NOOP - intended for datapack makers who want to disable a brain modifier
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.NONE.get();
    }
}