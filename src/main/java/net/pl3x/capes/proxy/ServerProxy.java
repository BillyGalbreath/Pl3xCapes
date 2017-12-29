package net.pl3x.capes.proxy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.pl3x.capes.capability.CapabilityHandler;
import net.pl3x.capes.network.PacketHandler;
import net.pl3x.capes.network.client.CUpdateCapePacket;

import java.util.Timer;
import java.util.TimerTask;

public class ServerProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void on(PlayerEvent.PlayerLoggedInEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                EntityPlayerMP player = (EntityPlayerMP) event.player;
                PacketHandler.sendAllCapesToPlayer(player);
                ItemStack cape = CapabilityHandler.getCape(player);
                if (!cape.isEmpty()) {
                    PacketHandler.INSTANCE.sendToAll(new CUpdateCapePacket(player.getUniqueID(), cape));
                }
            }
        }, 1000L);
    }

    @SubscribeEvent
    public void on(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            PacketHandler.sendAllCapesToPlayer((EntityPlayerMP) event.getEntity());
        }
    }
}
