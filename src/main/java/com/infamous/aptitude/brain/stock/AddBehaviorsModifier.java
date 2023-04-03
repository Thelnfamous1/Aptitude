package com.infamous.aptitude.brain.stock;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.codec.PrioritizedBehavior;
import com.infamous.aptitude.mixin.BrainAccessor;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record AddBehaviorsModifier(HolderSet<EntityType<?>> entityTypes, Activity activity, List<PrioritizedBehavior> prioritizedBehaviors) implements BrainModifier {
    @Override
    public void modify(LivingEntity entity, Holder<EntityType<?>> entityType, Brain<?> brain, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if(phase == Phase.ADD && this.entityTypes.contains(entityType)){
            Map<Integer, Map<Activity, Set<BehaviorControl<?>>>> availableBehaviorsByPriority = ((BrainAccessor<?>) brain).getAvailableBehaviorsByPriority();
            for(Pair<Integer, ? extends BehaviorControl<?>> pair : BehaviorMaker.makePrioritizedBehaviors(this.prioritizedBehaviors)) {
                availableBehaviorsByPriority
                        .computeIfAbsent(pair.getFirst(), (priority) -> Maps.newHashMap())
                        .computeIfAbsent(this.activity, (activity) -> Sets.newLinkedHashSet())
                        .add(pair.getSecond());
            }
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.ADD_BEHAVIORS.get();
    }
}
