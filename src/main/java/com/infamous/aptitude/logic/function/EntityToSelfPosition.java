package com.infamous.aptitude.logic.function;

import com.infamous.aptitude.registry.AptitudeFunctionMakers;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public class EntityToSelfPosition implements FunctionMaker<Entity, Vec3> {
    @Override
    public Function<Entity, Vec3> make() {
        return Entity::position;
    }

    @Override
    public Codec<? extends FunctionMaker<Entity, Vec3>> getCodec() {
        return AptitudeFunctionMakers.ENTITY_TO_SELF_POSITION.get();
    }
}
