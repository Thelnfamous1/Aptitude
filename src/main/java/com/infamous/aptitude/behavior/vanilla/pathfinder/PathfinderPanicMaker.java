package com.infamous.aptitude.behavior.vanilla.pathfinder;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

public record PathfinderPanicMaker(float speedMultiplier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new AnimalPanic(this.speedMultiplier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.PATHFINDER_PANIC.get();
    }
}
