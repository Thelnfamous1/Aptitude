package com.infamous.aptitude.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Piglin.class)
public interface PiglinAccessor {

    @Accessor
    static ImmutableList<MemoryModuleType<?>> getMEMORY_TYPES(){
        throw new AssertionError();
    }

    @Accessor
    static ImmutableList<SensorType<? extends Sensor<? super Piglin>>> getSENSOR_TYPES(){
        throw new AssertionError();
    }

    @Invoker
    boolean callCanHunt();
}
