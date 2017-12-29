package net.pl3x.capes.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.capes.Capes;

@Config(modid = Capes.modId)
@Config.LangKey("config.title")
public class CapesConfig {
    @Config.LangKey("config.drop_on_death")
    public static boolean dropOnDeath = false;

    @Mod.EventBusSubscriber(modid = Capes.modId)
    private static class EventHandler {
        @SubscribeEvent
        public static void on(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Capes.modId)) {
                ConfigManager.sync(Capes.modId, Config.Type.INSTANCE);
            }
        }
    }
}
