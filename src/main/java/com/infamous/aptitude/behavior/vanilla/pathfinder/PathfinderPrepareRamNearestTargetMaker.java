package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.PrepareRamNearestTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public record PathfinderPrepareRamNearestTargetMaker<E extends PathfinderMob>(FunctionMaker<E, Integer> getCooldownOnFail, int minRamDistance, int maxRamDistance, float walkSpeed, TargetingConditions ramTargeting, int ramPrepareTime, FunctionMaker<E, SoundEvent> getPrepareRamSound) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new PrepareRamNearestTarget<>(this.getCooldownOnFail.make()::apply, this.minRamDistance, this.maxRamDistance, this.walkSpeed, this.ramTargeting, this.ramPrepareTime, this.getPrepareRamSound.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_PREPARE_RAM_NEAREST_TARGET.get();
    }
}
