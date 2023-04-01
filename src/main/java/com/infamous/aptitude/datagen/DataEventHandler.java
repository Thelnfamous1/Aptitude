package com.infamous.aptitude.datagen;

import com.infamous.aptitude.Aptitude;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Aptitude.MODID)
public class DataEventHandler {

    @SubscribeEvent
    static void onDataGather(GatherDataEvent event){
        event.getGenerator().addProvider(event.includeServer(),
                (DataProvider.Factory<BrainModifiersProvider>) output -> new BrainModifiersProvider(output, event.getLookupProvider()));
    }
}
