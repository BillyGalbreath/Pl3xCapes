package net.pl3x.capes.listener;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import net.pl3x.capes.Capes;
import net.pl3x.capes.capability.PlayerData;
import net.pl3x.capes.network.PacketHandler;

public class CapabilityHandler {
    private static final ResourceLocation PLAYER_DATA = new ResourceLocation(Capes.modId, "playerdata");

    @SubscribeEvent
    public void on(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(PLAYER_DATA, new PlayerData.Provider());
        }
    }

    @SubscribeEvent
    public void on(PlayerEvent.Clone event) {
        event.getEntityPlayer().getCapability(PlayerData.Provider.CAPABILITY, null)
                .setDataFromNBT(event.getOriginal().getCapability(PlayerData.Provider.CAPABILITY, null)
                        .getDataAsNBT());
    }

    @SubscribeEvent
    public void on(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            PacketHandler.updatePlayerData((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void on(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            PacketHandler.updatePlayerData((EntityPlayerMP) event.getEntity());
        }
    }

    public static ItemStackHandler getCapeInventory(EntityPlayer player) {
        return player.getCapability(PlayerData.Provider.CAPABILITY, null);
    }

    public static void setCapeInventory(EntityPlayer player, ItemStackHandler capeInventory) {
        player.getCapability(PlayerData.Provider.CAPABILITY, null).setDataFromNBT(capeInventory.serializeNBT());
    }
}
