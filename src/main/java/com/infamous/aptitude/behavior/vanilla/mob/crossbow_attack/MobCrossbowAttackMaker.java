package com.infamous.aptitude.behavior.vanilla.mob.crossbow_attack;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;

public record MobCrossbowAttackMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new CrossbowAttack<>();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_CROSSBOW_ATTACK.get();
    }
}
