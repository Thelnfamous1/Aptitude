package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.mixin.PiglinAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.monster.piglin.Piglin;

import java.util.function.Predicate;

public class PiglinCanHunt implements PredicateMaker<Piglin>{
    @Override
    public Predicate<Piglin> make() {
        return p -> ((PiglinAccessor)p).callCanHunt();
    }

    @Override
    public Codec<? extends PredicateMaker<Piglin>> getCodec() {
        return null;
    }
}
