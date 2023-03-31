package com.infamous.aptitude.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.Locale;
import java.util.function.Function;

// Borrowed from Shadows-of-Fire's Placebo with their permission
public class EnumCodec<E extends Enum<E>> implements Codec<E> {
    private final Function<String, E> decoder;
    private final Function<E, String> encoder;

    public EnumCodec(Function<E, String> encoder, Function<String, E> decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public EnumCodec(Class<E> clazz) {
        this(e -> e.name().toLowerCase(Locale.ROOT), name -> Enum.valueOf(clazz, name.toUpperCase(Locale.ROOT)));
    }

    public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
        return Codec.STRING.decode(ops, input).map(pair -> Pair.of(this.decoder.apply(pair.getFirst()), pair.getSecond()));
    }

    public <T> DataResult<T> encode(E input, DynamicOps<T> ops, T prefix) {
        return Codec.STRING.encode(this.encoder.apply(input), ops, prefix);
    }
}
