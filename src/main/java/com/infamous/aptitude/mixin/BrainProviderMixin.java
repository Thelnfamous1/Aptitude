package com.infamous.aptitude.mixin;

import com.infamous.aptitude.brain.ModifiableBrainInfoProvider;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(Brain.Provider.class)
public abstract class BrainProviderMixin<E extends LivingEntity> implements ModifiableBrainInfoProvider<E> {
    private ModifiableBrainInfo<E> modifiableBrainInfo;

    @Mutable
    @Shadow @Final private Codec<Brain<E>> codec;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void handleConstruction(Collection<MemoryModuleType<?>> memories, Collection<SensorType<Sensor<? super E>>> sensors, CallbackInfo ci){
        this.modifiableBrainInfo = new ModifiableBrainInfo<E>(new ModifiableBrainInfo.BrainInfo<E>(memories, sensors));
    }

    @Inject(method = "makeBrain", at = @At("HEAD"))
    private void handleMakeBrain(Dynamic<?> dynamic, CallbackInfoReturnable<Brain<?>> cir){
        ModifiableBrainInfo.BrainInfo<E> modifiedBrainInfo = this.modifiableBrainInfo.getModifiedBrainInfo();
        if(modifiedBrainInfo != null)
            this.codec = Brain.codec(modifiedBrainInfo.memoryTypes(), modifiedBrainInfo.sensorTypes());
    }

    @Override
    public ModifiableBrainInfo<E> getModifiableBrainInfo() {
        return this.modifiableBrainInfo;
    }
}
