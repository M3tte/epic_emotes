package net.m3tte.epic_emotes.gameasset;

import yesman.epicfight.api.animation.LivingMotion;

public enum EELivingMotions implements LivingMotion {
    EMOTING;
    final int id;

    private EELivingMotions() {
        this.id = LivingMotion.ENUM_MANAGER.assign(this);
    }

    public int universalOrdinal() {
        return this.id;
    }
}
