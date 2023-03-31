package com.infamous.aptitude.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;
import java.util.function.Function;

public class CodecUtil {
    public static <O, A> RecordCodecBuilder<O, A> defineField(Codec<A> codec, String fieldName, Function<O, A> getter) {
        return codec.fieldOf(fieldName).forGetter(getter);
    }
    @SuppressWarnings("unchecked")
    public static <O, A, U> RecordCodecBuilder<O, A> defineFieldUnchecked(Codec<U> codec, String fieldName, Function<O, A> getter) {
        return ((Codec<A>) codec).fieldOf(fieldName).forGetter(getter);
    }
    public static <O, A> RecordCodecBuilder<O, Optional<A>> defineOptionalField(Codec<A> codec, String fieldName, Function<O, Optional<A>> getter) {
        return codec.optionalFieldOf(fieldName).forGetter(getter);
    }
    @SuppressWarnings("unchecked")
    public static <O, A, U> RecordCodecBuilder<O, Optional<A>> defineOptionalFieldUnchecked(Codec<U> codec, String fieldName, Function<O, Optional<A>> getter) {
        return ((Codec<A>) codec).optionalFieldOf(fieldName).forGetter(getter);
    }

}
