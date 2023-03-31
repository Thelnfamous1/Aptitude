package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record CountDownCooldownTicksMaker(MemoryModuleType<Integer> cooldownTicks) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new CountDownCooldownTicks(this.cooldownTicks);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.COUNT_DOWN_COOLDOWN_TICKS.get();
    }
}
