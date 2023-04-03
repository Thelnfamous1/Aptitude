package com.infamous.aptitude.brain.stock;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.codec.ActivityDefinition;
import com.infamous.aptitude.codec.MemoryCheck;
import com.infamous.aptitude.mixin.BrainAccessor;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.infamous.aptitude.util.BrainUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record MakeBrainModifier(boolean replace, HolderSet<EntityType<?>> entityTypes,
                                List<MemoryModuleType<?>> memoryTypes,
                                List<SensorType<?>> sensorTypes,
                                Map<Activity, ActivityDefinition> activityDefinitions,
                                Set<Activity> coreActivities,
                                Activity defaultActivity,
                                Schedule schedule) implements BrainModifier {
    @Override
    public void modify(LivingEntity entity, Holder<EntityType<?>> entityType, Brain<?> brain, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if(phase == Phase.BEFORE_EVERYTHING && this.entityTypes.contains(entityType)){
            BrainAccessor<?> brainAccessor = (BrainAccessor<?>) brain;
            if(this.replace){
                Aptitude.LOGGER.info("Wiping Brain for {}", entity);
                builder.getMemoryTypesBuilder().get().clear();
                builder.getSensorTypesBuilder().get().clear();
                brainAccessor.getAvailableBehaviorsByPriority().clear();
                brainAccessor.getActivityRequirements().clear();
                brainAccessor.getActivityMemoriesToEraseWhenStopped().clear();
            }

            builder.getMemoryTypesBuilder().get().addAll(this.memoryTypes);
            builder.getSensorTypesBuilder().getUnchecked().addAll(this.sensorTypes);
            this.activityDefinitions.forEach(((activity, activityDefinition) ->
                    brain.addActivityAndRemoveMemoriesWhenStopped(
                            activity,
                            BehaviorMaker.makePrioritizedBehaviors(activityDefinition.prioritizedBehaviors()),
                            activityDefinition.entryConditions().stream().map(MemoryCheck::decompose).collect(Collectors.toSet()),
                            activityDefinition.exitErasedMemories())));
            if(this.replace){
                brain.setCoreActivities(this.coreActivities);
            } else{
                BrainUtil.addToCoreActivities(brain, this.coreActivities);
            }
            brain.setDefaultActivity(this.defaultActivity);
            brain.useDefaultActivity();
            brain.setSchedule(this.schedule);
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.MAKE_BRAIN.get();
    }
}
