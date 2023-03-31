package com.infamous.aptitude.codec;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.mixin.TargetingConditionsAccessor;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
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
            ).apply(instance, (isCombat, range, checkLineOfSight, testInvisible, selector) -> makeTargetingConditions(isCombat, range, checkLineOfSight, testInvisible, (PredicateMaker<LivingEntity>) selector.orElse(null))));
    public static final Codec<List<Pair<BehaviorMaker, Integer>>> WEIGHTED_BEHAVIORS = Codec.compoundList(BehaviorMaker.DIRECT_CODEC, Codec.INT);
    public static final Codec<List<Pair<Integer, BehaviorMaker>>> PRIORITIZED_BEHAVIORS = Codec.compoundList(Codec.INT, BehaviorMaker.DIRECT_CODEC);
    public static final Codec<Set<MemoryModuleType<?>>> MEMORY_SET = set(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec());
    public static final Codec<MemoryStatus> MEMORY_STATUS = new EnumCodec<>(MemoryStatus.class);
    public static final UnboundedMapCodec<MemoryModuleType<?>, MemoryStatus> MEMORY_TO_STATUS_MAP = Codec.unboundedMap(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), MEMORY_STATUS);
    public static final Codec<GateBehavior.OrderPolicy> ORDER_POLICY = new EnumCodec<>(GateBehavior.OrderPolicy.class);
    public static final Codec<GateBehavior.RunningPolicy> RUNNING_POLICY = new EnumCodec<>(GateBehavior.RunningPolicy.class);
    public static final Codec<HolderSet<EntityType<?>>> ENTITY_TYPE_LIST = RegistryCodecs.homogeneousList(ForgeRegistries.Keys.ENTITY_TYPES);
    public static final Codec<HolderSet<MemoryModuleType<?>>> MEMORY_TYPE_LIST = RegistryCodecs.homogeneousList(ForgeRegistries.Keys.MEMORY_MODULE_TYPES);
    public static final Codec<HolderSet<SensorType<?>>> SENSOR_TYPE_LIST = RegistryCodecs.homogeneousList(ForgeRegistries.Keys.SENSOR_TYPES);
    public static final Codec<Set<Pair<MemoryModuleType<?>, MemoryStatus>>> ACTIVITY_REQUIREMENTS = new CompoundSetCodec<>(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), MEMORY_STATUS);
    public static final Codec<List<Integer>> INTEGER_LIST = Codec.list(Codec.INT);

    public static <E> Codec<Set<E>> set(final Codec<E> elementCodec) {
        return new SetCodec<>(elementCodec);
    }

    private static TargetingConditions makeTargetingConditions(boolean isCombat, double range, boolean checkLineOfSight, boolean testInvisible, @Nullable PredicateMaker<LivingEntity> selector){
        TargetingConditions targetingConditions = isCombat ? TargetingConditions.forCombat() : TargetingConditions.forNonCombat();
        targetingConditions.range(range);
        if(!checkLineOfSight) targetingConditions.ignoreLineOfSight();
        if(!testInvisible) targetingConditions.ignoreInvisibilityTesting();
        if(selector != null) targetingConditions.selector(selector.make());
        return targetingConditions;
    }

}
