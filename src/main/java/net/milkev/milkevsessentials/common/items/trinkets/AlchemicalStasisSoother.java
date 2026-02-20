package net.milkev.milkevsessentials.common.items.trinkets;

import io.wispforest.accessories.api.slot.SlotReference;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.tag.convention.v2.TagUtil;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class AlchemicalStasisSoother extends CharmWithTooltip {
    
    
    public AlchemicalStasisSoother(Settings settings, String itemTooltipID) {
        super(settings, itemTooltipID);
    }
    
    public static boolean sootherActive(Entity entity) {
        return entity.getAttached(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_ACTIVE);
    }
    
    public static void setSootherActive(Entity entity) {
        entity.setAttached(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_ACTIVE, true);
    }
    
    public static void setSootherInactive(Entity entity) {
        if(sootherActive(entity)) {
            entity.setAttached(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_ACTIVE, false);
        }
    }
    
    public static boolean effectBlacklisted(StatusEffect statusEffect) {
        return TagUtil.isIn(MilkevsEssentials.ALCHEMICAL_STASIS_SOOTHER_BLACKLIST, statusEffect);
    }
    
    public static boolean effectBlacklisted(RegistryEntry<StatusEffect> statusEffectRegistryEntry) {
        return effectBlacklisted(statusEffectRegistryEntry.comp_349());
    }
    
    public static boolean effectBlacklisted(StatusEffectInstance statusEffectInstance) {
        return effectBlacklisted(statusEffectInstance.getEffectType().comp_349());
    }
    
    public void onEquip (ItemStack stack, SlotReference reference) {
        //System.out.println("equipped! " + sootherActive(reference.entity()));
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if(config.alchemicalStasisSootherHideEffectVisuals) {
            List<StatusEffectInstance> effectsCopy = new ArrayList<>(reference.entity().getStatusEffects());
            for(StatusEffectInstance statusEffectInstance : effectsCopy) {
                if(statusEffectInstance != null) {
                    if(!effectBlacklisted(statusEffectInstance)) {
                        if (!statusEffectInstance.isInfinite() && (statusEffectInstance.shouldShowIcon() || statusEffectInstance.shouldShowParticles())) {
                            //System.out.println("replacing " + statusEffectInstance);
                            StatusEffectInstance replacementEffect = new StatusEffectInstance(statusEffectInstance.getEffectType(), statusEffectInstance.getDuration(), statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), false, false);
                            reference.entity().removeStatusEffect(statusEffectInstance.getEffectType());
                            reference.entity().addStatusEffect(replacementEffect);
                            //System.out.println("replaced with " + replacementEffect);
                        }
                    }
                }
            }
        }
        //System.out.println("Enabling!");
        setSootherActive(reference.entity());
    }
    
    public void onUnequip(ItemStack stack, SlotReference reference) {
        setSootherInactive(reference.entity());
        //System.out.println("Disabled " + sootherActive(reference.entity()));
    }

}
