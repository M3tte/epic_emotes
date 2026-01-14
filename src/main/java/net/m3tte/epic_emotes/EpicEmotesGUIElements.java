package net.m3tte.epic_emotes;

import net.m3tte.epic_emotes.gui.EmoteChooseGUI;
import net.m3tte.epic_emotes.gui.EmoteChooseWindow;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;

public class EpicEmotesGUIElements {

    public static void register(IEventBus bus) {
        EmoteChooseGUI.register(bus);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerClient() {
        DeferredWorkQueue.runLater(() -> ScreenManager.register(EmoteChooseGUI.getContainerType(), EmoteChooseWindow::new));
    }

}
