package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public record DigCooldownSetterMaker(int cooldown) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return BehaviorBuilder.create((instance) -> instance.group(instance.registered(MemoryModuleType.DIG_COOLDOWN)).apply(instance, (digCooldown) -> (level, entity, gameTime) -> {
            if (instance.tryGet(digCooldown).isPresent()) {
                digCooldown.setWithExpiry(Unit.INSTANCE, this.cooldown);
            }

            return true;
        }));
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.DIG_COOLDOWN_SETTER.get();
    }
}
