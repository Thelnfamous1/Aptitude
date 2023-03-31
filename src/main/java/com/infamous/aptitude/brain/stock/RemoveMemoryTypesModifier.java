package com.infamous.aptitude.brain.stock;

import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.util.MemoryTypesBuilder;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.ForgeRegistries;

public record RemoveMemoryTypesModifier(HolderSet<EntityType<?>> entityTypes, HolderSet<MemoryModuleType<?>> memoryTypes) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Either<Brain<?>, Brain.Provider<?>> brainOrProvider, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if (phase == Phase.REMOVE && this.entityTypes.contains(entityType))
        {
            MemoryTypesBuilder memoryTypesBuilder = builder.getMemoryTypesBuilder();
            memoryTypesBuilder.get().removeIf(mt -> this.memoryTypes.contains(ForgeRegistries.MEMORY_MODULE_TYPES.getHolder(mt).get()));
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.REMOVE_MEMORY_TYPES.get();
    }
}
