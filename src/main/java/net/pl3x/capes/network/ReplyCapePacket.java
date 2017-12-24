package net.pl3x.capes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class ReplyCapePacket implements IMessage {
    public UUID uuid;
    public ItemStack stack;

    public ReplyCapePacket() {
    }

    public ReplyCapePacket(UUID uuid, ItemStack stack) {
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

    public static class Handler implements IMessageHandler<ReplyCapePacket, IMessage> {
        @Override
        public IMessage onMessage(ReplyCapePacket message, MessageContext ctx) {
            PacketHandler.INSTANCE.sendToAll(new SendCapePacket(message.uuid, message.stack));
            return null;
        }
    }
}
