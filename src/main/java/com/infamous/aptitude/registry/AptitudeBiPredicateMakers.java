package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.codec.CustomCodecs;
import com.infamous.aptitude.logic.bipredicate.*;
import com.infamous.aptitude.logic.bipredicate.util.AllOfBiPredicateMaker;
import com.infamous.aptitude.logic.bipredicate.util.AlwaysFalseBiPredicateMaker;
import com.infamous.aptitude.logic.bipredicate.util.NegateBiPredicateMaker;
import com.infamous.aptitude.logic.bipredicate.util.PredicateSecond;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AptitudeBiPredicateMakers {

    public static final DeferredRegister<Codec<? extends BiPredicateMaker<?,?>>> BIPREDICATE_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.BIPREDICATE_MAKER_SERIALIZERS_KEY, Aptitude.MODID);

    public static final RegistryObject<Codec<AlwaysFalseBiPredicateMaker<?, ?>>> ALWAYS_FALSE = register("always_false", () ->
            Codec.unit(AlwaysFalseBiPredicateMaker::new));

    public static final RegistryObject<Codec<AllOfBiPredicateMaker<?, ?>>> ALL_OF = register("all_of", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(CustomCodecs.BIPREDICATE_MAKER_LIST, "bipredicates", AllOfBiPredicateMaker::biPredicates)
            ).apply(builder, AllOfBiPredicateMaker::new)));

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final RegistryObject<Codec<NegateBiPredicateMaker>> NEGATE = register("negate", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "bipredicate", NegateBiPredicateMaker::biPredicate)
            ).apply(builder, NegateBiPredicateMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<EntityIsValueMemoryValue>> ENTITY_IS_VALUE_MEMORY_VALUE = register("entity_is_value_memory_value", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "memory", EntityIsValueMemoryValue::memory)
            ).apply(builder, EntityIsValueMemoryValue::new)));

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final RegistryObject<Codec<OptionalResultOfFirstIsSecond>> OPTIONAL_RESULT_OF_FIRST_IS_SECOND = register("optional_result_of_first_is_second", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(FunctionMaker.DIRECT_CODEC, "optional_getter", OptionalResultOfFirstIsSecond::optionalGetter)
            ).apply(builder, OptionalResultOfFirstIsSecond::new)));

    public static final RegistryObject<Codec<PiglinlikeCanDance>> PIGLINLIKE_CAN_DANCE = register("piglinlike_can_dance", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "target_predicate", PiglinlikeCanDance::targetPredicate),
                    CodecUtil.defineField(Codec.FLOAT, "chance", PiglinlikeCanDance::chance)
            ).apply(builder, PiglinlikeCanDance::new)));

    public static final RegistryObject<Codec<PiglinlikeStopRiding>> PIGLINLIKE_STOP_RIDING = register("piglinlike_stop_riding", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(Codec.BOOL, "require_mount_is_baby", PiglinlikeStopRiding::requireMountIsBaby),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "self_predicate", PiglinlikeStopRiding::selfPredicate),
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "target_predicate", PiglinlikeStopRiding::mountPredicate),
                    CodecUtil.defineField(Codec.BOOL, "require_mount_is_stacking_passenger", PiglinlikeStopRiding::requireMountIsStackingPassenger)
            ).apply(builder, PiglinlikeStopRiding::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<PredicateSecond>> PREDICATE_SECOND = register("predicate_second", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "predicate", PredicateSecond::predicate)
            ).apply(builder, PredicateSecond::new)));

    private static <BM extends BiPredicateMaker<?,?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return BIPREDICATE_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
