package com.infamous.aptitude.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.List;
import java.util.Set;

public record ActivityDefinition(List<PrioritizedBehavior> prioritizedBehaviors, Set<MemoryCheck> entryConditions, Set<MemoryModuleType<?>> exitErasedMemories) {

    public static ActivityDefinition of(List<PrioritizedBehavior> prioritizedBehaviors, Set<MemoryCheck> entryConditions, Set<MemoryModuleType<?>> exitErasedMemories){
        return new ActivityDefinition(prioritizedBehaviors, entryConditions, exitErasedMemories);
    }

    public static Codec<ActivityDefinition> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            CustomCodecs.PRIORITIES_TO_BEHAVIORS.fieldOf(  "prioritized_behaviors").forGetter(ActivityDefinition::prioritizedBehaviors),
            CustomCodecs.ACTIVITY_REQUIREMENTS.fieldOf(  "entry_conditions").forGetter(ActivityDefinition::entryConditions),
            CustomCodecs.MEMORY_SET.fieldOf( "exit_erased_memories").forGetter(ActivityDefinition::exitErasedMemories)
    ).apply(builder, ActivityDefinition::new));
}
