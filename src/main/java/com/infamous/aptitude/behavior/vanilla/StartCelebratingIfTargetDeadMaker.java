package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StartCelebratingIfTargetDead;

public record StartCelebratingIfTargetDeadMaker(int duration, BiPredicateMaker<LivingEntity, LivingEntity> canCelebrate) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return StartCelebratingIfTargetDead.create(this.duration, this.canCelebrate().make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.START_CELEBRATING_IF_TARGET_DEAD.get();
    }
}
