package com.infamous.aptitude.brain.stock;

import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.util.MemoryTypesBuilder;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record AddMemoryTypesModifier(HolderSet<EntityType<?>> entityTypes, HolderSet<MemoryModuleType<?>> memoryTypes) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Brain<?> brain, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if (phase == Phase.ADD && this.entityTypes.contains(entityType))
        {
            MemoryTypesBuilder memoryTypesBuilder = builder.getMemoryTypesBuilder();
            for (Holder<MemoryModuleType<?>> memoryType : this.memoryTypes)
            {
                memoryTypesBuilder.get().add(memoryType.get());
            }
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.ADD_MEMORY_TYPES.get();
    }
}
