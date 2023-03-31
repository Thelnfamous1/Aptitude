package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Predicate;

public record EntityIsEntityTypeTag(TagKey<EntityType<?>> entityTypeTag) implements PredicateMaker<Entity>{
    @Override
    public Predicate<Entity> make() {
        return e -> e.getType().is(this.entityTypeTag);
    }

    @Override
    public Codec<? extends PredicateMaker<Entity>> getCodec() {
        return null;
    }
}
