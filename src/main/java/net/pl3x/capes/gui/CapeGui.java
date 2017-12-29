package net.pl3x.capes.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.pl3x.capes.Capes;
import net.pl3x.capes.cape.Textures;
import net.pl3x.capes.container.CapeContainer;
import net.pl3x.capes.tileentity.TileEntityCape;

public class CapeGui extends GuiContainer {
    private static final ResourceLocation BG = new ResourceLocation(Capes.modId, "textures/gui/cape.png");
    private final CapeContainer container;
    private ItemStack cape;
    private ResourceLocation capeLocation;

    CapeGui(Container container) {
        super(container);
        this.container = (CapeContainer) inventorySlots;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        ItemStack stack = container.inventorySlots.get(0).getStack();
        if (stack != cape) {
            cape = stack;
            if (!cape.isEmpty() && cape.getItem() == Items.BANNER) {
                TileEntityCape te = new TileEntityCape();
                te.setItemValues(cape);
                capeLocation = Textures.CAPE_DESIGNS.getResourceLocation(
                        te.getPatternResourceLocation(), te.getPatternList(), te.getColorList());
            } else {
                capeLocation = null;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = I18n.format("gui.cape");
        fontRenderer.drawString(title, xSize - fontRenderer.getStringWidth(title) - 8, 6, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BG);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (capeLocation != null) {
            mc.getTextureManager().bindTexture(capeLocation);
            drawScaledCustomSizeModalRect(guiLeft + 60, guiTop + 20, 0, 0, 44, 34, 66, 50, 128, 64);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }
}
