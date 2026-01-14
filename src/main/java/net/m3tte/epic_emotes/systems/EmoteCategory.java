package net.m3tte.epic_emotes.systems;

import java.util.ArrayList;

public class EmoteCategory extends EmoteNodeElement {

    private ArrayList<EmoteNodeElement> children = new ArrayList<>();

    public EmoteCategory(String languageKey, EmoteNodeElement parentElement) {
        super(languageKey, parentElement);
    }

    public ArrayList<EmoteNodeElement> getChildren() {
        return children;
    }

    public void addChild(EmoteNodeElement elmn) {
        elmn.setParent(this);
        this.children.add(elmn);
    }
}
