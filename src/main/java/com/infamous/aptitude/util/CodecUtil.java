package com.infamous.aptitude.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class CodecUtil {
    public static <O, A> RecordCodecBuilder<O, A> defineField(Codec<A> codec, String fieldName, Function<O, A> getter) {
        return codec.fieldOf(fieldName).forGetter(getter);
    }
    public static <O, A, U> RecordCodecBuilder<O, A> defineFieldUnchecked(Codec<U> codec, String fieldName, Function<O, A> getter) {
        return ((Codec<A>) codec).fieldOf(fieldName).forGetter(getter);
    }

}
