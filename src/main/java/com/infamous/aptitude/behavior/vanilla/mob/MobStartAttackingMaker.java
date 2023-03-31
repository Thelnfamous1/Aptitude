package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StartAttacking;

import java.util.Optional;

public record MobStartAttackingMaker<E extends Mob>(PredicateMaker<E> selfPredicate, FunctionMaker<E, Optional<? extends LivingEntity>> targetFinder) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StartAttacking.create(this.selfPredicate.make(), this.targetFinder.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_START_ATTACKING.get();
    }
}
