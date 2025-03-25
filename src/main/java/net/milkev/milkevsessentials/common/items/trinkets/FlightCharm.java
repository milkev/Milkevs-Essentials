package net.milkev.milkevsessentials.common.items.trinkets;

import io.github.ladysnake.pal.VanillaAbilities;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;

public class FlightCharm extends CharmWithTooltip {

    public FlightCharm(Settings settings) {
        super(settings, "flight_charm.tooltip");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(this);
        });
    }

    public void onEquip (ItemStack stack, SlotReference reference) {
        if (reference.entity() instanceof PlayerEntity player) {
            if (player.getWorld().isClient) return;
            MilkevsEssentials.FLIGHT_CHARM_FLIGHT_ABILITYSOURCE.grantTo(player, VanillaAbilities.ALLOW_FLYING);
        }
    }

    public void onUnequip(ItemStack stack, SlotReference reference) {
        if (reference.entity() instanceof PlayerEntity player) {
            if (player.getWorld().isClient) return;
            MilkevsEssentials.FLIGHT_CHARM_FLIGHT_ABILITYSOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
            if(player.getAbilities().flying && !VanillaAbilities.ALLOW_FLYING.isEnabledFor(player)) {
                player.getAbilities().flying = false;
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100));
            }
        }
    }
}
