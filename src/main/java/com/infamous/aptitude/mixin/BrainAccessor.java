package com.infamous.aptitude.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

@Mixin(Brain.class)
public interface BrainAccessor {

    @Accessor
    Map<Integer, Map<Activity, Set<BehaviorControl<?>>>> getAvailableBehaviorsByPriority();

    @Accessor
    Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> getActivityRequirements();

    @Accessor
    Map<Activity, Set<MemoryModuleType<?>>> getActivityMemoriesToEraseWhenStopped();

}
