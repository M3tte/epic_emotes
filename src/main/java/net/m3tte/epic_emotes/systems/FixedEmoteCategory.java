package net.m3tte.epic_emotes.systems;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class FixedEmoteCategory extends EmoteCategory {


    public FixedEmoteCategory(String languageKey, EmoteNodeElement parentElement, ResourceLocation icon, ArrayList<EmoteNodeElement> fixedArray) {
        super(languageKey, parentElement, icon);
        this.children = fixedArray;
    }

    public FixedEmoteCategory(String languageKey, ResourceLocation icon, ArrayList<EmoteNodeElement> fixedArray) {
        super(languageKey, icon);
        this.children = fixedArray;
    }

    @Override
    public EmoteNodeElement addChild(EmoteNodeElement elmn) {
        return elmn;
    }
}
