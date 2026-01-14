package net.m3tte.epic_emotes.systems;

import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.gameasset.EpicEmotesAnimations;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashMap;

import static net.m3tte.epic_emotes.EpicEmotesModVariables.EMOTE_CAPABILITY;

public class EmoteSystem {
    private static final ResourceLocation DEFAULT_SIT_ICON = new ResourceLocation("epic_emotes:textures/gui/sit.png");

    private static String PREFIX = "gui.epic_emotes.";

    private static HashMap<String, ActionEmote> emoteTable = null;

    private static EmoteCategory rootEmote = null;

    private static ActionEmote registerEmote(ActionEmote emote) {

        emoteTable.put(emote.getLookupIdentifier(), emote);

        return emote;
    }

    private static void setupEmoteTable() {
        emoteTable = new HashMap<>();
        rootEmote = new EmoteCategory(PREFIX+"root", null, null);

        EmoteCategory SITTING = (EmoteCategory) rootEmote.addChild(new EmoteCategory(PREFIX+"category.sitting", null, DEFAULT_SIT_ICON));

        SITTING.addChild(registerEmote(new ActionEmote(PREFIX+"sit1", "sit1", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        SITTING.addChild(registerEmote(new ActionEmote(PREFIX+"sit2", "sit2", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        SITTING.addChild(registerEmote(new ActionEmote(PREFIX+"sit3", "sit3", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));

        EmoteCategory LAYING = (EmoteCategory) rootEmote.addChild(new EmoteCategory(PREFIX+"category.laying", null, DEFAULT_SIT_ICON));

        LAYING.addChild(registerEmote(new ActionEmote(PREFIX+"lay1", "lay1", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        LAYING.addChild(registerEmote(new ActionEmote(PREFIX+"lay2", "lay2", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        LAYING.addChild(registerEmote(new ActionEmote(PREFIX+"lay3", "lay3", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        LAYING.addChild(registerEmote(new ActionEmote(PREFIX+"lay4", "lay4", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        LAYING.addChild(registerEmote(new ActionEmote(PREFIX+"lay5", "lay5", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        LAYING.addChild(registerEmote(new ActionEmote(PREFIX+"lay6", "lay6", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));
        LAYING.addChild(registerEmote(new ActionEmote(PREFIX+"lay7", "lay7", rootEmote, EpicEmotesAnimations.SIT_1_START, DEFAULT_SIT_ICON)));

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

    private static void executeRepeatingEmote(PlayerEntity player, RepeatingEmote emote, LivingEntityPatch<?> entityPatch) {
        EpicEmotesModVariables.PlayerVariables entityData = player.getCapability(EMOTE_CAPABILITY, null).orElse(null);

        if (entityData != null) {


            entityData.currEmote = emote.getLookupIdentifier();

            entityData.syncEmote(player);

            entityPatch.playAnimationSynchronized(emote.getStartAnimation(), 0);
        }


    }
    public static void executeEmote(PlayerEntity player, ActionEmote emote) {

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch == null)
            return;

        if (emote instanceof RepeatingEmote) {
            executeRepeatingEmote(player, (RepeatingEmote) emote, entitypatch);
            return;
        }

        entitypatch.playAnimationSynchronized(emote.getAnimation(), 0);
    }

}
