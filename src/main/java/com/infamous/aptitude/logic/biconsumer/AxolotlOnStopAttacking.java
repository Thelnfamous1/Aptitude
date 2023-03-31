package com.infamous.aptitude.logic.biconsumer;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

import java.util.function.BiConsumer;

public class AxolotlOnStopAttacking implements BiConsumerMaker<Axolotl, LivingEntity> {
    @Override
    public BiConsumer<Axolotl, LivingEntity> make() {
        return Axolotl::onStopAttacking;
    }

    @Override
    public Codec<? extends BiConsumerMaker<Axolotl, LivingEntity>> getCodec() {
        return null;
    }
}
