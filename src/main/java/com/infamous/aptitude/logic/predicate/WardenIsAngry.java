package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.monster.warden.Warden;

import java.util.function.Predicate;

public class WardenIsAngry implements PredicateMaker<Warden>{
    @Override
    public Predicate<Warden> make() {
        return w -> w.getAngerLevel().isAngry();
    }

    @Override
    public Codec<? extends PredicateMaker<Warden>> getCodec() {
        return null;
    }
}
