package com.infamous.aptitude.behavior.vanilla.goat;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.RamTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.goat.Goat;

public record GoatRamTargetMaker(FunctionMaker<Goat, UniformInt> getTimeBetweenRams, TargetingConditions ramTargeting, float speed, FunctionMaker<Goat, Double> getKnockbackForce, FunctionMaker<Goat, SoundEvent> getImpactSound, FunctionMaker<Goat, SoundEvent> getHornBreakSound) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new RamTarget(this.getTimeBetweenRams.make(), this.ramTargeting, this.speed, this.getKnockbackForce.make()::apply, this.getImpactSound.make(), this.getHornBreakSound.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.GOAT_RAM_TARGET.get();
    }
}
