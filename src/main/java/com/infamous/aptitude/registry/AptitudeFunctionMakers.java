package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.function.PiglinlikeTargetFinder;
import com.infamous.aptitude.logic.function.util.ToFloat;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AptitudeFunctionMakers {

    public static final DeferredRegister<Codec<? extends FunctionMaker<?, ?>>> FUNCTION_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.FUNCTION_MAKER_SERIALIZERS_KEY, Aptitude.MODID);

    public static final RegistryObject<Codec<ToFloat>> TO_FLOAT = register("to_float", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(Codec.FLOAT, "value", ToFloat::value)
            ).apply(builder, ToFloat::new)));

    public static final RegistryObject<Codec<PiglinlikeTargetFinder>> PIGLINLIKE_TARGET_FINDER = register("piglinlike_target_finder", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "self_predicate", PiglinlikeTargetFinder::selfPredicate),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "primary_target_uuid_memory", PiglinlikeTargetFinder::primaryTargetUuidMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "secondary_target_enabled_memory", PiglinlikeTargetFinder::secondaryTargetEnabledMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "secondary_target_memory", PiglinlikeTargetFinder::secondaryTargetMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "tertiary_target_memory", PiglinlikeTargetFinder::tertiaryTargetMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "quaternary_target_memory", PiglinlikeTargetFinder::quaternaryTargetMemory)
            ).apply(builder, PiglinlikeTargetFinder::new)));

    private static <BM extends FunctionMaker<?, ?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return FUNCTION_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
