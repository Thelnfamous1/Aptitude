package com.infamous.aptitude.mixin;

import net.minecraft.world.entity.monster.piglin.Piglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Piglin.class)
public interface PiglinAccessor {

    @Invoker
    boolean callCanHunt();
}
