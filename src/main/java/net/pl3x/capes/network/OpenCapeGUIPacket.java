package net.pl3x.capes.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.capes.Capes;
import net.pl3x.capes.gui.ModGuiHandler;

public class OpenCapeGUIPacket implements IMessage {
    public OpenCapeGUIPacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<OpenCapeGUIPacket, IMessage> {
        @Override
        public IMessage onMessage(OpenCapeGUIPacket message, MessageContext ctx) {
            ctx.getServerHandler().player.openGui(Capes.instance, ModGuiHandler.CAPE_GUI, null, 0, 0, 0);
            return null;
        }
    }
}
