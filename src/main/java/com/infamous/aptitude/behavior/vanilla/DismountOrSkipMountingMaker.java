package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.DismountOrSkipMounting;

public record DismountOrSkipMountingMaker<E extends LivingEntity>(int closeEnough, BiPredicateMaker<E, Entity> dismountIf) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return DismountOrSkipMounting.create(this.closeEnough, this.dismountIf.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.DISMOUNT_OR_SKIP_MOUNTING.get();
    }
}
