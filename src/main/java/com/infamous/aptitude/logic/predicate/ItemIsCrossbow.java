package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class ItemIsCrossbow implements PredicateMaker<ItemStack> {
    @Override
    public Predicate<ItemStack> make() {
        return is -> is.getItem() instanceof CrossbowItem;
    }

    @Override
    public Codec<? extends PredicateMaker<ItemStack>> getCodec() {
        return AptitudePredicateMakers.ITEM_IS_CROSSBOW.get();
    }
}
