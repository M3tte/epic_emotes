package net.m3tte.epic_emotes.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.systems.ActionEmote;
import net.m3tte.epic_emotes.systems.EmoteNodeElement;
import net.m3tte.epic_emotes.systems.EmoteSystem;
import net.m3tte.epic_emotes.systems.RepeatingEmote;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class HoverButton extends Button {

    private EmoteNodeElement linkedEmote = null;
    int iterationIndex = 0;

    public HoverButton(int x, int y, int width, int height, EmoteNodeElement linkedEmote, IPressable pressedAction, ResourceLocation buttonImage, ResourceLocation iconImage, int index) {
        super(x, y, width, height, new TranslationTextComponent(linkedEmote.getLanguageKey()), pressedAction);
        this.BUTTON_RESOURCE_LOCATION = buttonImage;
        this.ICON_RESOURCE_LOCATION = iconImage;
        this.linkedEmote = linkedEmote;
        this.iterationIndex = index;
    }

    public HoverButton(int x, int y, int width, int height, EmoteNodeElement linkedEmote, IPressable pressedAction, ResourceLocation iconImage, int index) {
        super(x, y, width, height, new TranslationTextComponent(linkedEmote.getLanguageKey()), pressedAction);
        this.ICON_RESOURCE_LOCATION = iconImage;
        this.linkedEmote = linkedEmote;
        this.iterationIndex = index;
    }

    public HoverButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ResourceLocation buttonImage, ResourceLocation iconImage, int index) {
        super(x, y, width, height, title, pressedAction);
        this.BUTTON_RESOURCE_LOCATION = buttonImage;
        this.ICON_RESOURCE_LOCATION = iconImage;
        this.iterationIndex = index;
    }

    public HoverButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ResourceLocation iconImage, int index) {
        super(x, y, width, height, title, pressedAction);
        this.ICON_RESOURCE_LOCATION = iconImage;
        this.iterationIndex = index;
    }

    public HoverButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ITooltip onTooltip) {
        super(x, y, width, height, title, pressedAction, onTooltip);
    }


    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        delegatedButtonRender(matrixStack, mouseX, mouseY, partialTicks);
    }

    private ResourceLocation BUTTON_RESOURCE_LOCATION = new ResourceLocation("epic_emotes:textures/gui/hoverbutton.png");
    private ResourceLocation ICON_RESOURCE_LOCATION = null;

    private final ResourceLocation REPEAT_ANIMATION_ICON = new ResourceLocation("epic_emotes:textures/gui/repeating_animation_type.png");
    private final ResourceLocation FOLDER_ANIMATION_ICON = new ResourceLocation("epic_emotes:textures/gui/folder_type.png");
    private final ResourceLocation ACTION_ANIMATION_ICON = new ResourceLocation("epic_emotes:textures/gui/action_animation_type.png");
    private final ResourceLocation REPEAT_ANIMATION_SPINNER = new ResourceLocation("epic_emotes:textures/gui/repeating.png");
    private final ResourceLocation FAVORITE_BUTTON = new ResourceLocation("epic_emotes:textures/gui/favorite_star.png");

    private float hoverTime = 0;

    public void delegatedButtonRender(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(BUTTON_RESOURCE_LOCATION);

        if (this.isHovered && hoverTime < 10) {
            hoverTime += 0.8f;
        } else if (hoverTime > 0 && !this.isHovered) {
            hoverTime -= 0.9f;
        } else if (hoverTime < 0){
            hoverTime = 0;
        }
        //System.out.println("HoverTime: "+hoverTime);

        RenderSystem.pushMatrix();
        RenderSystem.translated(this.x, this.y, 0);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.heightMultiplier();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, 0,0, 0, 0, 47, 47, 47, 94);

        if (hoverTime > 0) {
            this.blit(matrixStack, (int)(47f/2 * (1-hoverTime / 10)), 0, (int)(47f/2 * (1-hoverTime / 10)), 47, (int)(47f * (hoverTime / 10)), 47, 47, 94);
        }

        if (this.ICON_RESOURCE_LOCATION != null) {
            minecraft.getTextureManager().bind(ICON_RESOURCE_LOCATION);
            RenderSystem.pushMatrix();
            RenderSystem.scaled(0.8f, 0.8f, 0.8f);
            this.blit(matrixStack, 6,6, 0, 0, 47, 47, 47, 47);

            RenderSystem.popMatrix();
        }

        if (this.linkedEmote != null) {

            ResourceLocation locSave = null;

            if (this.linkedEmote instanceof RepeatingEmote)
                locSave = REPEAT_ANIMATION_ICON;
            else if (this.linkedEmote instanceof ActionEmote)
                locSave = ACTION_ANIMATION_ICON;
            else
                locSave = FOLDER_ANIMATION_ICON;

            minecraft.getTextureManager().bind(locSave);
            RenderSystem.pushMatrix();
            RenderSystem.scaled(0.8f, 0.8f, 0.8f);
            this.blit(matrixStack, 6,6, 0, 0, 47, 47, 47, 47);

            if (EmoteSystem.getFavoriteEmotes().contains(this.linkedEmote)) {
                minecraft.getTextureManager().bind(FAVORITE_BUTTON);
                this.blit(matrixStack, 6,6, 0, 0, 47, 47, 47, 47);

            }
            
            
            RenderSystem.popMatrix();

            EpicEmotesModVariables.PlayerVariables vars = EmoteSystem.getDataForPlayer(minecraft.player);

            if (this.linkedEmote instanceof RepeatingEmote) {
                if (Objects.equals(vars.currEmote, ((RepeatingEmote)this.linkedEmote).getLookupIdentifier())) {
                    minecraft.getTextureManager().bind(REPEAT_ANIMATION_SPINNER);
                    RenderSystem.pushMatrix();
                    RenderSystem.translated(23.5, 23.5, 0);
                    GL11.glPushMatrix();

                    float timeBetween = minecraft.player.tickCount - vars.emoteStart + partialTicks;

                    float scale = Math.min(timeBetween / 10, 1);

                    GL11.glScaled(scale * 1.2f,scale * 1.2f,scale * 1.2f);
                    RenderSystem.color4f(1, 1, 1, (float) Math.min(scale, Math.sin((minecraft.player.tickCount + partialTicks) / 6) * 0.25f + 0.4f));
                    GL11.glRotated(-(minecraft.player.tickCount + partialTicks) * 3, 0, 0, 0.001);
                    this.blit(matrixStack, -23,-23, 0, 0, 47, 47, 47, 47);
                    GL11.glPopMatrix();

                    RenderSystem.popMatrix();
                }
            }

        }

        this.renderBg(matrixStack, minecraft, mouseX, mouseY);

        if (minecraft.player == null)
            return;

        int j = getFGColor();
        RenderSystem.pushMatrix();
        float scale = Math.min(1f,(hoverTime - 5) / 15f) * 2;
        RenderSystem.translated(0 + this.width / 2f, (0 + 16 + (this.height - 8) / 2 + Math.sin(((minecraft.player.tickCount + partialTicks) / 10f))), 0);
        RenderSystem.pushMatrix();
        RenderSystem.scaled(scale, scale, scale);
        if (hoverTime >= 5) {
            drawCenteredString(matrixStack, fontrenderer, this.getMessage(), 0,0, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
        }
        RenderSystem.popMatrix();
        RenderSystem.popMatrix();
        RenderSystem.popMatrix();
    }

    public int heightMultiplier() {
        return 0;
    }
}
