package net.pl3x.capes.network.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.capes.CapeManager;
import net.pl3x.capes.capability.CapabilityHandler;
import net.pl3x.capes.network.PacketHandler;

public class ClientHandler extends PacketHandler {
    @Override
    public void handleCPlayerData(CPlayerDataPacket packet, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() ->
                CapabilityHandler.getCapability(Minecraft.getMinecraft().player).setDataFromNBT(packet.nbt));
    }

    @Override
    public void handleCUpdateCape(CUpdateCapePacket packet, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() ->
                CapeManager.addCape(packet.uuid, packet.stack));
    }
}
