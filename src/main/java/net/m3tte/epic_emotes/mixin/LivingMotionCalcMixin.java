package net.m3tte.epic_emotes.mixin;

import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.gameasset.EELivingMotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import static net.m3tte.epic_emotes.EpicEmotesModVariables.EMOTE_CAPABILITY;

@Mixin(value = AbstractClientPlayerPatch.class, remap = false)
public class LivingMotionCalcMixin {

    // Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z", shift = At.Shift.BEFORE, ordinal = 0), method = "updateMotion(Z)V")
    public void updateMotionPostInject(boolean considerInaction, CallbackInfo ci) {
        PlayerPatch<?> patch = ((PlayerPatch<?>) (Object) this);

        if (patch == null)
            return;

        EpicEmotesModVariables.PlayerVariables entityData = patch.getOriginal().getCapability(EMOTE_CAPABILITY, null).orElse(null);

        if (entityData != null) {
            if ((!entityData.currEmote.isEmpty()) || true) {
                patch.currentLivingMotion = EELivingMotions.EMOTING;
            }
        }


    }
}
