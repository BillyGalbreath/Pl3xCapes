package net.pl3x.capes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.capes.CapeManager;

import java.util.UUID;

public class SendCapePacket implements IMessage {
    public UUID uuid;
    public ItemStack stack;

    public SendCapePacket() {
    }

    public SendCapePacket(UUID uuid, ItemStack stack) {
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

    public static class Handler implements IMessageHandler<SendCapePacket, IMessage> {
        @Override
        public IMessage onMessage(SendCapePacket message, MessageContext ctx) {
            CapeManager.addCape(message.uuid, message.stack);
            return null;
        }
    }
}
