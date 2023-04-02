package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public record ItemIsEmpty() implements PredicateMaker<ItemStack> {
    @Override
    public Predicate<ItemStack> make() {
        return ItemStack::isEmpty;
    }

    @Override
    public Codec<? extends PredicateMaker<ItemStack>> getCodec() {
        return AptitudePredicateMakers.ITEM_IS_EMPTY.get();
    }
}
