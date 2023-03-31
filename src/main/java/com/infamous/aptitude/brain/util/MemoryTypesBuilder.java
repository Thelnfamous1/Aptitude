package com.infamous.aptitude.brain.util;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryTypesBuilder
{
    private final List<MemoryModuleType<?>> memoryTypes = new ArrayList<>();

    public static MemoryTypesBuilder copyOf(Collection<MemoryModuleType<?>> memories)
    {
        return new MemoryTypesBuilder(memories);
    }

    private MemoryTypesBuilder(Collection<MemoryModuleType<?>> memories)
    {
        this.memoryTypes.addAll(memories);
    }

    public List<MemoryModuleType<?>> get() {
        return memoryTypes;
    }
}