package net.milkev.milkevsessentials.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MilkevsEssentialsMixinCondition implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        //System.out.println(mixinClassName);

        if(mixinClassName.equalsIgnoreCase("net.milkev.milkevsessentials.common.mixins.OPGluttonyCharmMixinMealAPI")) {
            if (FabricLoader.getInstance().isModLoaded("mealapi")) {
                //System.out.println("Loading Meal API Mixin");
                return true;
            } else {
                //System.out.println("Not loading Meal API Mixin");
                return false;
            }
        }
        //System.out.println("Loaded Mixin: " + mixinClassName);
        return true;
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