package com.infamous.aptitude.logic.bipredicate;

import com.infamous.aptitude.logic.predicate.EntityCheckMemory;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.registry.AptitudeBiPredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record PiglinlikeStopRiding(boolean requireMountIsBaby, PredicateMaker<LivingEntity> selfPredicate, PredicateMaker<LivingEntity> mountPredicate, boolean requireMountIsStackingPassenger) implements BiPredicateMaker<LivingEntity, Entity> {

    public static PiglinlikeStopRiding piglin(){
        return new PiglinlikeStopRiding(true, wasHurtRecently(), wasHurtRecently(), true);
    }

    private static EntityCheckMemory wasHurtRecently() {
        return new EntityCheckMemory(MemoryModuleType.HURT_BY, MemoryStatus.VALUE_PRESENT);
    }

    private static boolean wantsToStopRiding(LivingEntity passenger, Entity vehicle, boolean requireMountIsBaby, Predicate<LivingEntity> selfPredicate, Predicate<LivingEntity> mountPredicate, boolean requireMountIsStackingPassenger) {
        if (!(vehicle instanceof Mob mount)) {
            return false;
        } else {
            return requireMountIsBaby && !mount.isBaby()
                    || !mount.isAlive()
                    || selfPredicate.test(passenger)
                    || mountPredicate.test(mount)
                    || requireMountIsStackingPassenger && passenger.getClass().isInstance(mount) && mount.getVehicle() == null;
        }
    }

    @Override
    public BiPredicate<LivingEntity, Entity> make() {
        return (le, e) -> wantsToStopRiding(le, e, this.requireMountIsBaby, this.selfPredicate.make(), this.mountPredicate.make(), this.requireMountIsStackingPassenger);
    }

    @Override
    public Codec<? extends BiPredicateMaker<LivingEntity, Entity>> getCodec() {
        return AptitudeBiPredicateMakers.PIGLINLIKE_STOP_RIDING.get();
    }
}
