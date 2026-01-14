package net.m3tte.epic_emotes.mixin;

import net.m3tte.epic_emotes.EpicEmotesMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CompatMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }
    public static final Logger LOGGER = LogManager.getLogger(EpicEmotesMod.class);

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if (!mixinClassName.contains("compat"))
            return true;

        int startIndex = mixinClassName.indexOf("compat.") + "compat.".length();
        int endIndex = mixinClassName.indexOf(".", startIndex);
        String modid = mixinClassName.substring(startIndex, endIndex);

        boolean found = FMLLoader.getLoadingModList().getModFileById(modid) != null;

        if (found) {
            LOGGER.info("Applying patch mixin for mod: "+modid);
            if (modid.equals("replaymod")) {
                LOGGER.info("EpicEmotes : Hello Replaymod :). Let me fix your comms with EF.");
            }
        }


        return found;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
