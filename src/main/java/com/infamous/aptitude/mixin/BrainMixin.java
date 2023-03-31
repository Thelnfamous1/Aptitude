package com.infamous.aptitude.mixin;

import com.google.common.collect.ImmutableList;
import com.infamous.aptitude.brain.ModifiableBrainInfoProvider;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.function.Supplier;

@Mixin(Brain.class)
public abstract class BrainMixin<E extends LivingEntity> implements ModifiableBrainInfoProvider<E> {
    private ModifiableBrainInfo<E> modifiableBrainInfo;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void handleConstruction(Collection<MemoryModuleType<?>> memories, Collection<SensorType<Sensor<? super E>>> sensors, ImmutableList memoryValues, Supplier codecSupplier, CallbackInfo ci){
        this.modifiableBrainInfo = new ModifiableBrainInfo<>(new ModifiableBrainInfo.BrainInfo<E>(memories, sensors));
    }

    @Override
    public ModifiableBrainInfo<E> getModifiableBrainInfo() {
        return this.modifiableBrainInfo;
    }
}
