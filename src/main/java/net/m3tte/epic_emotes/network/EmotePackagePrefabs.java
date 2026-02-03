package net.m3tte.epic_emotes.network;

import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.systems.ActionEmote;
import net.m3tte.epic_emotes.systems.EmoteSystem;
import net.m3tte.epic_emotes.systems.RepeatingEmote;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

import static net.m3tte.epic_emotes.EpicEmotesModVariables.EMOTE_CAPABILITY;
import static net.m3tte.epic_emotes.systems.EmoteSystem.executeEmote;


public class EmotePackagePrefabs {


    public static class ExecuteEmotePackage {
        int entityID;
        String emoteIdentifier;

        public ExecuteEmotePackage(PlayerEntity target, String emoteIdentifier) {
            this.entityID = target.getId();
            this.emoteIdentifier = emoteIdentifier;
        }

        public ExecuteEmotePackage(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            int len = buffer.readInt();
            this.emoteIdentifier = buffer.readUtf(len);
        }

        public static void buffer(ExecuteEmotePackage message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeInt(message.emoteIdentifier.length());
            buffer.writeUtf(message.emoteIdentifier);
        }

        public static void handler(ExecuteEmotePackage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getDirection().getReceptionSide().isServer()) {

                    World level = contextSupplier.get().getSender().level;

                    Entity target = level.getEntity(message.entityID);

                    System.out.println("GETTING PLAYER ON SERVER SIDE");

                    if (target instanceof PlayerEntity) {
                        runEmote((PlayerEntity) target, message.emoteIdentifier);
                        EpicEmotesMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),new ClientEmotePackagePrefabs.ClientCascadeLivingAnimation(message.entityID, message.emoteIdentifier));
                    }


                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class CancelEmotePackage {
        int entityID;
        String emoteIdentifier;

        public CancelEmotePackage(PlayerEntity target, String emoteIdentifier) {
            this.entityID = target.getId();
            this.emoteIdentifier = emoteIdentifier;
        }

        public CancelEmotePackage(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            int len = buffer.readInt();
            this.emoteIdentifier = buffer.readUtf(len);
        }

        public static void buffer(CancelEmotePackage message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeInt(message.emoteIdentifier.length());
            buffer.writeUtf(message.emoteIdentifier);
        }

        public static void handler(CancelEmotePackage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getDirection().getReceptionSide().isServer()) {

                    World level = contextSupplier.get().getSender().level;

                    Entity target = level.getEntity(message.entityID);

                    if (target instanceof PlayerEntity) {
                        EpicEmotesModVariables.PlayerVariables entityData = target.getCapability(EMOTE_CAPABILITY, null).orElse(null);
                        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                        ActionEmote targetEmote = EmoteSystem.getEmoteTable().getOrDefault(message.emoteIdentifier, null);
                        if (targetEmote instanceof RepeatingEmote) {
                            if (((RepeatingEmote) targetEmote).getEndAnimation() != null) {
                                entitypatch.playAnimationSynchronized(((RepeatingEmote) targetEmote).getEndAnimation(), 0);
                            }
                        }


                        entityData.currEmote = "";
                        entityData.syncEmote(target);
                    }


                }
            });
            context.setPacketHandled(true);
        }
    }


    private static void runEmote(PlayerEntity target, String emoteIdent) {

        ActionEmote emote = EmoteSystem.getEmoteTable().getOrDefault(emoteIdent, null);
        EpicEmotesModVariables.PlayerVariables entityData = target.getCapability(EMOTE_CAPABILITY, null).orElse(null);

        if (entityData == null) {
            EpicEmotesMod.LOGGER.warn("Cannot get target players emote data.");
            return;
        }

        if (emote == null) {
            EpicEmotesMod.LOGGER.warn("Emote with identifier: "+emoteIdent+" not found.");
            return;
        }

        executeEmote(target, emote, entityData);


    }





}
