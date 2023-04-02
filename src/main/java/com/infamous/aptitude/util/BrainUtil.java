package com.infamous.aptitude.util;

import com.google.common.collect.ImmutableList;
import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.brain.BrainModifier;
import com.infamous.aptitude.brain.ModifiableBrainInfo;
import com.infamous.aptitude.brain.ModifiableBrainInfoProvider;
import com.infamous.aptitude.mixin.BrainAccessor;
import com.infamous.aptitude.registry.AptitudeRegistries;
import com.mojang.serialization.*;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BrainUtil {

    public static void runBrainModifiers(LivingEntity entity, Brain<?> brain, Dynamic<Tag> lastKnownBrainData) {
        MinecraftServer server = entity.getServer();
        if(server != null) runBrainModifiersOnServer(server, entity, brain, lastKnownBrainData);
    }

    private static <E extends LivingEntity> void runBrainModifiersOnServer(MinecraftServer server, E entity, Brain<?> brain, Dynamic<Tag> lastKnownBrainData){
        final RegistryAccess registries = server.registryAccess();

        // The order of holders() is the order modifiers were loaded in.
        final List<BrainModifier> brainModifiers = registries.registryOrThrow(AptitudeRegistries.Keys.BRAIN_MODIFIERS)
                .holders()
                .map(Holder::value)
                .toList();

        //noinspection unchecked
        ModifiableBrainInfo<E> modifiableBrainInfo = ((ModifiableBrainInfoProvider<E>) brain).getModifiableBrainInfo();
        // Apply sorted brain modifiers to the Brain.
        modifiableBrainInfo.applyBrainModifiers(entity, brain, brainModifiers);
        // Remake the Brain with updated memories and sensors from the modifiable brain info, and the last known brain data
        remakeBrain(brain, modifiableBrainInfo, lastKnownBrainData);
    }

    private static <E extends LivingEntity> void remakeBrain(Brain<?> brain, ModifiableBrainInfo<E> brainInfo, Dynamic<Tag> lastKnownBrainData){
        Collection<MemoryModuleType<?>> memoryTypes = brainInfo.get().memoryTypes();
        Collection<SensorType<Sensor<? super E>>> sensorTypes = brainInfo.get().sensorTypes();
        BrainAccessor<E> brainAccessor = (BrainAccessor<E>) brain;
        brainAccessor.getMemories().clear(); // wipe the original memories
        brainAccessor.getSensors().clear(); // wipe the original sensors

        brainAccessor.setCodec(() -> Brain.codec(memoryTypes, sensorTypes));

        for(MemoryModuleType<?> memoryType : memoryTypes) {
            brainAccessor.getMemories().put(memoryType, Optional.empty());
        }

        for(SensorType<? extends Sensor<? super E>> sensorType : sensorTypes) {
            brainAccessor.getSensors().put(sensorType, sensorType.create());
        }

        for(Sensor<? super E> sensor : brainAccessor.getSensors().values()) {
            for(MemoryModuleType<?> requiredMemory : sensor.requires()) {
                brainAccessor.getMemories().put(requiredMemory, Optional.empty());
            }
        }

        // "re-decode" the last known brain data, using the
        ImmutableList<MemoryValue<?>> memoryValues = decodeMemoryValues(NbtOps.INSTANCE, toMapLike(lastKnownBrainData));

        for(MemoryValue<?> memoryValue : memoryValues) {
            memoryValue.setMemoryInternal(brain);
        }
    }

    private static MapLike<Tag> toMapLike(Dynamic<Tag> lastKnownBrainData) {
        return NbtOps.INSTANCE
                .getMap(lastKnownBrainData.getValue())
                .getOrThrow(false, Aptitude.LOGGER::error);
    }

    private static <T> ImmutableList<MemoryValue<?>> decodeMemoryValues(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        MutableObject<DataResult<ImmutableList.Builder<MemoryValue<?>>>> mutableDataResult = new MutableObject<>(DataResult.success(ImmutableList.builder()));
        mapLike.entries().forEach((entry) -> {
            DataResult<MemoryModuleType<?>> memoryTypeResult = ForgeRegistries.MEMORY_MODULE_TYPES.getCodec().parse(dynamicOps, entry.getFirst());
            DataResult<? extends MemoryValue<?>> memoryValueResult = memoryTypeResult.flatMap((memoryType) -> captureRead(memoryType, dynamicOps, entry.getSecond()));
            mutableDataResult.setValue(mutableDataResult.getValue().apply2(ImmutableList.Builder::add, memoryValueResult));
        });
        return mutableDataResult.getValue().resultOrPartial(Aptitude.LOGGER::error).map(ImmutableList.Builder::build).orElseGet(ImmutableList::of);
    }

    private static <T, U> DataResult<MemoryValue<U>> captureRead(MemoryModuleType<U> memoryType, DynamicOps<T> dynamicOps, T expirableValue) {
        return memoryType.getCodec()
                .map(DataResult::success)
                .orElseGet(() -> DataResult.error("No codec for memory: " + memoryType))
                .flatMap((valueCodec) -> valueCodec.parse(dynamicOps, expirableValue))
                .map((value) -> new MemoryValue<>(memoryType, Optional.of(value)));
    }

    private record MemoryValue<U>(MemoryModuleType<U> type, Optional<? extends ExpirableValue<U>> value) {

        void setMemoryInternal(Brain<?> brain) {
                ((BrainAccessor<?>) brain).callSetMemoryInternal(this.type, this.value);
            }

    }
}
