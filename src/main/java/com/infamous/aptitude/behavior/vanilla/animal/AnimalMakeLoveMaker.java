package com.infamous.aptitude.behavior.vanilla.animal;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.Animal;

public record AnimalMakeLoveMaker(EntityType<? extends Animal> partnerType, float speedModifier) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new AnimalMakeLove(this.partnerType, this.speedModifier);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.ANIMAL_MAKE_LOVE.get();
    }
}
