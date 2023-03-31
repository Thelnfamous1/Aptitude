package com.infamous.aptitude.logic.bipredicate;

import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;

import java.util.function.BiPredicate;

public class RaidExistsAndVictory implements BiPredicateMaker<ServerLevel, LivingEntity> {

    private static boolean check(ServerLevel level, LivingEntity entity) {
        Raid raid = level.getRaidAt(entity.blockPosition());
        return raid != null && raid.isVictory();
    }

    @Override
    public BiPredicate<ServerLevel, LivingEntity> make() {
        return RaidExistsAndVictory::check;
    }

    @Override
    public Codec<? extends BiPredicateMaker<ServerLevel, LivingEntity>> getCodec() {
        return null;
    }
}
