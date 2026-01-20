package net.m3tte.epic_emotes.systems;

import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class RepeatingEmote extends ActionEmote {

    private StaticAnimation startAnimation;
    private StaticAnimation endAnimation;
    private boolean cancelOnMove = true;

    public RepeatingEmote(String languageKey, String lookupIdentifier, EmoteNodeElement parentElement, StaticAnimation executeAnimation, StaticAnimation startAnimation, StaticAnimation endAnimation, ResourceLocation icon) {
        super(languageKey, lookupIdentifier, parentElement, executeAnimation, icon);
        this.startAnimation = startAnimation;
        this.endAnimation = endAnimation;
    }

    public StaticAnimation getStartAnimation() {
        return startAnimation;
    }

    public boolean isCancelOnMove() {
        return cancelOnMove;
    }

    public RepeatingEmote shouldNotCancelOnMove() {
        this.cancelOnMove = false;
        return this;
    }

    public void setStartAnimation(StaticAnimation startAnimation) {
        this.startAnimation = startAnimation;
    }

    public StaticAnimation getEndAnimation() {
        return endAnimation;
    }

    public void setEndAnimation(StaticAnimation endAnimation) {
        this.endAnimation = endAnimation;
    }
}
