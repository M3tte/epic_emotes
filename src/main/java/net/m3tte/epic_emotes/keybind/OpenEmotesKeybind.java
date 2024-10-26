
package net.m3tte.epic_emotes.keybind;

import net.m3tte.epic_emotes.EpicEmotesMod;
import net.minecraftforge.common.MinecraftForge;
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

import java.util.stream.Stream;
import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;

public class OpenEmotesKeybind extends KeyBinding{
	@OnlyIn(Dist.CLIENT)
	public static KeyBinding key;

	@OnlyIn(Dist.CLIENT)
	public static void setupKeybind() {
		key = new KeyBinding("key.epic_emotes.open", GLFW.GLFW_KEY_C, "key.categories.misc");
		MinecraftForge.EVENT_BUS.register(key);
		ClientRegistry.registerKeyBinding(key);
	}

	public OpenEmotesKeybind(String p_i45001_1_, int p_i45001_2_, String p_i45001_3_) {
		super(p_i45001_1_, p_i45001_2_, p_i45001_3_);
	}



	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Minecraft.getInstance().screen == null) {
			if (event.getKey() == this.getKey().getValue()) {
				if (event.getAction() == GLFW.GLFW_PRESS) {
					EpicEmotesMod.PACKET_HANDLER.sendToServer(new KeyBindingPressedMessage(0, 0));
					pressAction(Minecraft.getInstance().player, 0, 0);
				}
			}
		}
	}

	public static class KeyBindingPressedMessage {
		int type, pressedms;

		public KeyBindingPressedMessage(int type, int pressedms) {
			this.type = type;
			this.pressedms = pressedms;
		}

		public KeyBindingPressedMessage(PacketBuffer buffer) {
			this.type = buffer.readInt();
			this.pressedms = buffer.readInt();
		}

		public static void buffer(KeyBindingPressedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.type);
			buffer.writeInt(message.pressedms);
		}

		public static void handler(KeyBindingPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				pressAction(context.getSender(), message.type, message.pressedms);
			});
			context.setPacketHandled(true);
		}
	}

	private static void pressAction(PlayerEntity entity, int type, int pressedms) {
		World world = entity.level;
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.isLoaded(new BlockPos(x, y, z)))
			return;
		if (type == 0) {

			System.out.println("PRESSED KEY");
		}
	}
}
