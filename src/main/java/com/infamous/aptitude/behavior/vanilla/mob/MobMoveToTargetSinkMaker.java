package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;

public record MobMoveToTargetSinkMaker(int minDuration, int maxDuration) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new MoveToTargetSink(this.minDuration, this.maxDuration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_MOVE_TO_TARGET_SINK.get();
    }
}
