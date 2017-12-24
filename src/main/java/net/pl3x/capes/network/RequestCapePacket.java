package net.pl3x.capes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.capes.listener.CapabilityHandler;

public class RequestCapePacket implements IMessage {
    public RequestCapePacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<RequestCapePacket, IMessage> {
        @Override
        public IMessage onMessage(RequestCapePacket message, MessageContext ctx) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            ItemStack cape = CapabilityHandler.getCapeInventory(player).getStackInSlot(0);
            PacketHandler.INSTANCE.sendToServer(new ReplyCapePacket(player.getUniqueID(), cape));
            return null;
        }
    }
}
