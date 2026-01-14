package net.m3tte.epic_emotes.network;

import io.netty.buffer.Unpooled;
import net.m3tte.epic_emotes.gui.EmoteChooseGUI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;



public class KeybindPackages {
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
        World world = entity.level;
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        // security measure to prevent arbitrary chunk generation
        if (!world.isLoaded(new BlockPos(x, y, z)))
            return;

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
