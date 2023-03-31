package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.animal.goat.Goat;

import java.util.function.Predicate;

public class GoatIsScreamingGoat implements PredicateMaker<Goat>{
    @Override
    public Predicate<Goat> make() {
        return Goat::isScreamingGoat;
    }

    @Override
    public Codec<? extends PredicateMaker<Goat>> getCodec() {
        return null;
    }
}
