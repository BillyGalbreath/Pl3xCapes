package net.pl3x.capes.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.pl3x.capes.listener.CapabilityHandler;
import net.pl3x.capes.network.PacketHandler;
import net.pl3x.capes.network.RequestCapePacket;

import javax.annotation.Nonnull;

public class CapeContainer extends Container {
    private ItemStackHandler capeInventory;

    public CapeContainer(EntityPlayer player) {
        System.out.println("SERVER");
        capeInventory = CapabilityHandler.getCapeInventory(player);

        addSlotToContainer(new SlotItemHandler(capeInventory, 0, 8, 35) {
            @Override
            public void onSlotChanged() {
                CapabilityHandler.setCapeInventory(player, capeInventory);
                PacketHandler.INSTANCE.sendToAll(new RequestCapePacket());
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
