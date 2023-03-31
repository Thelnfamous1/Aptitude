package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

public record EntityGameTimeSeededRandomChance(float chance) implements PredicateMaker<Entity>{
    @Override
    public Predicate<Entity> make() {
        return e -> RandomSource.create(e.level.getGameTime()).nextFloat() < this.chance;
    }

    @Override
    public Codec<? extends PredicateMaker<Entity>> getCodec() {
        return null;
    }
}
