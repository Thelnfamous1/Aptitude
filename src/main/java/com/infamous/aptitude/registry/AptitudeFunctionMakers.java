package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.logic.function.*;
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

    public static final RegistryObject<Codec<BottomCenterOfBlockPosition>> BOTTOM_CENTER_OF_BLOCK_POSITION = register("bottom_center_of_block_position", () ->
            Codec.unit(BottomCenterOfBlockPosition::new));

    public static final RegistryObject<Codec<EntityToSelfPosition>> ENTITY_TO_SELF_POSITION = register("entity_to_self_position", () ->
            Codec.unit(EntityToSelfPosition::new));

    public static final RegistryObject<Codec<GetRandomLandPosition>> GET_RANDOM_LAND_POSITION = register("get_random_land_position", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(Codec.INT, "max_xz_distance", GetRandomLandPosition::maxXZDistance),
                    CodecUtil.defineField(Codec.INT, "max_y_distance", GetRandomLandPosition::maxYDistance)
            ).apply(builder, GetRandomLandPosition::new)));

    public static final RegistryObject<Codec<PiglinlikeFindNearestValidAttackTarget>> PIGLINLIKE_FIND_NEAREST_VALID_ATTACK_TARGET = register("piglinlike_find_nearest_valid_attack_target", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "dont_attack_if", PiglinlikeFindNearestValidAttackTarget::dontAttackIf),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "primary_target_uuid_memory", PiglinlikeFindNearestValidAttackTarget::primaryTargetUuidMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "secondary_target_enabled_memory", PiglinlikeFindNearestValidAttackTarget::secondaryTargetEnabledMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "secondary_target_memory", PiglinlikeFindNearestValidAttackTarget::secondaryTargetMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "tertiary_target_memory", PiglinlikeFindNearestValidAttackTarget::tertiaryTargetMemory),
                    CodecUtil.defineFieldUnchecked(ForgeRegistries.MEMORY_MODULE_TYPES.getCodec(), "quaternary_target_memory", PiglinlikeFindNearestValidAttackTarget::quaternaryTargetMemory)
            ).apply(builder, PiglinlikeFindNearestValidAttackTarget::new)));

    public static final RegistryObject<Codec<ToFloat>> TO_FLOAT = register("to_float", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    CodecUtil.defineField(Codec.FLOAT, "value", ToFloat::value)
            ).apply(builder, ToFloat::new)));

    private static <BM extends FunctionMaker<?, ?>> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return FUNCTION_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
