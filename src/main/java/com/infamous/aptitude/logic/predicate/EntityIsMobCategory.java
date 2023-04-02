package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Predicate;

public record EntityIsMobCategory(MobCategory mobCategory) implements PredicateMaker<Entity>{
    @Override
    public Predicate<Entity> make() {
        return e -> e.getType().getCategory() == this.mobCategory;
    }

    @Override
    public Codec<? extends PredicateMaker<Entity>> getCodec() {
        return AptitudePredicateMakers.ENTITY_IS_MOB_CATEGORY.get();
    }
}
