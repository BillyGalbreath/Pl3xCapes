package net.pl3x.capes.cape;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.capes.Capes;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Textures {
    public static final Cache CAPE_DESIGNS = new Cache(new ResourceLocation(Capes.modId, "textures/entity/cape_base.png"), Capes.modId + ":textures/entity/cape/");
    public static final ResourceLocation CAPE_BASE_TEXTURE = new ResourceLocation(Capes.modId, "textures/entity/cape/base.png");

    @SideOnly(Side.CLIENT)
    public static class Cache {
        private final Map<String, CacheEntry> cacheMap = Maps.newLinkedHashMap();
        private final ResourceLocation cacheResourceLocation;
        private final String cacheResourceBase;

        public Cache(ResourceLocation baseResource, String resourcePath) {
            cacheResourceLocation = baseResource;
            cacheResourceBase = resourcePath;
        }

        @Nullable
        public ResourceLocation getResourceLocation(String id, List<BannerPattern> patternList, List<EnumDyeColor> colorList) {
            if (id.isEmpty()) {
                return null;
            }
            CacheEntry entry = cacheMap.get(id);
            if (entry == null) {
                if (cacheMap.size() >= 256 && !freeCacheSlot()) {
                    return CAPE_BASE_TEXTURE;
                }
                List<String> list = Lists.newArrayList();
                for (BannerPattern bannerpattern : patternList) {
                    list.add(cacheResourceBase + bannerpattern.getFileName() + ".png");
                }
                entry = new CacheEntry();
                entry.textureLocation = new ResourceLocation(id);
                Minecraft.getMinecraft().getTextureManager().loadTexture(entry.textureLocation,
                        new LayeredColorMaskTexture(cacheResourceLocation, list, colorList));
                cacheMap.put(id, entry);
            }

            entry.lastUseMillis = System.currentTimeMillis();
            return entry.textureLocation;
        }

        private boolean freeCacheSlot() {
            long i = System.currentTimeMillis();
            Iterator<String> iter = cacheMap.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                CacheEntry entry = cacheMap.get(s);
                if (i - entry.lastUseMillis > 5000L) {
                    Minecraft.getMinecraft().getTextureManager().deleteTexture(entry.textureLocation);
                    iter.remove();
                    return true;
                }
            }
            return cacheMap.size() < 256;
        }
    }

    @SideOnly(Side.CLIENT)
    static class CacheEntry {
        public long lastUseMillis;
        public ResourceLocation textureLocation;

        private CacheEntry() {
        }
    }
}
