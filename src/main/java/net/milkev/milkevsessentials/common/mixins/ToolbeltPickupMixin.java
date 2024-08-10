package net.milkev.milkevsessentials.common.mixins;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.items.trinkets.ToolBelt;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class ToolbeltPickupMixin {

    /*
    @Mutable
    @Final
    @Shadow
    public final PlayerEntity player;

    @Shadow public abstract int getEmptySlot();

    @Shadow public abstract int getOccupiedSlotWithRoomForStack(ItemStack itemStack);

    //Intellij wouldnt let me launch without doing this... afaik this shouldnt be necessary thou ;-;
    public ToolbeltPickupMixin(PlayerEntity player) {
        this.player = player;
    }

    @Inject(at = @At("HEAD"), method = "insertStack(ILnet/minecraft/item/ItemStack;)Z")
    public void insertStack(int i, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        //System.out.println("Hah!");
        int slotNumber = i;
        if(i == -1) {
            slotNumber = this.getOccupiedSlotWithRoomForStack(itemStack);
            if(slotNumber == -1) {
                slotNumber = Math.max(this.getEmptySlot(), 9);
            }
        }
        //System.out.println(slotNumber);
        if(player instanceof ServerPlayerEntity serverPlayerEntity && slotNumber > 8 ) {

            TrinketComponent trinketComponent = TrinketsApi.getTrinketComponent(serverPlayerEntity).get();
            if(trinketComponent.isEquipped(MilkevsEssentials.TOOL_BELT)) {

                ItemStack toolBelt = ItemStack.EMPTY;

                for(int j = 0; j <trinketComponent.getAllEquipped().size(); j++) {
                    if(trinketComponent.getAllEquipped().get(j).getRight().isOf(MilkevsEssentials.TOOL_BELT)) {
                        toolBelt = trinketComponent.getAllEquipped().get(j).getRight();
                    }
                }
                ToolBelt.save(ToolBelt.tryAddToExistingStack(itemStack, ToolBelt.load(toolBelt), player), toolBelt);
            }
        }
    }*/

    /*
    public void triggerItemPickedUpByEntityCriteria(ItemEntity itemEntity) {
        System.out.println("Hah");
    }*/

    /*
    public void sendPickup(Entity entity, int i) {
        System.out.println("Hah");
    }*/

    /*
    @Inject(at = @At("HEAD"), method = "triggerItemPickedUpByEntityCriteria", cancellable = true)
    public void triggerItemPickedUpByEntityCriteria(ItemEntity itemEntity, CallbackInfo ci) {
        itemEntity.getStack().setCount(2);
        System.out.println("HI YOU PICKED UP: " + itemEntity.getStack().getItem().getName());
        ci();
    }*/

}
