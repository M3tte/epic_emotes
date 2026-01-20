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
    public static StaticAnimation SIT_FIRE_IDLE;
    public static StaticAnimation SIT_FIRE_START;
    public static StaticAnimation SIT_FIRE_END;

    public static StaticAnimation WALL_LEAN_1_IDLE;
    public static StaticAnimation WALL_LEAN_1_START;
    public static StaticAnimation WALL_LEAN_1_END;

    public static StaticAnimation WALL_LEAN_2_IDLE;
    public static StaticAnimation WALL_LEAN_2_START;
    public static StaticAnimation WALL_LEAN_2_END;

    public static StaticAnimation SORROW;
    public static StaticAnimation SURRENDER;
    public static StaticAnimation SURRENDER_START;
    public static StaticAnimation SURRENDER_END;
    public static StaticAnimation NOD_YES;
    public static StaticAnimation SHAKE_HEAD_NO;
    public static StaticAnimation SALUTE_1;
    public static StaticAnimation SALUTE_1_START;
    public static StaticAnimation SALUTE_1_END;

    public static StaticAnimation RELAXED_LAY_1_IDLE;
    public static StaticAnimation RELAXED_LAY_1_START;
    public static StaticAnimation RELAXED_LAY_1_END;

    public static StaticAnimation LAY_1_IDLE;
    public static StaticAnimation LAY_1_START;
    public static StaticAnimation LAY_1_END;
    public EpicEmotesAnimations() {
    }

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put("epic_emotes", EpicEmotesAnimations::build);
    }


    private static void build() {
        Models<?> models = FMLEnvironment.dist == Dist.CLIENT ? ClientModels.LOGICAL_CLIENT : Models.LOGICAL_SERVER;
        Model biped = models.biped;

        /***
         * SITTING TYPE ANIMATIONS
         */

        SIT_1_IDLE = new StaticAnimation(0.3f,true, "biped/emotes/sitting/sit_1", biped);
        SIT_1_START = new ActionAnimation(0.1f,1, "biped/emotes/sitting/sit_1_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SIT_1_END = new ActionAnimation(0.1f,1, "biped/emotes/sitting/sit_1_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        SIT_FIRE_IDLE = new StaticAnimation(0.3f,true, "biped/emotes/sitting/sit_fire", biped);
        SIT_FIRE_START = new ActionAnimation(0.1f,1, "biped/emotes/sitting/sit_fire_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SIT_FIRE_END = new ActionAnimation(0.1f,1, "biped/emotes/sitting/sit_fire_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);


        /***
         * STANDING TYPE ANIMATIONS
         */
        WALL_LEAN_1_IDLE = new StaticAnimation(0.3f,true, "biped/emotes/standing/wall_lean_1", biped);
        WALL_LEAN_1_START = new ActionAnimation(0.1f,1, "biped/emotes/standing/wall_lean_1_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        WALL_LEAN_1_END = new ActionAnimation(0.1f,1, "biped/emotes/standing/wall_lean_1_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        WALL_LEAN_2_IDLE = new StaticAnimation(0.3f,true, "biped/emotes/standing/wall_lean_2", biped);
        WALL_LEAN_2_START = new ActionAnimation(0.1f,1, "biped/emotes/standing/wall_lean_2_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        WALL_LEAN_2_END = new ActionAnimation(0.1f,1, "biped/emotes/standing/wall_lean_2_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        SORROW = new StaticAnimation(0.3f,true, "biped/emotes/standing/sorrow", biped);

        /***
         * GESTURE TYPE ANIMATIONS
         */
        SURRENDER = new StaticAnimation(0.3f,true, "biped/emotes/gestures/surrender", biped);
        SURRENDER_START = new ActionAnimation(0.1f,1, "biped/emotes/gestures/surrender_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SURRENDER_END = new ActionAnimation(0.1f,1, "biped/emotes/gestures/surrender_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        NOD_YES = new ActionAnimation(0.1f,1, "biped/emotes/gestures/yes", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SHAKE_HEAD_NO = new ActionAnimation(0.1f,1, "biped/emotes/gestures/no", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        SALUTE_1 = new StaticAnimation(0.3f,true, "biped/emotes/gestures/salute_1", biped);
        SALUTE_1_START = new ActionAnimation(0.1f,1, "biped/emotes/gestures/salute_1_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SALUTE_1_END = new ActionAnimation(0.1f,1, "biped/emotes/gestures/salute_1_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        /***
         * LAYING TYPE ANIMATIONS
         */
        RELAXED_LAY_1_IDLE = new StaticAnimation(0.3f,true, "biped/emotes/laying/lay_relax", biped);
        RELAXED_LAY_1_START = new ActionAnimation(0.1f,1, "biped/emotes/laying/lay_relax_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.0f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        RELAXED_LAY_1_END = new ActionAnimation(0.1f,1, "biped/emotes/laying/lay_relax_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        LAY_1_IDLE = new StaticAnimation(0.3f,true, "biped/emotes/laying/lay_1", biped);
        LAY_1_START = new ActionAnimation(0.1f,1, "biped/emotes/laying/lay_1_s", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.0f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        LAY_1_END = new ActionAnimation(0.1f,1, "biped/emotes/laying/lay_1_e", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME, new ActionAnimation.ActionTime[]{ActionAnimation.ActionTime.crate(0, 0.1f)})
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

    }




}
