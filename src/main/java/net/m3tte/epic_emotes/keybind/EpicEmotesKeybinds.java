
package net.m3tte.epic_emotes.keybind;

import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.gui.EmoteChooseGUI;
import net.m3tte.epic_emotes.gui.EmoteChooseWindow;
import net.m3tte.epic_emotes.network.EmotePackagePrefabs;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

public class EpicEmotesKeybinds {
	@OnlyIn(Dist.CLIENT)
	private final KeyBinding openUIBind;



	public EpicEmotesKeybinds() {
		MinecraftForge.EVENT_BUS.register(this);

		this.openUIBind = new KeyBinding("key.epic_emotes.open", GLFW.GLFW_KEY_C, "key.categories.misc");
		ClientRegistry.registerKeyBinding(this.openUIBind);

	}

	public static boolean holdingShift = false;

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		int key = event.getKey();
		if (Minecraft.getInstance().screen == null) {
			if (event.getAction() == GLFW.GLFW_PRESS) {
				if (key == this.openUIBind.getKey().getValue()) {
					//EpicEmotesMod.PACKET_HANDLER.sendToServer(new EmotePackagePrefabs.GenericKeybindingPressedMessage(0, 0));
					Minecraft.getInstance().setScreen(new EmoteChooseWindow(new EmoteChooseGUI.GuiContainerMod(1, Minecraft.getInstance().player.inventory, null),Minecraft.getInstance().player.inventory, new StringTextComponent("")));

				}

			}

		} else { // Exclusively for generic client side events.
			if (event.getAction() == GLFW.GLFW_PRESS) {

				if (key == GLFW.GLFW_KEY_LEFT_SHIFT)
					holdingShift = true;
			} else if (event.getAction() == GLFW.GLFW_RELEASE) {
				if (key == GLFW.GLFW_KEY_LEFT_SHIFT)
					holdingShift = false;
			}

		}
	}


}
