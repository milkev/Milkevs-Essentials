package net.milkev.milkevsessentials.common.mixins;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.Accessory;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.tag.convention.v2.TagUtil;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Iterator;

@Mixin(LivingEntity.class)
public abstract class AlchemicalStasisSootherCancelAddRemoveMixin {

    @Shadow public abstract Collection<StatusEffectInstance> getStatusEffects();

    @Shadow public abstract boolean clearStatusEffects();

    @Unique
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    @Inject(at = @At("HEAD"), method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", cancellable = true)
    private void cancelAdd(StatusEffectInstance statusEffectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(config.alchemicalStasisSootherPreventEffectAdd) {
            if (!TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, statusEffectInstance.getEffectType().comp_349())) {
                try {
                    LivingEntity livingEntity = (LivingEntity)(Object)this;
                    
                    AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(livingEntity);
                    if (accessoriesCapability.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
                        if (!statusEffectInstance.isInfinite()) {
                            //System.out.println("cancelled effect addition!");
                            cir.setReturnValue(false);
                        }
                    }
                } catch(Exception e) {}
            }
        }
    }
    
    @Inject(at = @At("HEAD"), method = "clearStatusEffects", cancellable = true)
    private void cancelClear(CallbackInfoReturnable<Boolean> cir) {
        if(config.alchemicalStasisSootherPreventEffectRemove) {
            
            try {
                LivingEntity livingEntity = (LivingEntity) (Object) this;

                AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(livingEntity);

                if (accessoriesCapability.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
                    //System.out.println("cancelled effect clear!");

                    boolean removed = false;

                    //when the clear command is called / milk is drank check if any active potion effects are blacklisted, and if they are, remove them.
                    //no, intellij, i will not use your enhanced for because it crashes the game.
                    for (Iterator<StatusEffectInstance> iter = livingEntity.getStatusEffects().iterator(); iter.hasNext(); ) {
                        StatusEffectInstance statusEffectInstance = iter.next();
                        if (TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, statusEffectInstance.getEffectType().comp_349())) {
                            livingEntity.removeStatusEffect(statusEffectInstance.getEffectType());
                            removed = true;
                        }
                    }
                    cir.setReturnValue(removed);
                }
            } catch(Exception e) {
                //this is here incase accessoriesCapability produces null, which is intended by accessories, and just means that the entity we are working on cant have accessories equipped
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "removeStatusEffect", cancellable = true)
    private void cancelRemove(RegistryEntry<StatusEffect> registryEntry, CallbackInfoReturnable<Boolean> cir) {
        if(config.alchemicalStasisSootherPreventEffectRemove) {
            if (!TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, registryEntry.comp_349())) {
                try{
                    LivingEntity livingEntity = (LivingEntity) (Object) this;

                    AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(livingEntity);
                    if (accessoriesCapability.isEquipped(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER)) {
                        //System.out.println("cancelled effect removal!");
                        cir.setReturnValue(false);
                    }
                }  catch(Exception e) {
                    //this is here incase accessoriesCapability produces null, which is intended by accessories, and just means that the entity we are working on cant have accessories equipped
                }
            }
        }
    }
    
}
