package com.infamous.aptitude.brain.stock;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public record AddActivityModifier(HolderSet<EntityType<?>> entityTypes, Activity activity, List<Pair<Integer, ? extends BehaviorMaker>> prioritizedBehaviors, Set<Pair<MemoryModuleType<?>, MemoryStatus>> activityRequirements, Set<MemoryModuleType<?>> activityMemoriesToEraseWhenStopped) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Brain<?> brain, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if(phase == Phase.ADD && this.entityTypes.contains(entityType)){
            brain.addActivityAndRemoveMemoriesWhenStopped(this.activity, BehaviorMaker.makePrioritizedBehaviors(this.prioritizedBehaviors), this.activityRequirements, this.activityMemoriesToEraseWhenStopped);
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.ADD_ACTIVITY.get();
    }
}
