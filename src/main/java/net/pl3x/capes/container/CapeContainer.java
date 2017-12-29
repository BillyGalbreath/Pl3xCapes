package net.pl3x.capes.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.pl3x.capes.capability.CapabilityHandler;
import net.pl3x.capes.network.PacketHandler;
import net.pl3x.capes.network.client.CUpdateCapePacket;

import javax.annotation.Nonnull;

public class CapeContainer extends Container {
    public CapeContainer(EntityPlayer player) {
        addSlotToContainer(new SlotItemHandler(CapabilityHandler.getCapability(player), 0, 8, 35) {
            @Override
            public void onSlotChanged() {
                //PacketHandler.INSTANCE.sendToServer(new SUpdateCapePacket(getStack()));
                if (player instanceof EntityPlayerMP) {
                    PacketHandler.INSTANCE.sendToAll(new CUpdateCapePacket(player.getUniqueID(), getStack()));
                }
            }

            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return stack.getItem() == Items.BANNER;
            }
        });

        // player inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // player hotbar
        for (int k = 0; k < 9; ++k) {
            addSlotToContainer(new Slot(player.inventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            copy = stack.copy();
            int slots = inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < slots) {
                if (!mergeItemStack(stack, slots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(stack, 0, slots, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (stack.getCount() == copy.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stack);
        }
        return copy;
    }
}
