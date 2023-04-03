package com.infamous.aptitude.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Mixin(Brain.class)
public interface BrainAccessor<E extends LivingEntity> {
    @Accessor
    Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> getMemories();

    @Accessor
    Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> getSensors();

    @Mutable
    @Accessor
    void setCodec(Supplier<Codec<Brain<E>>> codec);

    @Invoker
    <U> void callSetMemoryInternal(MemoryModuleType<U> memory, Optional<? extends ExpirableValue<?>> value);

    @Accessor
    Map<Integer, Map<Activity, Set<BehaviorControl<?>>>> getAvailableBehaviorsByPriority();

    @Accessor
    Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> getActivityRequirements();

    @Accessor
    Map<Activity, Set<MemoryModuleType<?>>> getActivityMemoriesToEraseWhenStopped();

    @Accessor
    Set<Activity> getCoreActivities();

    @Mutable
    @Accessor
    void setCoreActivities(Set<Activity> coreActivities);

}
