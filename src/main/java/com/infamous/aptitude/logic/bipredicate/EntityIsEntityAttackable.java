package com.infamous.aptitude.logic.bipredicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.function.BiPredicate;

public record EntityIsEntityAttackable(boolean ignoreLineOfSight) implements BiPredicateMaker<LivingEntity, LivingEntity> {
    @Override
    public BiPredicate<LivingEntity, LivingEntity> make() {
        return this.ignoreLineOfSight ? Sensor::isEntityAttackableIgnoringLineOfSight : Sensor::isEntityAttackable;
    }

    @Override
    public Codec<? extends BiPredicateMaker<LivingEntity, LivingEntity>> getCodec() {
        return null;
    }
}
