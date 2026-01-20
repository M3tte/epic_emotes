package net.m3tte.epic_emotes.network;

import io.netty.buffer.Unpooled;
import net.m3tte.epic_emotes.EpicEmotesMod;
import net.m3tte.epic_emotes.EpicEmotesModVariables;
import net.m3tte.epic_emotes.gui.EmoteChooseGUI;
import net.m3tte.epic_emotes.systems.ActionEmote;
import net.m3tte.epic_emotes.systems.EmoteSystem;
import net.m3tte.epic_emotes.systems.RepeatingEmote;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

import static net.m3tte.epic_emotes.EpicEmotesModVariables.EMOTE_CAPABILITY;
import static net.m3tte.epic_emotes.systems.EmoteSystem.executeEmote;


public class EmotePackagePrefabs {
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
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);

                    if (target instanceof PlayerEntity) {
                        // runEmote((PlayerEntity) target, message.emoteIdentifier);
                        EpicEmotesMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),new EmotePackagePrefabs.ExecuteEmotePackage((PlayerEntity) target, message.emoteIdentifier));
                    }
                } else {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);

                    if (target instanceof PlayerEntity) {
                        runEmote((PlayerEntity) target, message.emoteIdentifier);
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
