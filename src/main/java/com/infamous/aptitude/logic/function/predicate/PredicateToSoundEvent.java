package com.infamous.aptitude.logic.function.predicate;

import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundEvent;

public class PredicateToSoundEvent<T> extends PredicateToValue<T, SoundEvent> {
    public PredicateToSoundEvent(PredicateMaker<T> predicate, SoundEvent primary, SoundEvent secondary) {
        super(predicate, primary, secondary);
    }

    @Override
    public Codec<? extends FunctionMaker<T, SoundEvent>> getCodec() {
        return null;
    }
}
