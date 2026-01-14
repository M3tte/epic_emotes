
package net.m3tte.epic_emotes.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class EmoteChooseWindow extends ContainerScreen<EmoteChooseGUI.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = EmoteChooseGUI.guistate;

	private final ResourceLocation RETURN_LOC = new ResourceLocation("epic_emotes:textures/gui/returnbutton.png");
	private final ResourceLocation DEFAULT_SIT_ICON = new ResourceLocation("epic_emotes:textures/gui/sit.png");

	public EmoteChooseWindow(EmoteChooseGUI.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.x = 217;
		this.y = 173;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
		drawGuiContainerForegroundLayer(ms, mouseX, mouseY);

	}

	@Override
	protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();



		RenderSystem.disableBlend();
	}


	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void tick() {
		super.tick();
	}

	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();

	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);

		int centerX = this.getXSize() / 2;
		int centerY = this.getYSize() / 2;

		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		// Bottom  ( Durandal )

		this.addButton(new HoverButton(this.leftPos + centerX - 24, this.topPos + centerY, 47, 47, new StringTextComponent("Return"), e -> {
			if (true) {
				//EgoWeaponsMod.PACKET_HANDLER.sendToServer(new EmoteChooseGUI.ButtonPressedMessage(0, x, y, z));
				EmoteChooseGUI.handleWeaponSwap(entity, 0);
			}
		}, RETURN_LOC, null));

		this.addButton(new HoverButton(this.leftPos + centerX - 24, this.topPos + centerY + 70, 47, 47, new StringTextComponent("Sit 1"), e -> {
			if (true) {
				//EgoWeaponsMod.PACKET_HANDLER.sendToServer(new EmoteChooseGUI.ButtonPressedMessage(0, x, y, z));
				EmoteChooseGUI.handleWeaponSwap(entity, 0);
			}
		}, DEFAULT_SIT_ICON));


	}
}
