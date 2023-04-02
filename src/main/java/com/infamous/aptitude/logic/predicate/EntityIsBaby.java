package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Predicate;

public class EntityIsBaby implements PredicateMaker<LivingEntity> {
    @Override
    public Predicate<LivingEntity> make() {
        return LivingEntity::isBaby;
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return AptitudePredicateMakers.ENTITY_IS_BABY.get();
    }
}
