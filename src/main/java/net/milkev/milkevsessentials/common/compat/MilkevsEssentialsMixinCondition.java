package net.milkev.milkevsessentials.common.compat;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MilkevsEssentialsMixinCondition implements IMixinConfigPlugin {

    ModConfig config;

    @Override
    public void onLoad(String mixinPackage) {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        //System.out.println(mixinClassName);
        final String id = "net.milkev.milkevsessentials.common.mixins.";
        return switch (mixinClassName) {
            case id + "FlightCharmMixin" -> config.enableFlightCharm;
            case id + "GluttonyCharmMixin" -> config.gluttonyCharm || config.opGluttonyCharm;
            case id + "OPGluttonyCharmMixin" -> config.opGluttonyCharm;
            case id + "ShieldMixin" -> config.enableInstantShieldBlocking || config.enableShieldBlocksFallDamage;
            case id + "ToolbeltPickupMixin" -> config.enableToolBeltPickup && config.enableToolBelt;
            case id + "AlchemicalStatisSootherCancelAddRemoveMixin",
                 id + "AlchemicalStatisSootherUnDecrementEffectMixin" -> config.alchemicalStasisSoother;
            default ->
                    true;
        };
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
