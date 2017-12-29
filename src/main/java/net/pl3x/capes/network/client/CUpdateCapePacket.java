package net.pl3x.capes.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.capes.Capes;

import java.util.UUID;

public class CUpdateCapePacket implements IMessage {
    UUID uuid;
    ItemStack stack;

    public CUpdateCapePacket() {
    }

    public CUpdateCapePacket(UUID uuid, ItemStack stack) {
        this.uuid = uuid;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, uuid.toString());
        ByteBufUtils.writeItemStack(buf, stack);
    }

    public static class Handler implements IMessageHandler<CUpdateCapePacket, IMessage> {
        @Override
        public IMessage onMessage(CUpdateCapePacket packet, MessageContext ctx) {
            Capes.proxy.packetHandler.handleCUpdateCape(packet, ctx);
            return null;
        }
    }
}
