package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.animal.camel.Camel;

import java.util.function.Predicate;

public class CamelRefuseToMove implements PredicateMaker<Camel>{
    @Override
    public Predicate<Camel> make() {
        return Camel::refuseToMove;
    }

    @Override
    public Codec<? extends PredicateMaker<Camel>> getCodec() {
        return null;
    }
}
