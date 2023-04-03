package com.infamous.aptitude.codec;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record WeightedBehavior(BehaviorMaker behavior, int weight) {

    public static WeightedBehavior of(BehaviorMaker behavior, int weight){
        return new WeightedBehavior(behavior, weight);
    }

    public static Codec<WeightedBehavior> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            CodecUtil.defineField(BehaviorMaker.DIRECT_CODEC, "behavior", WeightedBehavior::behavior),
            CodecUtil.defineField(Codec.INT, "weight", WeightedBehavior::weight)
            ).apply(builder, WeightedBehavior::new));
}
