package com.infamous.aptitude.logic.function;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;

import java.util.Optional;
import java.util.function.Function;

public class WardenGetEntityAngryAt implements FunctionMaker<Warden, Optional<LivingEntity>>{
    @Override
    public Function<Warden, Optional<LivingEntity>> make() {
        return Warden::getEntityAngryAt;
    }

    @Override
    public Codec<? extends FunctionMaker<Warden, Optional<LivingEntity>>> getCodec() {
        return null;
    }
}
