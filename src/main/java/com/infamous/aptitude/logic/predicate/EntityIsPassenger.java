package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

public class EntityIsPassenger implements PredicateMaker<Entity>{
    @Override
    public Predicate<Entity> make() {
        return Entity::isPassenger;
    }

    @Override
    public Codec<? extends PredicateMaker<Entity>> getCodec() {
        return AptitudePredicateMakers.ENTITY_IS_PASSENGER.get();
    }
}
