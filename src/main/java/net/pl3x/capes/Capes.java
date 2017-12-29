package net.pl3x.capes;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.pl3x.capes.proxy.CommonProxy;

@Mod(modid = Capes.modId, name = Capes.name, version = Capes.version)
public class Capes {
    public static final String modId = "capes";
    public static final String name = "Capes";
    public static final String version = "@DEV_BUILD@";

    @Mod.Instance(modId)
    public static Capes instance;

    @SidedProxy(serverSide = "net.pl3x.capes.proxy.ServerProxy", clientSide = "net.pl3x.capes.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
