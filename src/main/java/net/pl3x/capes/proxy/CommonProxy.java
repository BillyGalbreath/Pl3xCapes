package net.pl3x.capes.proxy;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.pl3x.capes.Capes;
import net.pl3x.capes.capability.CapabilityHandler;
import net.pl3x.capes.capability.PlayerData;
import net.pl3x.capes.configuration.CapesConfig;
import net.pl3x.capes.gui.ModGuiHandler;
import net.pl3x.capes.network.PacketHandler;

public class CommonProxy {
    public PacketHandler packetHandler;

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
        CapabilityManager.INSTANCE.register(PlayerData.class, new PlayerData.Storage(), PlayerData.class);

        NetworkRegistry.INSTANCE.registerGuiHandler(Capes.instance, new ModGuiHandler());

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());

        PacketHandler.init();
    }

    @SubscribeEvent
    public void on(LivingDropsEvent event) {
        if (!CapesConfig.dropOnDeath) {
            return; // drop on death disabled
        }
        if (!(event.getEntity() instanceof EntityPlayerMP)) {
            return; // only drop from server instance
        }
        EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
        ItemStack cape = CapabilityHandler.getCape(player);
        if (cape.isEmpty()) {
            return;
        }
        event.getDrops().add(new EntityItem(player.world, player.posX, player.posY, player.posZ, cape));
        CapabilityHandler.setCape(player, ItemStack.EMPTY);
    }
}
