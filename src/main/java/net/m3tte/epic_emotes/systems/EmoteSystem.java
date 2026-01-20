package net.m3tte.epic_emotes.systems;

import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.gameasset.EELivingMotions;
import net.m3tte.epic_emotes.gameasset.EpicEmotesAnimations;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static net.m3tte.epic_emotes.EpicEmotesModVariables.EMOTE_CAPABILITY;

public class EmoteSystem {
    private static final ResourceLocation DEFAULT_SIT_ICON = new ResourceLocation("epic_emotes:textures/gui/sit.png");
    private static final ResourceLocation FAVORITE_ICON = new ResourceLocation("epic_emotes:textures/gui/favorites.png");

    private static String PREFIX = "gui.epic_emotes.";

    private static HashMap<String, ActionEmote> emoteTable = null;

    private static ArrayList<EmoteNodeElement> favoriteEmotes = new ArrayList<>();

    public static void toggleFavoriteEmote(ActionEmote emote) {
        if (favoriteEmotes.contains(emote)) {
            favoriteEmotes.remove(emote);
        } else {
            favoriteEmotes.add(emote);
        }
    }

    public static ArrayList<EmoteNodeElement> getFavoriteEmotes() {
        return favoriteEmotes;
    }

    private static EmoteCategory rootEmote = null;

    private static ActionEmote registerEmote(ActionEmote emote) {

        emoteTable.put(emote.getLookupIdentifier(), emote);

        return emote;
    }

    public static String getEmoteForPlayer(PlayerEntity player) {
        EpicEmotesModVariables.PlayerVariables entityData = player.getCapability(EMOTE_CAPABILITY, null).orElse(null);

        return entityData.currEmote;
    }

    public static EpicEmotesModVariables.PlayerVariables getDataForPlayer(PlayerEntity player) {
        EpicEmotesModVariables.PlayerVariables entityData = player.getCapability(EMOTE_CAPABILITY, null).orElse(null);

        return entityData;
    }

    private static void setupEmoteTable() {
        emoteTable = new HashMap<>();
        rootEmote = new EmoteCategory(PREFIX+"root", null, null);

        EmoteCategory FAVORITES = (EmoteCategory) rootEmote.addChild(new FixedEmoteCategory(PREFIX+"category.favorites", null, FAVORITE_ICON, favoriteEmotes));

        EmoteCategory SITTING = (EmoteCategory) rootEmote.addChild(new EmoteCategory(PREFIX+"category.sitting", null, DEFAULT_SIT_ICON));

        SITTING.addChild(registerEmote(new RepeatingEmote(PREFIX+"sit1", "sit1", rootEmote, EpicEmotesAnimations.SIT_1_IDLE, EpicEmotesAnimations.SIT_1_START, EpicEmotesAnimations.SIT_1_END, DEFAULT_SIT_ICON)));
        SITTING.addChild(registerEmote(new RepeatingEmote(PREFIX+"sit_fire", "sit_fire", rootEmote, EpicEmotesAnimations.SIT_FIRE_IDLE, EpicEmotesAnimations.SIT_FIRE_START, EpicEmotesAnimations.SIT_FIRE_END, DEFAULT_SIT_ICON)));

        EmoteCategory STANDING = (EmoteCategory) rootEmote.addChild(new EmoteCategory(PREFIX+"category.standing", null, DEFAULT_SIT_ICON));

        STANDING.addChild(registerEmote(new RepeatingEmote(PREFIX+"wall_lean_1", "wall_lean_1", rootEmote, EpicEmotesAnimations.WALL_LEAN_1_IDLE, EpicEmotesAnimations.WALL_LEAN_1_START, EpicEmotesAnimations.WALL_LEAN_1_END, DEFAULT_SIT_ICON)));
        STANDING.addChild(registerEmote(new RepeatingEmote(PREFIX+"wall_lean_2", "wall_lean_2", rootEmote, EpicEmotesAnimations.WALL_LEAN_2_IDLE, EpicEmotesAnimations.WALL_LEAN_2_START, EpicEmotesAnimations.WALL_LEAN_2_END, DEFAULT_SIT_ICON)));
        STANDING.addChild(registerEmote(new RepeatingEmote(PREFIX+"sorrow", "sorrow", rootEmote, EpicEmotesAnimations.SORROW, null, null, DEFAULT_SIT_ICON)));

        EmoteCategory GESTURES = (EmoteCategory) rootEmote.addChild(new EmoteCategory(PREFIX+"category.gestures", null, DEFAULT_SIT_ICON));
        GESTURES.addChild(registerEmote(new RepeatingEmote(PREFIX+"surrender", "surrender", rootEmote, EpicEmotesAnimations.SURRENDER, EpicEmotesAnimations.SURRENDER_START, EpicEmotesAnimations.SURRENDER_END, DEFAULT_SIT_ICON)));
        GESTURES.addChild(registerEmote(new ActionEmote(PREFIX+"yes", "yes", rootEmote, EpicEmotesAnimations.NOD_YES, DEFAULT_SIT_ICON)));
        GESTURES.addChild(registerEmote(new ActionEmote(PREFIX+"no", "no", rootEmote, EpicEmotesAnimations.SHAKE_HEAD_NO, DEFAULT_SIT_ICON)));
        GESTURES.addChild(registerEmote(new RepeatingEmote(PREFIX+"salute_1", "salute_1", rootEmote, EpicEmotesAnimations.SALUTE_1, EpicEmotesAnimations.SALUTE_1_START, EpicEmotesAnimations.SALUTE_1_END, DEFAULT_SIT_ICON)));

        EmoteCategory LAYING = (EmoteCategory) rootEmote.addChild(new EmoteCategory(PREFIX+"category.laying", null, DEFAULT_SIT_ICON));

        LAYING.addChild(registerEmote(new RepeatingEmote(PREFIX+"relaxed_lay_1", "relaxed_lay_1", rootEmote, EpicEmotesAnimations.RELAXED_LAY_1_IDLE, EpicEmotesAnimations.RELAXED_LAY_1_START, EpicEmotesAnimations.RELAXED_LAY_1_END, DEFAULT_SIT_ICON)));
        LAYING.addChild(registerEmote(new RepeatingEmote(PREFIX+"lay_1", "lay_1", rootEmote, EpicEmotesAnimations.LAY_1_IDLE, EpicEmotesAnimations.LAY_1_START, EpicEmotesAnimations.LAY_1_END, DEFAULT_SIT_ICON)));


    }
    public static EmoteCategory getRootCategory() {

        if (rootEmote == null)
            setupEmoteTable();

        return rootEmote;

    }

