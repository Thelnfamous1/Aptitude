package com.infamous.aptitude.logic.bipredicate;

import com.infamous.aptitude.logic.predicate.EntityIsEntityType;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.registry.AptitudeBiPredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record PiglinlikeCanDance(PredicateMaker<LivingEntity> targetPredicate, float chance) implements BiPredicateMaker<LivingEntity, LivingEntity> {
    private static final float PROBABILITY_OF_CELEBRATION_DANCE = 0.1F;

    public static PiglinlikeCanDance piglin(){
        return new PiglinlikeCanDance((PredicateMaker<LivingEntity>) (PredicateMaker<?>) new EntityIsEntityType(EntityType.HOGLIN), PROBABILITY_OF_CELEBRATION_DANCE);
    }

    private static boolean wantsToDance(LivingEntity entity, LivingEntity target, Predicate<LivingEntity> targetPredicate, float chance) {
        if (!targetPredicate.test(target)) {
            return false;
        } else {
            return RandomSource.create(entity.level.getGameTime()).nextFloat() < chance;
        }
    }

    @Override
    public BiPredicate<LivingEntity, LivingEntity> make() {
        return (e1, e2) -> wantsToDance(e1, e2, this.targetPredicate().make(), this.chance);
    }

    @Override
    public Codec<? extends BiPredicateMaker<LivingEntity, LivingEntity>> getCodec() {
        return AptitudeBiPredicateMakers.PIGLINLIKE_CAN_DANCE.get();
    }
}
