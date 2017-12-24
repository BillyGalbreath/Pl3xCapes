package net.pl3x.capes.tileentity.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.capes.cape.Textures;
import net.pl3x.capes.tileentity.TileEntityCape;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class TileEntityCapeRenderer extends TileEntitySpecialRenderer<TileEntityCape> {
    public void render(TileEntityCape te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ResourceLocation resourcelocation = getResourceLocation(te);
        if (resourcelocation != null) {
            bindTexture(resourcelocation);
        }
    }

    @Nullable
    private ResourceLocation getResourceLocation(TileEntityCape cape) {
        return Textures.CAPE_DESIGNS.getResourceLocation(cape.getPatternResourceLocation(), cape.getPatternList(), cape.getColorList());
    }
}
