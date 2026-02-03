package net.m3tte.epic_emotes.network;

import io.netty.buffer.Unpooled;
import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.gameasset.EELivingMotions;
import net.m3tte.epic_emotes.gui.EmoteChooseGUI;
import net.m3tte.epic_emotes.systems.ActionEmote;
import net.m3tte.epic_emotes.systems.EmoteSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

import static net.m3tte.epic_emotes.EpicEmotesModVariables.EMOTE_CAPABILITY;
import static net.m3tte.epic_emotes.systems.EmoteSystem.executeEmote;


public class ClientEmotePackagePrefabs {


    public static class ClientCascadeLivingAnimation {
        int entityID;
        String emoteIdentifier;

        public ClientCascadeLivingAnimation(int targetID, String emoteIdentifier) {
            this.entityID = targetID;
            this.emoteIdentifier = emoteIdentifier;
        }

        public ClientCascadeLivingAnimation(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            int len = buffer.readInt();
            this.emoteIdentifier = buffer.readUtf(len);
        }

        public static void buffer(ClientCascadeLivingAnimation message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeInt(message.emoteIdentifier.length());
            buffer.writeUtf(message.emoteIdentifier);
        }

        public static void handler(ClientCascadeLivingAnimation message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                Entity target = Minecraft.getInstance().level.getEntity(message.entityID);

                System.out.println("EXECUTING EMOTE :: CLIENT");
                if (target instanceof PlayerEntity) {
                    EpicEmotesModVariables.PlayerVariables entityData = target.getCapability(EMOTE_CAPABILITY, null).orElse(null);

                    entityData.emoteStart = target.tickCount;
                    cascadeLivingMotion((PlayerEntity) target, message.emoteIdentifier);
                }


            });
            context.setPacketHandled(true);
        }
    }


    private static void cascadeLivingMotion(PlayerEntity target, String emoteIdent) {

        ActionEmote emote = EmoteSystem.getEmoteTable().getOrDefault(emoteIdent, null);
        EpicEmotesModVariables.PlayerVariables entityData = target.getCapability(EMOTE_CAPABILITY, null).orElse(null);
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entityData == null) {
            EpicEmotesMod.LOGGER.warn("Cannot get target players emote data.");
            return;
        }

        if (emote == null) {
            EpicEmotesMod.LOGGER.warn("Emote with identifier: "+emoteIdent+" not found.");
            return;
        }

        if (entitypatch == null) {
            EpicEmotesMod.LOGGER.warn("Error Executing Emote. No Patch Found.");
            return;
        }


        entitypatch.getAnimator().addLivingAnimation(EELivingMotions.EMOTING, emote.getAnimation());
    }

    public static class GenericKeybindingPressedMessage {
        int type, pressedms;

        public GenericKeybindingPressedMessage(int type, int pressedms) {
            this.type = type;
            this.pressedms = pressedms;
        }

        public GenericKeybindingPressedMessage(PacketBuffer buffer) {
            this.type = buffer.readInt();
            this.pressedms = buffer.readInt();
        }

        public static void buffer(GenericKeybindingPressedMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.type);
            buffer.writeInt(message.pressedms);
        }

        public static void handler(GenericKeybindingPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                pressAction(context.getSender(), message.type);
            });
            context.setPacketHandled(true);
        }
    }

    public static void pressAction(PlayerEntity entity, int type) {

        // security measure to prevent arbitrary chunk generation

        switch (type) {
            case 0:
                openEmotesUI(entity);
                break;

            default:
                break;
        }
    }

    public static void openEmotesUI(PlayerEntity entity) {
        System.out.println("PRESSED KEY. Opening GUI");
        if (entity instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) entity, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new StringTextComponent("Emotes");
                }

                @Override
                public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                    return new EmoteChooseGUI.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(entity.blockPosition()));
                }
            }, entity.blockPosition());
        }
    }


}
