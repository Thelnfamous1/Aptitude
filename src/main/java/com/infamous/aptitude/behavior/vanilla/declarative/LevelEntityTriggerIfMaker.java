package com.infamous.aptitude.behavior.vanilla.declarative;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.bipredicate.BiPredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;

public record LevelEntityTriggerIfMaker<E extends LivingEntity>(BiPredicateMaker<ServerLevel, E> levelEntityBiPredicate) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return BehaviorBuilder.triggerIf(this.levelEntityBiPredicate.make());
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.LEVEL_ENTITY_TRIGGER_IF.get();
    }
}
