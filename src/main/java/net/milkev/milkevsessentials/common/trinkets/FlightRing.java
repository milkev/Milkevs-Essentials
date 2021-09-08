package net.milkev.milkevsessentials.common.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class FlightRing extends TrinketItem {

    public FlightRing(Settings settings) {
        super(settings);
        TrinketsApi.registerTrinket(this,this);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) entity);
            if(TrinketsApi.getTrinketComponent(player).get().isEquipped(MilkevsEssentials.EXTENDO_GRIP_NORMAL)) {
                player.getAbilities().allowFlying = true;
            } else if(!player.isSpectator() || !player.isCreative()) {
                player.getAbilities().allowFlying = false;
            }
        }
        super.tick(stack,slot,entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) entity);
            if(!player.isSpectator() || !player.isCreative()) {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
            }
        }
        super.onUnequip(stack, slot, entity);
    }
}
