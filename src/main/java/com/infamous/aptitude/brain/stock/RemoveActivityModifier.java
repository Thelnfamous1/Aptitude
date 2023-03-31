package com.infamous.aptitude.brain.stock;

import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.mixin.BrainAccessor;
import com.infamous.aptitude.registry.AptitudeBrainModifiers;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;

public record RemoveActivityModifier(HolderSet<EntityType<?>> entityTypes, Activity activity) implements BrainModifier {
    @Override
    public void modify(Holder<EntityType<?>> entityType, Either<Brain<?>, Brain.Provider<?>> brainOrProvider, Phase phase, ModifiableBrainInfo.BrainInfo.Builder<?> builder) {
        if(phase == Phase.ADD && this.entityTypes.contains(entityType)){
            brainOrProvider.ifLeft(brain -> {
                BrainAccessor brainAccessor = (BrainAccessor) brain;
                brainAccessor.getAvailableBehaviorsByPriority().forEach((integer, activitySetMap) -> activitySetMap.remove(this.activity));
                brainAccessor.getActivityRequirements().remove(this.activity);
                brainAccessor.getActivityMemoriesToEraseWhenStopped().remove(this.activity);
            });
        }
    }

    @Override
    public Codec<? extends BrainModifier> getCodec() {
        return AptitudeBrainModifiers.REMOVE_ACTIVITY.get();
    }
}
