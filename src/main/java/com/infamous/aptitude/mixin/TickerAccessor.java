package com.infamous.aptitude.mixin;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetEntityLookTargetSometimes.Ticker.class)
public interface TickerAccessor {

    @Accessor
    UniformInt getInterval();
}
