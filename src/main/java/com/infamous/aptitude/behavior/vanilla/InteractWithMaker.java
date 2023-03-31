package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record InteractWithMaker<E extends LivingEntity, T extends LivingEntity>(EntityType<? extends T> targetType, int maxDistance, PredicateMaker<E> checkSelf, PredicateMaker<T> checkTarget, MemoryModuleType<T> targetMemory, float speedModifier, int closeEnough) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return InteractWith.of(this.targetType, this.maxDistance, this.checkSelf.make(), this.checkTarget.make(), this.targetMemory, this.speedModifier, this.closeEnough);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.INTERACT_WITH.get();
    }
}
