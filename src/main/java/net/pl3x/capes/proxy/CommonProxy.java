package net.pl3x.capes.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.pl3x.capes.Capes;
import net.pl3x.capes.capability.PlayerData;
import net.pl3x.capes.gui.ModGuiHandler;
import net.pl3x.capes.capability.CapabilityHandler;
import net.pl3x.capes.network.PacketHandler;

public class CommonProxy {
    public PacketHandler packetHandler;

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
        CapabilityManager.INSTANCE.register(PlayerData.class, new PlayerData.Storage(), PlayerData.class);

        NetworkRegistry.INSTANCE.registerGuiHandler(Capes.instance, new ModGuiHandler());

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());

        PacketHandler.init();
    }
}
