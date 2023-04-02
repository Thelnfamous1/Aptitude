package com.infamous.aptitude.logic.predicate;

import com.infamous.aptitude.registry.AptitudePredicateMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;

import java.util.function.Predicate;

public record EntityTickerTickDownAndCheckMaker(SetEntityLookTargetSometimes.Ticker ticker) implements PredicateMaker<Entity>{

    @Override
    public Predicate<Entity> make() {
        return e -> this.ticker.tickDownAndCheck(e.level.getRandom());
    }

    @Override
    public Codec<? extends PredicateMaker<Entity>> getCodec() {
        return AptitudePredicateMakers.ENTITY_TICKER_TICK_DOWN_AND_CHECK.get();
    }
}
