package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.ResetRaidStatus;

public record ResetRaidStatusMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return ResetRaidStatus.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.RESET_RAID_STATUS.get();
    }
}
