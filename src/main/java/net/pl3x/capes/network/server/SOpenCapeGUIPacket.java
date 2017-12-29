package net.pl3x.capes.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.capes.Capes;
import net.pl3x.capes.gui.ModGuiHandler;

public class SOpenCapeGUIPacket implements IMessage {
    public SOpenCapeGUIPacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<SOpenCapeGUIPacket, IMessage> {
        @Override
        public IMessage onMessage(SOpenCapeGUIPacket packet, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            ((WorldServer) player.world).addScheduledTask(() ->
                    player.openGui(Capes.instance, ModGuiHandler.CAPE_GUI, player.world, 0, 0, 0));
            return null;
        }
    }
}
