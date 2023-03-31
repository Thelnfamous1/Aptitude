package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;

public record SetLookAndInteractMaker(EntityType<?> targetType, int maxDistance) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return SetLookAndInteract.create(this.targetType, this.maxDistance);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SET_LOOK_AND_INTERACT.get();
    }
}
