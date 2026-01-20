package net.m3tte.epic_emotes;

import com.replaymod.recording.ReplayModRecording;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.spongepowered.asm.mixin.Unique;
import yesman.epicfight.network.EpicFightNetworkManager;

import java.util.function.Supplier;


public class EpicEmotesModVariables {
	public EpicEmotesModVariables() {

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
	}

	private void init(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(PlayerVariables.class, new PlayerVariablesStorage(), PlayerVariables::new);
	}


	@CapabilityInject(PlayerVariables.class)
	public static Capability<PlayerVariables> EMOTE_CAPABILITY = null;

	@SubscribeEvent
	public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer))
			event.addCapability(new ResourceLocation("epic_emotes", "player_variables"), new PlayerVariablesProvider());
	}

	private static class PlayerVariablesProvider implements ICapabilitySerializable<INBT> {
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(EMOTE_CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == EMOTE_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public INBT serializeNBT() {
			return EMOTE_CAPABILITY.getStorage().writeNBT(EMOTE_CAPABILITY, this.instance.orElseThrow(RuntimeException::new),
					null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			EMOTE_CAPABILITY.getStorage().readNBT(EMOTE_CAPABILITY, this.instance.orElseThrow(RuntimeException::new), null,
					nbt);
		}
	}

	private static class PlayerVariablesStorage implements Capability.IStorage<PlayerVariables> {
		@Override
		public INBT writeNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("currentEmote", instance.currEmote);
			nbt.putInt("emoteStart", instance.emoteStart);

			return nbt;
		}

		@Override
		public void readNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side, INBT inbt) {
			CompoundNBT nbt = (CompoundNBT) inbt;
			instance.currEmote = nbt.getString("currentEmote");
			instance.emoteStart = nbt.getInt("emoteStart");
		}
	}

	public static class PlayerVariables {
		public String currEmote = "";
		public int emoteStart = 0;

		public void syncEmote(Entity entity) {
			if (entity instanceof ServerPlayerEntity) {

				PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(this);

				EpicEmotesMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity), message);
			}

		}

	}


	@SubscribeEvent
	public void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getPlayer().level.isClientSide)
			((PlayerVariables) event.getPlayer().getCapability(EMOTE_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncEmote(event.getPlayer());
	}

	@SubscribeEvent
	public void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (!event.getPlayer().level.isClientSide())
			((PlayerVariables) event.getPlayer().getCapability(EMOTE_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncEmote(event.getPlayer());
	}

	@SubscribeEvent
	public void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!event.getPlayer().level.isClientSide())
			((PlayerVariables) event.getPlayer().getCapability(EMOTE_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncEmote(event.getPlayer());
	}

	@SubscribeEvent
	public void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = event.getOriginal().getCapability(EMOTE_CAPABILITY, null)
				.orElse(new PlayerVariables());
		PlayerVariables clone = event.getEntity().getCapability(EMOTE_CAPABILITY, null).orElse(new PlayerVariables());


		if (!event.isWasDeath()) {
			clone.currEmote = original.currEmote;
			clone.emoteStart = original.emoteStart;
		}
	}

	public static class PlayerVariablesSyncMessage {
		public PlayerVariables data;

		public PlayerVariablesSyncMessage(PacketBuffer buffer) {
			this.data = new PlayerVariables();
			new PlayerVariablesStorage().readNBT(null, this.data, null, buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, PacketBuffer buffer) {
			buffer.writeNbt((CompoundNBT) new PlayerVariablesStorage().writeNBT(null, message.data, null));
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(EMOTE_CAPABILITY, null)
							.orElse(new PlayerVariables()));
					variables.currEmote = message.data.currEmote;
					variables.emoteStart = message.data.emoteStart;
				}
			});
			context.setPacketHandled(true);
		}
	}

}
