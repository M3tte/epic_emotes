
package net.m3tte.epic_emotes.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.keybind.EpicEmotesKeybinds;
import net.m3tte.epic_emotes.network.EmotePackagePrefabs;
import net.m3tte.epic_emotes.systems.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.ActionAnimation;

import java.util.HashMap;

import static net.m3tte.epic_emotes.systems.EmoteSystem.clientSideOverrideMotion;

@OnlyIn(Dist.CLIENT)
public class EmoteChooseWindow extends ContainerScreen<EmoteChooseGUI.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = EmoteChooseGUI.guistate;

	private final ResourceLocation RETURN_LOC = new ResourceLocation("epic_emotes:textures/gui/returnbutton.png");
	private final ResourceLocation DEFAULT_SIT_ICON = new ResourceLocation("epic_emotes:textures/gui/sit.png");

	private static EmoteCategory selectedElement = EmoteSystem.getRootCategory();

	public static EmoteCategory getLocalSelectedCategory() {
		return selectedElement;
	}

	private Tuple<Double, Double> generatePositionFromRotation(float rotPercent) {
		Double absoluteRot = Math.PI * 2 * rotPercent;

		double x = Math.sin(absoluteRot) * 75;
		double y = Math.cos(absoluteRot) * -75;
		System.out.println("OFFS FOR "+rotPercent+" ARE : "+x+"|"+y);
		return new Tuple<>(x,y);
	}

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
	protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {

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

		if (minecraft == null)
			return;

		int centerX = minecraft.screen.width / 2;
		int centerY = minecraft.screen.height / 2;

		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		// Bottom  ( Durandal )

		if (selectedElement.getParent() != null) {
			this.addButton(new HoverButton(centerX - 24, centerY -24, 47, 47, new StringTextComponent("Return"), e -> {
				if (selectedElement.getParent() != null) {
					selectedElement = (EmoteCategory) selectedElement.getParent();
					init(minecraft, width, height);
				}
				}, RETURN_LOC, null, 0));
		}

		double steps = 1d / selectedElement.getChildren().size();

		float val = 0;

		int idx = 0;

		for (EmoteNodeElement elm : selectedElement.getChildren()) {

			Tuple<Double, Double> position = generatePositionFromRotation(val);


			this.addButton(new HoverButton((int)(centerX + position.getA() - 24), (int)(centerY + position.getB()) - 24, 47, 47, elm, e -> {
				if (elm instanceof EmoteCategory) {
					selectedElement = (EmoteCategory) elm;
					init(minecraft, width, height);
				} else if (elm instanceof ActionEmote) {

					System.out.println("SHIFT IS "+EpicEmotesKeybinds.holdingShift);

					if (EpicEmotesKeybinds.holdingShift) {
						EmoteSystem.toggleFavoriteEmote((ActionEmote) elm);
						return;
					}


					EpicEmotesMod.PACKET_HANDLER.sendToServer(new EmotePackagePrefabs.ExecuteEmotePackage(entity, ((ActionEmote) elm).getLookupIdentifier()));
				}
			}, elm.getIcon(), idx));
			val += steps;
			idx++;
		}






	}
}
