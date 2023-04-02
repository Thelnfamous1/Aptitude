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

public record AddSensorTypesModifier(HolderSet<EntityType<?>> entityTypes, HolderSet<SensorType<?>> sensorTypes) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Brain<?> brain, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if (phase == Phase.ADD && this.entityTypes.contains(entityType))
        {
            SensorTypesBuilder<?> sensorTypesBuilder = builder.getSensorTypesBuilder();
            for (Holder<SensorType<?>> sensorType : this.sensorTypes)
            {
                sensorTypesBuilder.getUnchecked().add(sensorType.get());
            }
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.ADD_SENSOR_TYPES.get();
    }
}
