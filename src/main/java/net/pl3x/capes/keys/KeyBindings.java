package net.pl3x.capes.keys;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.pl3x.capes.network.PacketHandler;
import net.pl3x.capes.network.server.SOpenCapeGUIPacket;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
    private final static String CATEGORY = "key.categories.capes";

    private final KeyBinding OPEN_GUI;

    public KeyBindings() {
        ClientRegistry.registerKeyBinding(OPEN_GUI = new KeyBinding("key.capes.gui", Keyboard.KEY_B, CATEGORY));
    }

    @SubscribeEvent
    public void on(InputEvent.KeyInputEvent event) {
        if (OPEN_GUI.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new SOpenCapeGUIPacket());
        }
    }
}
