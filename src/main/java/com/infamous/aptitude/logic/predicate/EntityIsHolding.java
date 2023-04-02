package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public record EntityIsHolding(PredicateMaker<ItemStack> itemPredicate) implements PredicateMaker<LivingEntity> {
    @Override
    public Predicate<LivingEntity> make() {
        return le -> le.isHolding(this.itemPredicate().make());
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return AptitudePredicateMakers.ENTITY_IS_HOLDING.get();
    }
}
