package net.m3tte.epic_emotes.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModelRegisterHandler {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void registerModels(ModelRegistryEvent event) {

    }


}
