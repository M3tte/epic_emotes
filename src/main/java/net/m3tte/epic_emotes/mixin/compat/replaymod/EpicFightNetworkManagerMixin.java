package net.m3tte.epic_emotes.mixin.compat.replaymod;

import com.replaymod.recording.ReplayModRecording;
import net.minecraft.network.IPacket;
import net.minecraftforge.fml.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.network.EpicFightNetworkManager;


@Mixin(value = EpicFightNetworkManager.class, remap = false)
public abstract class EpicFightNetworkManagerMixin {

    @Unique
    private static <MSG> IPacket<?> epicemotes$createVanillaPacket(MSG message, PacketDistributor.PacketTarget packetTarget) {

        IPacket<?> packet = EpicFightNetworkManager.INSTANCE.toVanillaPacket(message, packetTarget.getDirection());

        return packet;
    }



    @Inject(method = "sendToClient(Ljava/lang/Object;Lnet/minecraftforge/fml/network/PacketDistributor$PacketTarget;)V", at = @At("HEAD"))
    private static <MSG> void sendToClient(MSG message, PacketDistributor.PacketTarget packetTarget, CallbackInfo ci) {
        if (ReplayModRecording.instance.getConnectionEventHandler().getPacketListener() != null) {
            ReplayModRecording.instance.getConnectionEventHandler().getPacketListener().save(epicemotes$createVanillaPacket(message, packetTarget));

        }
    }

}
