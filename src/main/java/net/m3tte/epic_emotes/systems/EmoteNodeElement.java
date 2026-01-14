package net.m3tte.epic_emotes.systems;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.LanguageMap;

public class EmoteNodeElement {

    private ResourceLocation icon = null;

    public ResourceLocation getIcon() {
        return icon;
    }

    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }

    public EmoteNodeElement(String languageKey, EmoteNodeElement parentElement, ResourceLocation icon) {
        this.languageKey = languageKey;
        this.parentElement = parentElement;
        this.icon = icon;
    }


    public String getLanguageKey() {
        return languageKey;
    }

    public void setLanguageKey(String languageKey) {
        this.languageKey = languageKey;
    }

    public void setParent(EmoteNodeElement parentElement) {
        this.parentElement = parentElement;
    }

    private String languageKey = "";

    @Override
    public String toString() {
        return LanguageMap.getInstance().getOrDefault(languageKey);
    }
    EmoteNodeElement parentElement = null;

    public EmoteNodeElement getParent() {
        return parentElement;
    }
}
