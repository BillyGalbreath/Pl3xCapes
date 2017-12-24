package net.pl3x.capes.tileentity;

import com.google.common.collect.Lists;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class TileEntityCape extends TileEntity {
    private EnumDyeColor baseColor = EnumDyeColor.BLACK;
    private NBTTagList patterns;
    private boolean patternDataSet;
    private List<BannerPattern> patternList;
    private List<EnumDyeColor> colorList;
    private String patternResourceLocation;

    public void setItemValues(ItemStack stack) {
        patterns = null;
        NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag");
        if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9)) {
            patterns = nbttagcompound.getTagList("Patterns", 10).copy();
        }
        baseColor = ItemBanner.getBaseColor(stack);
        patternList = null;
        colorList = null;
        patternResourceLocation = "";
        patternDataSet = true;
    }

    @SideOnly(Side.CLIENT)
    public List<BannerPattern> getPatternList() {
        initializeBannerData();
        return patternList;
    }

    @SideOnly(Side.CLIENT)
    public List<EnumDyeColor> getColorList() {
        initializeBannerData();
        return colorList;
    }

    @SideOnly(Side.CLIENT)
    public String getPatternResourceLocation() {
        initializeBannerData();
        return patternResourceLocation;
    }

    @SideOnly(Side.CLIENT)
    private void initializeBannerData() {
        if (patternList == null || colorList == null || patternResourceLocation == null) {
            if (!patternDataSet) {
                patternResourceLocation = "";
            } else {
                patternList = Lists.newArrayList();
                colorList = Lists.newArrayList();
                patternList.add(BannerPattern.BASE);
                colorList.add(this.baseColor);
                patternResourceLocation = "c" + baseColor.getDyeDamage();
                if (this.patterns != null) {
                    for (int i = 0; i < patterns.tagCount(); ++i) {
                        NBTTagCompound nbttagcompound = patterns.getCompoundTagAt(i);
                        BannerPattern bannerpattern = BannerPattern.byHash(nbttagcompound.getString("Pattern"));
                        if (bannerpattern != null) {
                            patternList.add(bannerpattern);
                            int color = nbttagcompound.getInteger("Color");
                            colorList.add(EnumDyeColor.byDyeDamage(color));
                            patternResourceLocation = patternResourceLocation + bannerpattern.getHashname() + color;
                        }
                    }
                }
            }
        }
    }
}
