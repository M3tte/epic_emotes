package net.m3tte.epic_emotes.mixin.compat.replaymod;

import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.network.EpicFightNetworkManager;


@Mixin(value = EpicFightNetworkManager.class, remap = false)
public abstract class EpicFightNetworkManagerMixin {
/*
    @Unique
    private static <MSG> IPacket<?> epicemotes$createVanillaPacket(MSG message, PacketTarget packetTarget) {

        IPacket<?> packet = EpicFightNetworkManager.INSTANCE.toVanillaPacket(message, packetTarget.getDirection());

        return packet;
    }



    @Inject(method = "sendToClient(Ljava/lang/Object;Lnet/minecraftforge/fml/network/PacketDistributor$PacketTarget;)V", at = @At("HEAD"))
    private static <MSG> void sendToClient(MSG message, PacketTarget packetTarget, CallbackInfo ci) {
        if (ReplayModRecording.instance.getConnectionEventHandler().getPacketListener() != null) {
            ReplayModRecording.instance.getConnectionEventHandler().getPacketListener().save(epicemotes$createVanillaPacket(message, packetTarget));

        }
    }
*/
}
