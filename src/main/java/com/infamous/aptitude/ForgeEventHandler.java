package com.infamous.aptitude;

import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Aptitude.MODID)
public class ForgeEventHandler {

    @SubscribeEvent
    static void onEntityJoinLevel(EntityJoinLevelEvent event){
        if(event.getLevel().isClientSide) return;

        // TODO TESTING ONLY, DO NOT FORGET TO DELETE THIS UPON RELEASE!
        if(event.getEntity() instanceof Piglin piglin){
            piglin.setImmuneToZombification(true);
        } else if(event.getEntity() instanceof Hoglin hoglin){
            hoglin.setImmuneToZombification(true);
        }
    }
}
