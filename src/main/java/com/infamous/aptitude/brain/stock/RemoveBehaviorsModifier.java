package com.infamous.aptitude.brain.stock;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.mixin.BrainAccessor;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record RemoveBehaviorsModifier(HolderSet<EntityType<?>> entityTypes, Activity activity, List<Integer> priorities) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Either<Brain<?>, Brain.Provider<?>> brainOrProvider, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if(phase == Phase.ADD && this.entityTypes.contains(entityType)){
            brainOrProvider.ifLeft(brain -> {
                Map<Integer, Map<Activity, Set<BehaviorControl<?>>>> availableBehaviorsByPriority = ((BrainAccessor) brain).getAvailableBehaviorsByPriority();
                for(Integer priority : this.priorities) {
                    availableBehaviorsByPriority
                            .computeIfAbsent(priority, (p) -> Maps.newHashMap())
                            .computeIfAbsent(this.activity, (activity) -> Sets.newLinkedHashSet())
                            .clear();
                }
            });
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.REMOVE_BEHAVIORS.get();
    }
}
