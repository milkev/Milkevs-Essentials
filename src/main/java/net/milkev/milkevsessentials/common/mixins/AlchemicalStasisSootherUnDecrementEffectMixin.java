package net.milkev.milkevsessentials.common.mixins;

import io.wispforest.accessories.api.AccessoriesCapability;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.tag.convention.v2.TagUtil;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
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
        try {
            StatusEffectInstance object = (StatusEffectInstance) (Object) this;
            AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(livingEntity);
            timer++;
            if (accessoriesCapability.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
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
        }  catch(Exception e) {
            //this is here incase accessoriesCapability produces null, which is intended by accessories, and just means that the entity we are working on cant have accessories equipped
        }
    }
}
