package net.m3tte.epic_emotes.systems;

import yesman.epicfight.api.animation.types.StaticAnimation;

public class RepeatingEmote extends ActionEmote {

    private StaticAnimation startAnimation = null;
    private StaticAnimation endAnimation = null;

    public RepeatingEmote(String languageKey, String lookupIdentifier, EmoteNodeElement parentElement, StaticAnimation executeAnimation) {
        super(languageKey, lookupIdentifier, parentElement, executeAnimation);
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
