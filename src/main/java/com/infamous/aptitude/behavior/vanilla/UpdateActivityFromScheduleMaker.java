package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.UpdateActivityFromSchedule;

public record UpdateActivityFromScheduleMaker() implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return UpdateActivityFromSchedule.create();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.UPDATE_ACTIVITY_FROM_SCHEDULE.get();
    }
}
