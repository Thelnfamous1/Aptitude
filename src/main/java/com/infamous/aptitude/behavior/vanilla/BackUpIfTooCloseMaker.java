package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BackUpIfTooClose;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

public record BackUpIfTooCloseMaker(int tooClose, float strafe) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return BackUpIfTooClose.create(this.tooClose, this.strafe);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.BACK_UP_IF_TOO_CLOSE.get();
    }
}
