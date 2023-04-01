package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public record EntityCheckItemInSlot(EquipmentSlot slot, PredicateMaker<ItemStack> itemPredicate) implements PredicateMaker<LivingEntity> {
    @Override
    public Predicate<LivingEntity> make() {
        return le -> this.itemPredicate().make().test(le.getItemBySlot(this.slot));
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return null;
    }
}
