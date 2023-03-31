package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.TryLaySpawnOnWaterNearLand;
import net.minecraft.world.level.block.Block;

public record TryLaySpawnOnWaterNearLandMaker(Block spawnBlock) implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return TryLaySpawnOnWaterNearLand.create(this.spawnBlock);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.TRY_LAY_SPAWN_ON_WATER_NEAR_LAND.get();
    }
}
