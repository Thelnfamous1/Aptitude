package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.logic.function.util.ToFloat;
import com.infamous.aptitude.logic.predicate.utility.AlwaysTrue;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.function.FunctionMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;

public record SetWalkTargetFromLookTargetMaker(PredicateMaker<LivingEntity> selfPredicate, FunctionMaker<LivingEntity, Float> speedModifier, int closeEnough) implements BehaviorMaker {

    public static SetWalkTargetFromLookTargetMaker simple(float speedModifier, int closeEnough) {
        return new SetWalkTargetFromLookTargetMaker(new AlwaysTrue<>(), new ToFloat<>(speedModifier), closeEnough);
    }

    @Override
    public BehaviorControl<?> make() {
        return SetWalkTargetFromLookTarget.create(this.selfPredicate.make(), this.speedModifier.make(), this.closeEnough);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SET_WALK_TARGET_FROM_LOOK_TARGET.get();
    }
}
