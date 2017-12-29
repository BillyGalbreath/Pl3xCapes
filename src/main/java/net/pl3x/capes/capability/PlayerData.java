package net.pl3x.capes.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerData extends ItemStackHandler {
    public NBTTagCompound getDataAsNBT() {
        return serializeNBT();
    }

    public void setDataFromNBT(NBTTagCompound nbt) {
        deserializeNBT(nbt);
    }

    public static class Provider implements ICapabilitySerializable<NBTBase> {
        @CapabilityInject(PlayerData.class)
        static Capability<PlayerData> CAPABILITY = null;
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
