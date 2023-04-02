package com.infamous.aptitude.codec;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.PairCodec;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public final class SetOfPairsCodec<F, S> implements Codec<Set<Pair<F, S>>> {

    private final PairCodec<F, S> pairCodec;

    public SetOfPairsCodec(final Codec<F> firstCodec, final Codec<S> secondCodec) {
        this.pairCodec = new PairCodec<>(firstCodec, secondCodec);
    }

    @Override
    public <T> DataResult<Pair<Set<Pair<F, S>>, T>> decode(final DynamicOps<T> ops, final T input) {
        return ops.getList(input).setLifecycle(Lifecycle.stable()).flatMap(stream -> {
            final ImmutableSet.Builder<Pair<F, S>> read = ImmutableSet.builder();
            final Stream.Builder<T> failed = Stream.builder();
            // TODO: AtomicReference.getPlain/setPlain in java9+
            final MutableObject<DataResult<Unit>> result = new MutableObject<>(DataResult.success(Unit.INSTANCE, Lifecycle.experimental()));

            stream.accept(t -> {
                final DataResult<Pair<Pair<F, S>, T>> element = this.pairCodec.decode(ops, t);
                element.error().ifPresent(e -> failed.add(t));
                result.setValue(result.getValue().apply2stable((r, v) -> {
                    read.add(v.getFirst());
                    return r;
                }, element));
            });

            final ImmutableSet<Pair<F, S>> elements = read.build();
            final T errors = ops.createList(failed.build());

            final Pair<Set<Pair<F, S>>, T> pair = Pair.of(elements, errors);

            return result.getValue().map(unit -> pair).setPartial(pair);
        });
    }

    @Override
    public <T> DataResult<T> encode(final Set<Pair<F, S>> input, final DynamicOps<T> ops, final T prefix) {
        final ListBuilder<T> builder = ops.listBuilder();

        for (final Pair<F, S> a : input) {
            builder.add(this.pairCodec.encodeStart(ops, a));
        }

        return builder.build(prefix);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SetOfPairsCodec<?, ?> that = (SetOfPairsCodec<?, ?>) o;
        return Objects.equals(this.pairCodec, that.pairCodec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pairCodec);
    }

    @Override
    public String toString() {
        return "SetOfPairsCodec[" + this.pairCodec + ']';
    }
}