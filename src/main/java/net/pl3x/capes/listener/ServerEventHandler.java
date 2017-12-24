package net.pl3x.capes.listener;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.pl3x.capes.network.PacketHandler;
import net.pl3x.capes.network.RequestCapePacket;

import java.util.Timer;
import java.util.TimerTask;

public class ServerEventHandler {
    /*@SubscribeEvent
    public void on(BannerSlotChangedEvent event) {
        if (event.getPlayer() instanceof EntityPlayerMP) {
            PacketHandler.INSTANCE.sendToAll(new RequestCapePacket());
        }
    }*/

    @SubscribeEvent
    public void on(PlayerEvent.PlayerLoggedInEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                PacketHandler.INSTANCE.sendToAll(new RequestCapePacket());
            }
        }, 1000L);
    }
}
