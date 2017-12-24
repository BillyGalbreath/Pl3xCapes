package net.pl3x.capes.cape;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.capes.CapeManager;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class LayerPl3xCape implements LayerRenderer<AbstractClientPlayer> {
    private final RenderPlayer playerRenderer;

    public LayerPl3xCape(RenderPlayer renderer) {
        this.playerRenderer = renderer;
    }

    public void doRenderLayer(@Nonnull AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (player.isInvisible()) {
            return;
        }
        ItemStack elytra = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        //if (!elytra.isEmpty() && elytra.getItem() == Items.ELYTRA) {
        //    return;
        //}
        CapeManager.CapeData data = CapeManager.getCapeData(player.getUniqueID());
        if (data == null || data.stack == null || data.stack.isEmpty() || data.getCapeLocation() == null) {
            return;
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        playerRenderer.bindTexture(data.getCapeLocation());
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.125F);
        double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * partialTicks);
        double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * partialTicks);
        double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks);
        float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;

        double d3 = MathHelper.sin(f * 0.017453292F);
        double d4 = -MathHelper.cos(f * 0.017453292F);
        float f1 = (float) d1 * 10.0F;
        f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
        float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
        float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
        if (player.isElytraFlying()) {
            f2 /= 20;
            if (f2 > 15.0F) {
                f2 = 15.0F;
            }
        }
        if (f2 < 0.0F) {
            f2 = 0.0F;
        } else if (f2 > 160.0F) {
            f2 = 160.0F;
        }
        float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
        f1 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
        if (player.isSneaking()) {
            f1 += 25.0F;
            GlStateManager.translate(0.0F, 0.05F, -0.1F);
        }
        GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        playerRenderer.getMainModel().renderCape(0.0625F);
        GlStateManager.popMatrix();
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
