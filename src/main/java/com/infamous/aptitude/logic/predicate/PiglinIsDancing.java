package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.monster.piglin.Piglin;

import java.util.function.Predicate;

public class PiglinIsDancing implements PredicateMaker<Piglin>{
    @Override
    public Predicate<Piglin> make() {
        return Piglin::isDancing;
    }

    @Override
    public Codec<? extends PredicateMaker<Piglin>> getCodec() {
        return AptitudePredicateMakers.PIGLIN_IS_DANCING.get();
    }
}
