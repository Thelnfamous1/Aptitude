package com.infamous.aptitude.registry;

import com.infamous.aptitude.Aptitude;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.behavior.custom.CustomSetEntityLookTargetMaker;
import com.infamous.aptitude.behavior.custom.CustomStopAttackingIfTargetInvalidMaker;
import com.infamous.aptitude.logic.biconsumer.BiConsumerMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AptitudeBehaviorMakers {

    public static final DeferredRegister<Codec<? extends BehaviorMaker>> BEHAVIOR_MAKER_SERIALIZERS =
            DeferredRegister.create(AptitudeRegistries.Keys.BEHAVIOR_MAKER_SERIALIZERS_KEY, Aptitude.MODID);

    public static final RegistryObject<Codec<CustomSetEntityLookTargetMaker>> SET_ENTITY_LOOK_TARGET = register("set_entity_look_target",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(PredicateMaker.DIRECT_CODEC, "can_look_at", CustomSetEntityLookTargetMaker::canLookAt),
                    CodecUtil.defineField(Codec.FLOAT, "max_distance", CustomSetEntityLookTargetMaker::maxDistance)
            ).apply(b, CustomSetEntityLookTargetMaker::new)));

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final RegistryObject<Codec<CustomStopAttackingIfTargetInvalidMaker>> STOP_ATTACKING_IF_TARGET_INVALID = register("stop_attacking_if_target_invalid",
            () -> RecordCodecBuilder.create((b) -> b.group(
                    CodecUtil.defineFieldUnchecked(BiPredicateMaker.DIRECT_CODEC, "stop_attacking_when", CustomStopAttackingIfTargetInvalidMaker::stopAttackingWhen),
                    CodecUtil.defineFieldUnchecked(BiConsumerMaker.DIRECT_CODEC, "on_target_erased", CustomStopAttackingIfTargetInvalidMaker::onTargetErased),
                    CodecUtil.defineField(Codec.BOOL, "can_tire", CustomStopAttackingIfTargetInvalidMaker::canTire),
                    CodecUtil.defineField(Codec.INT, "timeout", CustomStopAttackingIfTargetInvalidMaker::timeout)
            ).apply(b, CustomStopAttackingIfTargetInvalidMaker::new)));

    private static <BM extends BehaviorMaker> RegistryObject<Codec<BM>> register(String id, Supplier<Codec<BM>> codecSupplier) {
        return BEHAVIOR_MAKER_SERIALIZERS.register(id, codecSupplier);
    }
}
