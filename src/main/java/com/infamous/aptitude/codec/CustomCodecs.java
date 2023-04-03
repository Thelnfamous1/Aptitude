package com.infamous.aptitude.codec;

import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.mixin.TargetingConditionsAccessor;
import com.infamous.aptitude.mixin.TickerAccessor;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CustomCodecs {

    @SuppressWarnings("unchecked")
    public static final Codec<TargetingConditions> TARGETING_CONDITIONS = RecordCodecBuilder.create((instance) -> instance.group(
            CodecUtil.defineField(Codec.BOOL, "is_combat", tc -> ((TargetingConditionsAccessor)tc).getIsCombat()),
            CodecUtil.defineField(Codec.DOUBLE, "range", tc -> ((TargetingConditionsAccessor)tc).getRange()),
            CodecUtil.defineField(Codec.BOOL, "check_line_of_sight", tc -> ((TargetingConditionsAccessor)tc).getCheckLineOfSight()),
            CodecUtil.defineField(Codec.BOOL, "test_invisible", tc -> ((TargetingConditionsAccessor)tc).getTestInvisible()),
            CodecUtil.defineOptionalFieldUnchecked(PredicateMaker.DIRECT_CODEC, "selector", tc -> Optional.empty()) // TargetingConditions don't store a PredicateMaker
            ).apply(instance, (isCombat, range, checkLineOfSight, testInvisible, selector) -> makeTargetingConditions(isCombat, range, checkLineOfSight, testInvisible, selector.map(PredicateMaker.class::cast).orElse(null))));
    public static final Codec<List<WeightedBehavior>> BEHAVIORS_TO_WEIGHT = new ListCodec<>(WeightedBehavior.CODEC);
    public static final Codec<List<PrioritizedBehavior>> PRIORITIES_TO_BEHAVIORS = new ListCodec<>(PrioritizedBehavior.CODEC);
    public static final Codec<Set<MemoryModuleType<?>>> MEMORY_SET = new SetCodec<>(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec());
    public static final Codec<MemoryStatus> MEMORY_STATUS = new EnumCodec<>(MemoryStatus.class);
    public static final SimpleMapCodec<MemoryModuleType<?>, MemoryStatus> MEMORY_TO_STATUS_MAP = Codec.simpleMap(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), MEMORY_STATUS, BuiltInRegistries.MEMORY_MODULE_TYPE);
    public static final Codec<GateBehavior.OrderPolicy> ORDER_POLICY = new EnumCodec<>(GateBehavior.OrderPolicy.class);
    public static final Codec<GateBehavior.RunningPolicy> RUNNING_POLICY = new EnumCodec<>(GateBehavior.RunningPolicy.class);
    public static final Codec<HolderSet<EntityType<?>>> ENTITY_TYPE_LIST = RegistryCodecs.homogeneousList(ForgeRegistries.Keys.ENTITY_TYPES);
    public static final Codec<HolderSet<MemoryModuleType<?>>> MEMORY_TYPE_HOLDER_SET = RegistryCodecs.homogeneousList(ForgeRegistries.Keys.MEMORY_MODULE_TYPES);
    public static final Codec<HolderSet<SensorType<?>>> SENSOR_TYPE_HOLDER_SET = RegistryCodecs.homogeneousList(ForgeRegistries.Keys.SENSOR_TYPES);
    public static final Codec<Set<MemoryCheck>> ACTIVITY_REQUIREMENTS = new SetCodec<>(MemoryCheck.CODEC);
    public static final Codec<List<Integer>> INTEGER_LIST = Codec.list(Codec.INT);
    public static final Codec<List<BiPredicateMaker<?, ?>>> BIPREDICATE_MAKER_LIST = Codec.list(BiPredicateMaker.DIRECT_CODEC);
    public static final Codec<List<PredicateMaker<?>>> PREDICATE_MAKER_LIST = Codec.list(PredicateMaker.DIRECT_CODEC);
    public static final Codec<EquipmentSlot> EQUIPMENT_SLOT = new EnumCodec<>(EquipmentSlot.class);

    public static final Codec<SetEntityLookTargetSometimes.Ticker> TICKER = RecordCodecBuilder.create(instance -> instance
            .group(
                    CodecUtil.defineField(UniformInt.CODEC, "interval", t -> ((TickerAccessor)(Object)t).getInterval()))
            .apply(instance, SetEntityLookTargetSometimes.Ticker::new)
    );
    public static final Codec<List<MemoryModuleType<?>>> MEMORY_TYPE_LIST = Codec.list(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec());
    public static final Codec<List<SensorType<?>>> SENSOR_TYPE_LIST = Codec.list(ForgeRegistries.SENSOR_TYPES.getCodec());

    private static TargetingConditions makeTargetingConditions(boolean isCombat, double range, boolean checkLineOfSight, boolean testInvisible, @Nullable PredicateMaker<LivingEntity> selector){
        TargetingConditions targetingConditions = isCombat ? TargetingConditions.forCombat() : TargetingConditions.forNonCombat();
        targetingConditions.range(range);
        if(!checkLineOfSight) targetingConditions.ignoreLineOfSight();
        if(!testInvisible) targetingConditions.ignoreInvisibilityTesting();
        if(selector != null) targetingConditions.selector(selector.make());
        return targetingConditions;
    }

}
