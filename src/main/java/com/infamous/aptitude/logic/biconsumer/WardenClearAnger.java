package com.infamous.aptitude.logic.biconsumer;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;

import java.util.function.BiConsumer;

public class WardenClearAnger implements BiConsumerMaker<Warden, LivingEntity> {

    @Override
    public BiConsumer<Warden, LivingEntity> make() {
        return Warden::clearAnger;
    }

    @Override
    public Codec<? extends BiConsumerMaker<Warden, LivingEntity>> getCodec() {
        return null;
    }
}
