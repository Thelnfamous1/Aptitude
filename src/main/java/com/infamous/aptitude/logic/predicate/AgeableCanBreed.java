package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.AgeableMob;

import java.util.function.Predicate;

public class AgeableCanBreed implements PredicateMaker<AgeableMob> {
    @Override
    public Predicate<AgeableMob> make() {
        return AgeableMob::canBreed;
    }

    @Override
    public Codec<? extends PredicateMaker<AgeableMob>> getCodec() {
        return null;
    }
}
