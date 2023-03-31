package com.infamous.aptitude.behavior.vanilla.warden;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.warden.SetRoarTarget;
import net.minecraft.world.entity.monster.warden.Warden;

import java.util.Optional;

public record WardenSetRoarTargetMaker<E extends Warden>(FunctionMaker<E, Optional<? extends LivingEntity>> targetGetter) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetRoarTarget.create(this.targetGetter.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.WARDEN_SET_ROAR_TARGET.get();
    }
}