    public static HashMap<String, ActionEmote> getEmoteTable() {

        if (emoteTable == null)
            setupEmoteTable();

        return emoteTable;
    }


    public static void clientSideOverrideMotion(PlayerEntity player, RepeatingEmote emote) {

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
        EpicEmotesModVariables.PlayerVariables entityData = player.getCapability(EMOTE_CAPABILITY, null).orElse(null);

        if (entityData != null && emote.getEndAnimation() != null) {

            if (entityData.currEmote.equals(emote.getLookupIdentifier())) {
                entitypatch.playAnimationSynchronized(emote.getEndAnimation(), 0);

            }
            return;
        }


        entitypatch.getAnimator().addLivingAnimation(EELivingMotions.EMOTING, emote.getAnimation());


        if (emote.getStartAnimation() != null)
            entitypatch.playAnimationSynchronized(emote.getStartAnimation(), 0);

    }

    private static void cancelRepeatingEmote(PlayerEntity player, RepeatingEmote emote, LivingEntityPatch<?> entityPatch, EpicEmotesModVariables.PlayerVariables entData) {
        entData.currEmote = "";
        entData.emoteStart = player.tickCount;

        entData.syncEmote(player);


        if (emote.getEndAnimation() != null)
            entityPatch.playAnimationSynchronized(emote.getEndAnimation(), 0);
    }


    private static void executeRepeatingEmote(PlayerEntity player, RepeatingEmote emote, LivingEntityPatch<?> entityPatch, EpicEmotesModVariables.PlayerVariables entData) {

        if (entData.currEmote.equals(emote.getLookupIdentifier())) {
            cancelRepeatingEmote(player, emote, entityPatch, entData);
            return;
        }

        entData.currEmote = emote.getLookupIdentifier();
        entData.emoteStart = player.tickCount;

        entData.syncEmote(player);

        entityPatch.getAnimator().addLivingAnimation(EELivingMotions.EMOTING, emote.getAnimation());

        if (emote.getStartAnimation() != null)
            entityPatch.playAnimationSynchronized(emote.getStartAnimation(), 0);


    }
    public static void executeEmote(PlayerEntity player, ActionEmote emote, EpicEmotesModVariables.PlayerVariables entData) {

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch == null)
            return;

        if (emote instanceof RepeatingEmote) {
            executeRepeatingEmote(player, (RepeatingEmote) emote, entitypatch, entData);
            return;
        }

        entitypatch.playAnimationSynchronized(emote.getAnimation(), 0);
    }

}
