package net.pl3x.capes.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.capes.cape.LayerPl3xCape;
import net.pl3x.capes.cape.LayerPl3xElytra;
import net.pl3x.capes.listener.KeyBindings;
import net.pl3x.capes.listener.KeyInputHandler;
import net.pl3x.capes.tileentity.TileEntityCape;
import net.pl3x.capes.tileentity.render.TileEntityCapeRenderer;

import java.lang.reflect.Field;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCape.class, new TileEntityCapeRenderer());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();

        for (RenderPlayer renderer : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            try {
                for (Field f : RenderLivingBase.class.getDeclaredFields()) {
                    boolean a = f.isAccessible();
                    f.setAccessible(true);
                    if (f.getType().equals(List.class)) {
                        List l = (List) f.get(renderer);
                        for (int i = 0; i < l.size(); ++i) {
                            Object c = l.get(i);
                            if (c.getClass().equals(LayerCape.class) || c.getClass().equals(LayerElytra.class)) {
                                l.remove(c);
                            }
                        }
                    }
                    f.setAccessible(a);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            renderer.addLayer(new LayerPl3xCape(renderer));
            renderer.addLayer(new LayerPl3xElytra(renderer));
        }
    }
}
