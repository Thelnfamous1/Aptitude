package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.monster.piglin.StartAdmiringItemIfSeen;

public record StartAdmiringItemIfSeenMaker(int duration) implements BehaviorMaker {

    @Override
    public BehaviorControl<?> make() {
        return StartAdmiringItemIfSeen.create(this.duration);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.START_ADMIRING_ITEM_IF_SEEN.get();
    }
}
