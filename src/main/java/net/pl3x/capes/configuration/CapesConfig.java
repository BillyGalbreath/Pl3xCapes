package net.pl3x.capes.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.capes.Capes;

@Config(modid = Capes.modId)
@Config.LangKey("capes.config.title")
public class CapesConfig {
    @Config.Name("Drop Cape On Death")
    @Config.Comment("When killed, player cape will drop to the ground")
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
