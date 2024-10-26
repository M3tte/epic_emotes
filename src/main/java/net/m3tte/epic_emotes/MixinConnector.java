package net.m3tte.epic_emotes;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        System.out.println("mixin connected");
        Mixins.addConfiguration(("epic_emotes.mixins.json"));
    }
}
