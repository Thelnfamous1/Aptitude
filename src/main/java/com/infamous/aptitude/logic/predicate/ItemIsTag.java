package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public record ItemIsTag(TagKey<Item> itemTag) implements PredicateMaker<ItemStack> {
    @Override
    public Predicate<ItemStack> make() {
        return is -> is.is(this.itemTag);
    }

    @Override
    public Codec<? extends PredicateMaker<ItemStack>> getCodec() {
        return AptitudePredicateMakers.ITEM_IS_TAG.get();
    }
}
