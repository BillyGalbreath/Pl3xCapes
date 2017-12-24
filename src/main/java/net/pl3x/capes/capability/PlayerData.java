package net.pl3x.capes.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import net.pl3x.capes.Capes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerData extends ItemStackHandler {
    public NBTTagCompound getDataAsNBT() {
        return serializeNBT();
    }

    public void setDataFromNBT(NBTTagCompound nbt) {
        deserializeNBT(nbt);
    }

    private static final ResourceLocation PLAYER_DATA = new ResourceLocation(Capes.modId, "capes");

    @SubscribeEvent
    public void on(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(PLAYER_DATA, new Provider());
        }
    }

    @SubscribeEvent
    public void on(PlayerEvent.Clone event) {
        event.getEntityPlayer().getCapability(Provider.CAPABILITY, null)
                .setDataFromNBT(event.getOriginal().getCapability(Provider.CAPABILITY, null)
                        .getDataAsNBT());
    }

    public static class Provider implements ICapabilitySerializable<NBTBase> {
        @CapabilityInject(PlayerData.class)
        public static final Capability<PlayerData> CAPABILITY = null;

        private final PlayerData instance = CAPABILITY.getDefaultInstance();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CAPABILITY ? CAPABILITY.cast(this.instance) : null;
        }

        @Override
        public NBTBase serializeNBT() {
            return CAPABILITY.getStorage().writeNBT(CAPABILITY, this.instance, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CAPABILITY.getStorage().readNBT(CAPABILITY, this.instance, null, nbt);
        }
    }

    public static class Storage implements Capability.IStorage<PlayerData> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side) {
            return instance.getDataAsNBT();
        }

        @Override
        public void readNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side, NBTBase nbt) {
            instance.setDataFromNBT((NBTTagCompound) nbt);
        }
    }
}
