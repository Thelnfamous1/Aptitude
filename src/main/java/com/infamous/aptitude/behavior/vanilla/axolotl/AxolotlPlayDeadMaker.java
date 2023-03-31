package com.infamous.aptitude.behavior.vanilla.axolotl;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.axolotl.PlayDead;

public record AxolotlPlayDeadMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new PlayDead();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.AXOLOTL_PLAY_DEAD.get();
    }
}
