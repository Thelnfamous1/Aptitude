package com.infamous.aptitude.behavior.vanilla.warden;

import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;

public class WardenSonicBoomMaker implements BehaviorMaker {
    @Override
    public BehaviorControl<?> make() {
        return new SonicBoom();
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.WARDEN_SONIC_BOOM.get();
    }
}
