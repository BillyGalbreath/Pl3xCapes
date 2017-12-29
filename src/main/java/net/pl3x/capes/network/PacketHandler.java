package net.pl3x.capes.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.capes.Capes;
import net.pl3x.capes.capability.CapabilityHandler;
import net.pl3x.capes.network.client.CPlayerDataPacket;
import net.pl3x.capes.network.client.CUpdateCapePacket;
import net.pl3x.capes.network.server.SOpenCapeGUIPacket;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Capes.modId);

    public static void init() {
        INSTANCE.registerMessage(CPlayerDataPacket.Handler.class, CPlayerDataPacket.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(SOpenCapeGUIPacket.Handler.class, SOpenCapeGUIPacket.class, 1, Side.SERVER);
        INSTANCE.registerMessage(CUpdateCapePacket.Handler.class, CUpdateCapePacket.class, 2, Side.CLIENT);
    }

    public static void sendAllCapesToPlayer(EntityPlayerMP player) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(online ->
                PacketHandler.INSTANCE.sendTo(new CUpdateCapePacket(online.getUniqueID(), CapabilityHandler.getCape(online)), player));
    }

    public void handleCPlayerData(CPlayerDataPacket packet, MessageContext ctx) {
    }

    public void handleCUpdateCape(CUpdateCapePacket packet, MessageContext ctx) {
    }
}
