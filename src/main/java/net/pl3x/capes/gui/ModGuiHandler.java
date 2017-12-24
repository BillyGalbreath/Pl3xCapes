package net.pl3x.capes.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.pl3x.capes.container.CapeContainer;

public class ModGuiHandler implements IGuiHandler {
    public static final int CAPE_GUI = 0;

    @Override
    public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case CAPE_GUI:
                return new CapeContainer(player);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case CAPE_GUI:
                return new CapeGui(getServerGuiElement(id, player, world, x, y, z));
            default:
                return null;
        }
    }
}
