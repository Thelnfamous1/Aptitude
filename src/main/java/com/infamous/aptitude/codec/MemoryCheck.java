package com.infamous.aptitude.codec;

import com.infamous.aptitude.util.CodecUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraftforge.registries.ForgeRegistries;

public record MemoryCheck(MemoryModuleType<?> memory, MemoryStatus status){

    public static MemoryCheck of(MemoryModuleType<?> memory, MemoryStatus status){
        return new MemoryCheck(memory, status);
    }

    public Pair<MemoryModuleType<?>, MemoryStatus> decompose(){
        return Pair.of(this.memory, this.status);
    }

    public static Codec<MemoryCheck> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            CodecUtil.defineField(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "memory", MemoryCheck::memory),
            CodecUtil.defineField(CustomCodecs.MEMORY_STATUS, "status", MemoryCheck::status)
    ).apply(builder, MemoryCheck::new));
}
