package com.infamous.aptitude.brain.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SensorTypesBuilder<E extends LivingEntity>
{
    private final List<SensorType<Sensor<? super E>>> sensorTypes = new ArrayList<>();

    public static <E extends LivingEntity> SensorTypesBuilder<E> copyOf(Collection<SensorType<Sensor<? super E>>> sensors)
    {
        return new SensorTypesBuilder<>(sensors);
    }

    private SensorTypesBuilder(Collection<SensorType<Sensor<? super E>>> sensors)
    {
        this.sensorTypes.addAll(sensors);
    }

    public List<SensorType<Sensor<? super E>>> get() {
        return sensorTypes;
    }

    public List<SensorType<?>> getUnchecked() {
        return (List<SensorType<?>>) (List<?>) sensorTypes;
    }
}