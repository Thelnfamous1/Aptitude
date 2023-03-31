package com.infamous.aptitude.logic.bipredicate;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record PiglinlikeStopRiding(PredicateMaker<LivingEntity> selfPredicate, PredicateMaker<LivingEntity> mountPredicate, boolean requireMountBaby, boolean requireMountStackingPassenger) implements BiPredicateMaker<LivingEntity, Entity> {

    private static boolean wantsToStopRiding(LivingEntity passenger, Entity vehicle, boolean requireMountBaby, Predicate<LivingEntity> selfPredicate, Predicate<LivingEntity> mountPredicate, boolean requireMountStackingPassenger) {
        if (!(vehicle instanceof Mob mount)) {
            return false;
        } else {
            return requireMountBaby && !mount.isBaby()
                    || !mount.isAlive()
                    || selfPredicate.test(passenger)
                    || mountPredicate.test(mount)
                    || requireMountStackingPassenger && passenger.getClass().isInstance(mount) && mount.getVehicle() == null;
        }
    }

    @Override
    public BiPredicate<LivingEntity, Entity> make() {
        return (le, e) -> wantsToStopRiding(le, e, this.requireMountBaby, this.selfPredicate.make(), this.mountPredicate.make(), this.requireMountStackingPassenger);
    }

    @Override
    public Codec<? extends BiPredicateMaker<LivingEntity, Entity>> getCodec() {
        return null;
    }
}
