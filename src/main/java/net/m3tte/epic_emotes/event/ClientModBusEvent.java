package net.m3tte.epic_emotes.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(
        modid = "epic_emotes",
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ClientModBusEvent {

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void RenderRegistry(PatchedRenderersEvent.Add event) {

    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onParticleRegistry(final ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ParticleManager particleEngine = mc.particleEngine;

    }

}
