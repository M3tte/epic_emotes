package net.m3tte.epic_emotes.network;

import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EpicEmotePackageRegistry {

    public EpicEmotePackageRegistry() {

        addNetworkMessage(EpicEmotesModVariables.PlayerVariablesSyncMessage.class, EpicEmotesModVariables.PlayerVariablesSyncMessage::buffer, EpicEmotesModVariables.PlayerVariablesSyncMessage::new,
                EpicEmotesModVariables.PlayerVariablesSyncMessage::handler);
        addNetworkMessage(EmotePackagePrefabs.GenericKeybindingPressedMessage.class, EmotePackagePrefabs.GenericKeybindingPressedMessage::buffer, EmotePackagePrefabs.GenericKeybindingPressedMessage::new,
                EmotePackagePrefabs.GenericKeybindingPressedMessage::handler);
        addNetworkMessage(EmotePackagePrefabs.ExecuteEmotePackage.class, EmotePackagePrefabs.ExecuteEmotePackage::buffer, EmotePackagePrefabs.ExecuteEmotePackage::new,
                EmotePackagePrefabs.ExecuteEmotePackage::handler);


    }




    private int messageID = 0;
    public <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder,
                                      BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        EpicEmotesMod.PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

}
