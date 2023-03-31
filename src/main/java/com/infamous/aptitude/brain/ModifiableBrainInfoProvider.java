package com.infamous.aptitude.brain;

import net.minecraft.world.entity.LivingEntity;

public interface ModifiableBrainInfoProvider<E extends LivingEntity> {

    ModifiableBrainInfo<E> getModifiableBrainInfo();
}
