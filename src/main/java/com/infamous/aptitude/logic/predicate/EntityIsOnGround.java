package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

public class EntityIsOnGround implements PredicateMaker<Entity>{
    @Override
    public Predicate<Entity> make() {
        return Entity::isOnGround;
    }

    @Override
    public Codec<? extends PredicateMaker<Entity>> getCodec() {
        return null;
    }
}
