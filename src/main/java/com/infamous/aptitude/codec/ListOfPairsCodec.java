package com.infamous.aptitude.codec;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.PairCodec;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class ListOfPairsCodec<F, S> implements Codec<List<Pair<F, S>>> {

    private final PairCodec<F, S> pairCodec;

    public ListOfPairsCodec(final Codec<F> firstCodec, final Codec<S> secondCodec) {
        this.pairCodec = new PairCodec<>(firstCodec, secondCodec);
    }

    @Override
    public <T> DataResult<Pair<List<Pair<F, S>>, T>> decode(final DynamicOps<T> ops, final T input) {
        return ops.getList(input).setLifecycle(Lifecycle.stable()).flatMap(stream -> {
            final ImmutableList.Builder<Pair<F, S>> read = ImmutableList.builder();
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

            final ImmutableList<Pair<F, S>> elements = read.build();
            final T errors = ops.createList(failed.build());

            final Pair<List<Pair<F, S>>, T> pair = Pair.of(elements, errors);

            return result.getValue().map(unit -> pair).setPartial(pair);
        });
    }

    @Override
    public <T> DataResult<T> encode(final List<Pair<F, S>> input, final DynamicOps<T> ops, final T prefix) {
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
        final ListOfPairsCodec<?, ?> that = (ListOfPairsCodec<?, ?>) o;
        return Objects.equals(this.pairCodec, that.pairCodec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pairCodec);
    }

    @Override
    public String toString() {
        return "ListOfPairsCodec[" + this.pairCodec + ']';
    }
}