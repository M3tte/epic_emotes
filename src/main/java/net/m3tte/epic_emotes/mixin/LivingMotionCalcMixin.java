package net.m3tte.epic_emotes.mixin;

import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.gameasset.EELivingMotions;
import net.m3tte.epic_emotes.network.EmotePackagePrefabs;
import net.m3tte.epic_emotes.systems.ActionEmote;
import net.m3tte.epic_emotes.systems.EmoteSystem;
import net.m3tte.epic_emotes.systems.RepeatingEmote;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import static net.m3tte.epic_emotes.EpicEmotesModVariables.EMOTE_CAPABILITY;
@OnlyIn(Dist.CLIENT)
@Mixin(value = AbstractClientPlayerPatch.class, remap = false)
public class LivingMotionCalcMixin {

    // Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z

    @Unique
    private boolean epicemotes$checkShouldCancelConditions(LivingEntityPatch<?> patch, RepeatingEmote emote) {

        if (emote.isCancelOnMove()) {
            return !patch.currentLivingMotion.equals(LivingMotions.IDLE) || patch.getEntityState().attacking();
        }



        return patch.getEntityState().attacking() || patch.getEntityState().hurt();
    }


    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z", shift = At.Shift.BEFORE, ordinal = 0), method = "updateMotion(Z)V")
    public void updateMotionPostInject(boolean considerInaction, CallbackInfo ci) {
        PlayerPatch<?> patch = ((PlayerPatch<?>) (Object) this);

        if (patch == null)
            return;



        EpicEmotesModVariables.PlayerVariables entityData = patch.getOriginal().getCapability(EMOTE_CAPABILITY, null).orElse(null);

        if (entityData != null) {
            if ((!entityData.currEmote.isEmpty())) {
                boolean moveLock = false;

                RepeatingEmote targetEmote = (RepeatingEmote) EmoteSystem.getEmoteTable().getOrDefault(entityData.currEmote, null);

                float animTicks = targetEmote.getStartAnimation() != null ? (targetEmote.getStartAnimation().getTotalTime() + 0.5f) * 20 : 0;




                if (epicemotes$checkShouldCancelConditions(patch, targetEmote) && (patch.getOriginal().tickCount - entityData.emoteStart) >= animTicks) {
                    moveLock = true;
                }


                if (patch.getOriginal().tickCount - entityData.emoteStart >= animTicks - 12f)
                    patch.currentLivingMotion = EELivingMotions.EMOTING;


                if (targetEmote instanceof RepeatingEmote) {

                    if (moveLock) {
                        /*if (targetEmote.getEndAnimation() != null)
                            patch.playAnimationSynchronized(((RepeatingEmote) targetEmote).getEndAnimation(), 0);
                        entityData.currEmote = "";
                        entityData.syncEmote(patch.getOriginal());*/

                        EpicEmotesMod.PACKET_HANDLER.sendToServer(new EmotePackagePrefabs.CancelEmotePackage(patch.getOriginal(), ((ActionEmote) targetEmote).getLookupIdentifier()));
                    }
                }


            }
        }


    }
}
