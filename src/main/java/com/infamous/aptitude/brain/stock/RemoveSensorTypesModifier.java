package com.infamous.aptitude.brain.stock;

import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.brain.util.SensorTypesBuilder;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.ForgeRegistries;

public record RemoveSensorTypesModifier(HolderSet<EntityType<?>> entityTypes, HolderSet<SensorType<?>> sensorTypes) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Brain<?> brain, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if (phase == Phase.REMOVE && this.entityTypes.contains(entityType))
        {
            SensorTypesBuilder<?> sensorTypesBuilder = builder.getSensorTypesBuilder();
            sensorTypesBuilder.get().removeIf(st -> this.sensorTypes.contains(ForgeRegistries.SENSOR_TYPES.getHolder(st).get()));
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.REMOVE_SENSOR_TYPES.get();
    }
}
