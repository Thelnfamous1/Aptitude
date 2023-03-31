package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;

public record MobMeleeAttackMaker(int cooldown) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return MeleeAttack.create(this.cooldown);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_MELEE_ATTACK.get();
    }
}
