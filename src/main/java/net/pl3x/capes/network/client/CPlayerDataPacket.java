package net.pl3x.capes.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.capes.Capes;
import net.pl3x.capes.capability.PlayerData;

public class CPlayerDataPacket implements IMessage {
    NBTTagCompound nbt;

    public CPlayerDataPacket() {
    }

    public CPlayerDataPacket(PlayerData playerData) {
        this.nbt = playerData.getDataAsNBT();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<CPlayerDataPacket, IMessage> {
        @Override
        public IMessage onMessage(CPlayerDataPacket packet, MessageContext ctx) {
            Capes.proxy.packetHandler.handleCPlayerData(packet, ctx);
            return null;
        }
    }
}
