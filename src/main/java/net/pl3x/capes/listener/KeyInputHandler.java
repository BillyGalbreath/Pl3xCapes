package net.pl3x.capes.listener;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.pl3x.capes.network.OpenCapeGUIPacket;
import net.pl3x.capes.network.PacketHandler;

public class KeyInputHandler {
    @SubscribeEvent
    public void on(InputEvent.KeyInputEvent event) {
        if (KeyBindings.capesGUI.isPressed()) {
            //Minecraft.getMinecraft().player.openGui(Capes.instance, ModGuiHandler.CAPE_GUI, null, 0, 0, 0);
            PacketHandler.INSTANCE.sendToServer(new OpenCapeGUIPacket());
        }
    }
}
