package net.milkev.milkevsessentials.common.mixins;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.tag.convention.v2.TagUtil;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public abstract class AlchemicalStasisSootherUnDecrementEffectMixin {
   
    @Shadow
    private int duration;

    @Shadow public abstract String getTranslationKey();

    @Unique
    private int timer = 0;
    //will track the time since the last duration refresh
    
    @Unique
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    
    @Inject(at = @At("HEAD"), method = "update")
    private void unDecrementDuration(LivingEntity livingEntity, Runnable runnable, CallbackInfoReturnable<Integer> cir) {
        StatusEffectInstance object = (StatusEffectInstance)(Object)this;
        TrinketComponent trinket = TrinketsApi.getTrinketComponent(livingEntity).get();
        timer++;
        if(trinket.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
            if (!TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, object.getEffectType().comp_349())) {
                //if the status effect is not blacklisted in the milkevsessentials:alchemical_stasis_soother_blacklist.json file
                if (duration <= config.alchemicalStasisSootherRefreshTimer && config.alchemicalStasisSootherRefreshesShortEffects) {
                    duration = config.alchemicalStasisSootherRefreshTimer * 3; //if an effect has a duration less than the refresh timer, set the duration to the refresh timer * 3
                    timer = 0;
                }
                if (timer >= config.alchemicalStasisSootherRefreshTimer) {
                    duration += config.alchemicalStasisSootherRefreshTimer; //restores configurable ticks every x ticks to the effect
                    timer = 0;
                }
            } else {
                //System.out.println("Effect is blacklisted!");
            }
        }
    }
}
