package net.m3tte.epic_emotes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.HashMap;
import java.util.Map;

public class EpicEmotesSounds {

    private static ResourceLocation locationFrom(String loc) {
        return new ResourceLocation(EpicEmotesMod.MODID, loc);
    }

    public static Map<ResourceLocation, SoundEvent> SOUNDS = new HashMap<>();

    private static SoundEvent generateSoundEvent(ResourceLocation location) {
        SoundEvent event = new SoundEvent(location);
        SOUNDS.put(location, event);
        return event;
    }

}
