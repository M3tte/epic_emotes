//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.epic_emotes;

import net.m3tte.epic_emotes.gameasset.EpicEmotesAnimations;
import net.minecraftforge.eventbus.api.IEventBus;

public class EpicEmotesEpicFightLoader {
    public EpicEmotesEpicFightLoader() {
    }

    public static void registerStuffs(IEventBus bus) {
        bus.addListener(EpicEmotesAnimations::registerAnimations);

        EpicEmotesParticleRegistry.PARTICLES.register(bus);


    }
}
