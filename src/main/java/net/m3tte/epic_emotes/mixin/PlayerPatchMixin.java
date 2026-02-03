package net.m3tte.epic_emotes.mixin;

import net.m3tte.epic_emotes.gameasset.EELivingMotions;
import net.m3tte.epic_emotes.gameasset.EpicEmotesAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@OnlyIn(Dist.CLIENT)
@Mixin(value = PlayerPatch.class, remap = false)
public class PlayerPatchMixin {

    @Inject(at = @At(value = "HEAD"), method = "initAnimator(Lyesman/epicfight/api/client/animation/ClientAnimator;)V")
    private void injectInit(ClientAnimator clientAnimator, CallbackInfo cbk) {

        clientAnimator.addLivingAnimation(EELivingMotions.EMOTING, EpicEmotesAnimations.SIT_1_IDLE);


    }
}
