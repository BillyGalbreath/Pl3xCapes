package net.pl3x.capes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.pl3x.capes.cape.Textures;
import net.pl3x.capes.tileentity.TileEntityCape;

import java.util.HashMap;
import java.util.UUID;

public class CapeManager {
    private static final HashMap<UUID, CapeData> capeList = new HashMap<>();

    public static void clear() {
        capeList.clear();
    }

    public static void addCape(UUID uuid, ItemStack stack) {
        if (stack.getItem() == Items.BANNER) {
            capeList.put(uuid, new CapeData(stack));
        } else {
            capeList.remove(uuid);
        }
    }

    public static CapeData getCapeData(UUID uuid) {
        return capeList.get(uuid);
    }

    public static class CapeData {
        public final ItemStack stack;
        private ResourceLocation capeLocation;

        CapeData(ItemStack banner) {
            stack = banner;
            capeLocation = null;
        }

        public ResourceLocation getCapeLocation() {
            if (capeLocation == null) {
                TileEntityCape te = new TileEntityCape();
                te.setItemValues(stack);
                capeLocation = Textures.CAPE_DESIGNS.getResourceLocation(
                        te.getPatternResourceLocation(), te.getPatternList(), te.getColorList());
            }
            return capeLocation;
        }
    }
}
