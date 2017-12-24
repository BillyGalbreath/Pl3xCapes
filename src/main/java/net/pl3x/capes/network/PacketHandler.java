package net.pl3x.capes.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.capes.Capes;
import net.pl3x.capes.capability.PlayerData;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Capes.modId);

    public static void init() {
        INSTANCE.registerMessage(PlayerDataPacket.Handler.class, PlayerDataPacket.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(OpenCapeGUIPacket.Handler.class, OpenCapeGUIPacket.class, 1, Side.SERVER);
        INSTANCE.registerMessage(RequestCapePacket.Handler.class, RequestCapePacket.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(ReplyCapePacket.Handler.class, ReplyCapePacket.class, 3, Side.SERVER);
        INSTANCE.registerMessage(SendCapePacket.Handler.class, SendCapePacket.class, 4, Side.CLIENT);
    }

    public static void updatePlayerData(EntityPlayerMP player) {
        PlayerData data = player.getCapability(PlayerData.Provider.CAPABILITY, null);
        INSTANCE.sendTo(new PlayerDataPacket(data), player);
    }
}
