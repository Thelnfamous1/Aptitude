package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;

public record StopBeingAngryIfTargetDeadMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StopBeingAngryIfTargetDead.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.STOP_BEING_ANGRY_IF_TARGET_DEAD.get();
    }
}
