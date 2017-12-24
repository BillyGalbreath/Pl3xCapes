package net.pl3x.capes.listener;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
    private final static String CATEGORY = "key.categories.pl3x";

    public static KeyBinding capesGUI;

    public static void init() {
        ClientRegistry.registerKeyBinding(capesGUI = new KeyBinding("key.capesGUI", Keyboard.KEY_B, CATEGORY));
    }
}
