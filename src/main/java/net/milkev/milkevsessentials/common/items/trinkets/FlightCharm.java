package net.milkev.milkevsessentials.common.items.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.ladysnake.pal.VanillaAbilities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class FlightCharm extends CharmWithTooltip {

    public FlightCharm(Settings settings) {
        super(settings, "flight_charm.tooltip", "flight_charm");
        TrinketsApi.registerTrinket(this, this);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(this);
        });
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onEquip(stack, slot, entity);

        if (entity instanceof PlayerEntity player) {
            if (player.getWorld().isClient) return;
            MilkevsEssentials.FLIGHT_CHARM_FLIGHT_ABILITYSOURCE.grantTo(player, VanillaAbilities.ALLOW_FLYING);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);

        if (entity instanceof PlayerEntity player) {
            if (player.getWorld().isClient) return;
            MilkevsEssentials.FLIGHT_CHARM_FLIGHT_ABILITYSOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
            if(player.getAbilities().flying && !VanillaAbilities.ALLOW_FLYING.isEnabledFor(player)) {
                player.getAbilities().flying = false;
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100));
            }
        }
    }
}
