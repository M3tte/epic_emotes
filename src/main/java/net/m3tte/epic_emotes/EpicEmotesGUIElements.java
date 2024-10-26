package net.m3tte.epic_emotes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;

public class EpicEmotesGUIElements {

    public static void register(IEventBus bus) {

    }

    @OnlyIn(Dist.CLIENT)
    public static void registerClient() {
        //DeferredWorkQueue.runLater(() -> ScreenManager.register(BlackSilenceSwapGUI.getContainerType(), BlackSilenceSwapWindow::new));
    }

}
