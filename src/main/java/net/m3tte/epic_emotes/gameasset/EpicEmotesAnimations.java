//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.epic_emotes.gameasset;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.gameasset.Models;

public class EpicEmotesAnimations {

    public static StaticAnimation SIT_1_IDLE;
    public static StaticAnimation SIT_1_START;
    public static StaticAnimation SIT_1_END;

    public EpicEmotesAnimations() {
    }

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put("epic_emotes", EpicEmotesAnimations::build);
    }


    private static void build() {
        Models<?> models = FMLEnvironment.dist == Dist.CLIENT ? ClientModels.LOGICAL_CLIENT : Models.LOGICAL_SERVER;
        Model biped = models.biped;

        SIT_1_IDLE = new StaticAnimation(0.3f,true, "biped/emotes/sit_1", biped);
        SIT_1_START = new ActionAnimation(0.3f,0, "biped/emotes/sit_1_s", biped);
        SIT_1_END = new ActionAnimation(0.3f,0, "biped/emotes/sit_1_e", biped);

    }


}
