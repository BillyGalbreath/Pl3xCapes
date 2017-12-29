package net.pl3x.capes.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.capes.Capes;
import net.pl3x.capes.network.PacketHandler;
import net.pl3x.capes.network.client.CPlayerDataPacket;

public class CapabilityHandler {
    private static final ResourceLocation PLAYER_DATA = new ResourceLocation(Capes.modId, "playerdata");

    @SubscribeEvent
    public void on(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(PLAYER_DATA, new PlayerData.Provider());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void on(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            PacketHandler.INSTANCE.sendTo(new CPlayerDataPacket(CapabilityHandler.getCapability(player)), player);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void on(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            PacketHandler.INSTANCE.sendTo(new CPlayerDataPacket(CapabilityHandler.getCapability(player)), player);
        }
    }

    @SubscribeEvent
    public void on(PlayerEvent.Clone event) {
        PlayerData newData = getCapability(event.getEntityPlayer());
        PlayerData oldData = getCapability(event.getOriginal());
        if (newData != null && oldData != null) {
            newData.setDataFromNBT(oldData.getDataAsNBT());
        }
    }

    public static PlayerData getCapability(EntityPlayer player) {
        return player.getCapability(PlayerData.Provider.CAPABILITY, null);
    }

    public static boolean hasCapability(EntityPlayer player) {
        return player.hasCapability(PlayerData.Provider.CAPABILITY, null);
    }

    public static ItemStack getCape(EntityPlayer player) {
        return hasCapability(player) ? getCapability(player).getStackInSlot(0) : ItemStack.EMPTY;
    }

    public static void setCape(EntityPlayer player, ItemStack stack) {
        if (hasCapability(player)) {
            getCapability(player).setStackInSlot(0, stack);
        }
    }
}
