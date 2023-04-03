package com.infamous.aptitude.brain;

import com.infamous.aptitude.brain.util.MemoryTypesBuilder;
import com.infamous.aptitude.brain.util.SensorTypesBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class ModifiableBrainInfo<E extends LivingEntity> {
    @NotNull
    private final ModifiableBrainInfo.BrainInfo<E> originalBrainInfo;
    @Nullable
    private ModifiableBrainInfo.BrainInfo<E> modifiedBrainInfo = null;

    public ModifiableBrainInfo(@NotNull final ModifiableBrainInfo.BrainInfo<E> originalBrainInfo)
    {
        this.originalBrainInfo = originalBrainInfo;
    }

    @NotNull
    public ModifiableBrainInfo.BrainInfo<E> get()
    {
        return this.modifiedBrainInfo == null
                ? originalBrainInfo
                : modifiedBrainInfo;
    }

    @NotNull
    public ModifiableBrainInfo.BrainInfo<E> getOriginalBrainInfo()
    {
        return this.originalBrainInfo;
    }

    @Nullable
    public ModifiableBrainInfo.BrainInfo<E> getModifiedBrainInfo()
    {
        return this.modifiedBrainInfo;
    }

    @ApiStatus.Internal
    public void applyBrainModifiers(final E entity, final Brain<?> brain, final List<BrainModifier> brainModifiers)
    {
        /*
        if (this.modifiedBrainInfo != null)
            throw new IllegalStateException(String.format(Locale.ENGLISH, "Brain for %s already modified", entity));

         */
       Holder<EntityType<?>> entityType = ForgeRegistries.ENTITY_TYPES.getHolder(entity.getType()).get();

        ModifiableBrainInfo.BrainInfo<E> original = this.getOriginalBrainInfo();
        final ModifiableBrainInfo.BrainInfo.Builder<E> builder = ModifiableBrainInfo.BrainInfo.Builder.copyOf(original);
        for (BrainModifier.Phase phase : BrainModifier.Phase.values())
        {
            for (BrainModifier modifier : brainModifiers)
            {
                modifier.modify(entity, entityType, brain, phase, builder);
            }
        }
        this.modifiedBrainInfo = builder.build();
    }

    public record BrainInfo<E extends LivingEntity>(Collection<MemoryModuleType<?>> memoryTypes, Collection<SensorType<Sensor<? super E>>> sensorTypes)
    {
        public static class Builder<E extends LivingEntity>
        {
            private final MemoryTypesBuilder memoryTypes;
            private final SensorTypesBuilder<E> sensorTypes;

            public static <E extends LivingEntity> ModifiableBrainInfo.BrainInfo.Builder<E> copyOf(final ModifiableBrainInfo.BrainInfo<E> original)
            {
                final MemoryTypesBuilder memoriesBuilder = MemoryTypesBuilder.copyOf(original.memoryTypes());
                final SensorTypesBuilder<E> sensorsBuilder = SensorTypesBuilder.copyOf(original.sensorTypes());

                return new ModifiableBrainInfo.BrainInfo.Builder<>(
                        memoriesBuilder,
                        sensorsBuilder
                );
            }

            private Builder(MemoryTypesBuilder memoryTypes, SensorTypesBuilder<E> sensorTypes)
            {
                this.memoryTypes = memoryTypes;
                this.sensorTypes = sensorTypes;
            }

            public ModifiableBrainInfo.BrainInfo<E> build()
            {
                return new BrainInfo<>(this.memoryTypes.get(), this.sensorTypes.get());
            }

            public MemoryTypesBuilder getMemoryTypesBuilder()
            {
                return this.memoryTypes;
            }

            public SensorTypesBuilder<E> getSensorTypesBuilder()
            {
                return this.sensorTypes;
            }
        }
    }
}
