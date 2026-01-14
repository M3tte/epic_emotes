package net.m3tte.epic_emotes.systems;

import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class RepeatingEmote extends ActionEmote {

    private StaticAnimation startAnimation = null;
    private StaticAnimation endAnimation = null;

    public RepeatingEmote(String languageKey, String lookupIdentifier, EmoteNodeElement parentElement, StaticAnimation executeAnimation, ResourceLocation icon) {
        super(languageKey, lookupIdentifier, parentElement, executeAnimation, icon);
    }

    public RepeatingEmote(String languageKey, String lookupIdentifier, StaticAnimation executeAnimation, ResourceLocation icon) {
        super(languageKey, lookupIdentifier, null, executeAnimation, icon);
    }

    public StaticAnimation getStartAnimation() {
        return startAnimation;
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
