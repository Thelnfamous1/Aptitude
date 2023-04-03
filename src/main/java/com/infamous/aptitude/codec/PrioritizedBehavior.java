package com.infamous.aptitude.codec;

import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.util.CodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PrioritizedBehavior(int priority, BehaviorMaker behavior) {

    public static PrioritizedBehavior of(int priority, BehaviorMaker behavior){
        return new PrioritizedBehavior(priority, behavior);
    }

    public static Codec<PrioritizedBehavior> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            CodecUtil.defineField(Codec.INT, "priority", PrioritizedBehavior::priority),
            CodecUtil.defineField(BehaviorMaker.DIRECT_CODEC, "behavior", PrioritizedBehavior::behavior)
            ).apply(builder, PrioritizedBehavior::new));
}
