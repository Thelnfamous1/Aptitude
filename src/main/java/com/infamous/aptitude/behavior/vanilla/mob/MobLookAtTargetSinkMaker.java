package com.infamous.aptitude.behavior.vanilla.mob;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;

public record MobLookAtTargetSinkMaker(int minDuration, int maxDuration) implements BehaviorMaker {

    public static MobLookAtTargetSinkMaker simple(){
        return new MobLookAtTargetSinkMaker(45, 90);
    }

    @Override
    public BehaviorControl<?> make() {
        return new LookAtTargetSink(this.minDuration, this.maxDuration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.MOB_LOOK_AT_TARGET_SINK.get();
    }
}
