package net.m3tte.epic_emotes.systems;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class EmoteCategory extends EmoteNodeElement {

    private ArrayList<EmoteNodeElement> children = new ArrayList<>();

    public EmoteCategory(String languageKey, EmoteNodeElement parentElement, ResourceLocation icon) {
        super(languageKey, parentElement, icon);
    }

    public EmoteCategory(String languageKey, ResourceLocation icon) {
        super(languageKey, null, icon);
    }

    public ArrayList<EmoteNodeElement> getChildren() {
        return children;
    }

    public EmoteNodeElement addChild(EmoteNodeElement elmn) {
        elmn.setParent(this);
        this.children.add(elmn);
        return elmn;
    }
}
