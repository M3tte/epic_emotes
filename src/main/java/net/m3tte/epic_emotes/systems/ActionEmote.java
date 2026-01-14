package net.m3tte.epic_emotes.systems;

import yesman.epicfight.api.animation.types.StaticAnimation;

public class ActionEmote extends EmoteNodeElement {

    private StaticAnimation animation = null;

    private String lookupIdentifier = "";

    public ActionEmote(String languageKey, String lookupIdentifier,EmoteNodeElement parentElement, StaticAnimation executeAnimation) {
        super(languageKey, parentElement);
        this.animation = executeAnimation;
        this.lookupIdentifier = lookupIdentifier;
    }

    public StaticAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(StaticAnimation animation) {
        this.animation = animation;
    }

    public String getLookupIdentifier() {
        return lookupIdentifier;
    }

    public void setLookupIdentifier(String lookupIdentifier) {
        this.lookupIdentifier = lookupIdentifier;
    }
}
