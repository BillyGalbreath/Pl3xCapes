package net.pl3x.capes.cape;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.capes.CapeManager;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class LayerPl3xElytra implements LayerRenderer<AbstractClientPlayer> {
    private final RenderPlayer renderPlayer;
    private final ModelElytra modelElytra = new ModelElytra();
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");

    public LayerPl3xElytra(RenderPlayer renderer) {
        renderPlayer = renderer;
    }

    public void doRenderLayer(@Nonnull AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack elytra = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (elytra.isEmpty() || elytra.getItem() != Items.ELYTRA) {
            return;
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();

        CapeManager.CapeData data = CapeManager.getCapeData(player.getUniqueID());
        if (data != null && data.getCapeLocation() != null && data.stack != null && !data.stack.isEmpty()) {
            return;
            //renderPlayer.bindTexture(data.getCapeLocation());
        } else {
            renderPlayer.bindTexture(TEXTURE_ELYTRA);
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.125F);
        modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, player);
        modelElytra.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (elytra.isItemEnchanted()) {
            LayerArmorBase.renderEnchantedGlint(renderPlayer, player, modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }
        GlStateManager.popMatrix();
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
