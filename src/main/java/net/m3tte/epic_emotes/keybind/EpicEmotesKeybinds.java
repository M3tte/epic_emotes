
package net.m3tte.epic_emotes.keybind;

import io.netty.buffer.Unpooled;
import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.gui.EmoteChooseGUI;
import net.m3tte.epic_emotes.network.KeybindPackages;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class EpicEmotesKeybinds {
	@OnlyIn(Dist.CLIENT)
	private final KeyBinding openUIBind;



	public EpicEmotesKeybinds() {
		MinecraftForge.EVENT_BUS.register(this);

		this.openUIBind = new KeyBinding("key.epic_emotes.open", GLFW.GLFW_KEY_C, "key.categories.misc");
		ClientRegistry.registerKeyBinding(this.openUIBind);

	}



	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Minecraft.getInstance().screen == null) {
			if (event.getAction() == GLFW.GLFW_PRESS) {
				if (event.getKey() == this.openUIBind.getKey().getValue()) {
					EpicEmotesMod.PACKET_HANDLER.sendToServer(new KeybindPackages.GenericKeybindingPressedMessage(0, 0));


				}

			}

		}
	}


}
