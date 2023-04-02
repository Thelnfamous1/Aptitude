package com.infamous.aptitude.brain.stock;

import com.google.common.collect.ImmutableSet;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.mixin.BrainAccessor;
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
import java.util.Map;
import java.util.Set;

public record MakeBrainModifier(boolean replace, HolderSet<EntityType<?>> entityTypes,
                                Map<Activity, List<Pair<Integer, ? extends BehaviorMaker>>> prioritizedBehaviors,
                                Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> activityRequirements,
                                Map<Activity, Set<MemoryModuleType<?>>> activityMemoriesToEraseWhenStopped,
                                Set<Activity> coreActivities,
                                Activity defaultActivity) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Brain<?> brain, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if(phase == Phase.BEFORE_EVERYTHING && this.entityTypes.contains(entityType)){
            if(this.replace){
                BrainAccessor<?> brainAccessor = (BrainAccessor<?>) brain;
                brainAccessor.getAvailableBehaviorsByPriority().clear();
                brainAccessor.getActivityRequirements().clear();
                brainAccessor.getActivityMemoriesToEraseWhenStopped().clear();
            }
            for(Activity activity : this.prioritizedBehaviors.keySet()){
                brain.addActivityAndRemoveMemoriesWhenStopped(activity,
                        BehaviorMaker.makePrioritizedBehaviors(this.prioritizedBehaviors.get(activity)),
                        this.activityRequirements.getOrDefault(activity, ImmutableSet.of()),
                        this.activityMemoriesToEraseWhenStopped.getOrDefault(activity, ImmutableSet.of()));
            }
            brain.setCoreActivities(this.coreActivities);
            brain.setDefaultActivity(this.defaultActivity);
            brain.useDefaultActivity();
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.MAKE_BRAIN.get();
    }
}
