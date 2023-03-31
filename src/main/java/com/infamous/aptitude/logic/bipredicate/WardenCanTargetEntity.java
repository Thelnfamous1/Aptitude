package com.infamous.aptitude.logic.bipredicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;

import java.util.function.BiPredicate;

public class WardenCanTargetEntity implements BiPredicateMaker<Warden, LivingEntity> {
    @Override
    public BiPredicate<Warden, LivingEntity> make() {
        return Warden::canTargetEntity;
    }

    @Override
    public Codec<? extends BiPredicateMaker<Warden, LivingEntity>> getCodec() {
        return null;
    }
}
