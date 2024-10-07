package net.milkev.milkevsessentials.common.mixins;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Iterator;

@Mixin(LivingEntity.class)
public class AlchemicalStasisSootherCancelAddRemoveMixin {

    @Unique
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    @Inject(at = @At("HEAD"), method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", cancellable = true)
    private void cancelAdd(StatusEffectInstance statusEffectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(config.alchemicalStasisSootherPreventEffectAdd) {
            if (!TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, statusEffectInstance.getEffectType())) {
                TrinketComponent trinket = TrinketsApi.getTrinketComponent((LivingEntity) (Object) this).get();
                if (trinket.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
                    if (!statusEffectInstance.isInfinite()) {
                        //System.out.println("cancelled effect addition!");
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "clearStatusEffects", cancellable = true)
    private void cancelClear(CallbackInfoReturnable<Boolean> cir) {
        if(config.alchemicalStasisSootherPreventEffectRemove) {

            LivingEntity livingEntity = (LivingEntity)(Object)this;

            TrinketComponent trinket = TrinketsApi.getTrinketComponent(livingEntity).get();

            if (trinket.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
                //System.out.println("cancelled effect clear!");

                boolean removed = false;

                //when the clear command is called / milk is drank check if any active potion effects are blacklisted, and if they are, remove them.
                //no, intellij, i will not use your enhanced for because it crashes the game.
                for (Iterator<StatusEffectInstance> iter = livingEntity.getStatusEffects().iterator(); iter.hasNext();) {
                    StatusEffectInstance statusEffectInstance = iter.next();
                    if (TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, statusEffectInstance.getEffectType())) {
                        livingEntity.removeStatusEffect(statusEffectInstance.getEffectType());
                        removed = true;
                    }
                }
                cir.setReturnValue(removed);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "removeStatusEffect", cancellable = true)
    private void cancelRemove(StatusEffect statusEffect, CallbackInfoReturnable<Boolean> cir) {
        if(config.alchemicalStasisSootherPreventEffectRemove) {
            if (!TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, statusEffect)) {
                TrinketComponent trinket = TrinketsApi.getTrinketComponent((LivingEntity) (Object) this).get();
                if (trinket.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
                    //System.out.println("cancelled effect removal!");
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
