package net.milkev.milkevsessentials.common.mixins;

import io.wispforest.accessories.api.AccessoriesCapability;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.items.trinkets.ToolBelt;
import net.milkev.milkevsessentials.common.items.trinkets.ToolbeltComponent;
import net.minecraft.component.Component;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(PlayerInventory.class)
public abstract class ToolbeltPickupMixin {
    
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
    public void insertStack(int slot, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        //System.out.println("Hah!");
        int slotNumber = slot;
        if(slot == -1) {
            slotNumber = this.getOccupiedSlotWithRoomForStack(itemStack);
            if(slotNumber == -1) {
                slotNumber = Math.max(this.getEmptySlot(), 9);
            }
        }
        //System.out.println(slotNumber);
        if(player instanceof ServerPlayerEntity serverPlayerEntity && slotNumber > 8 ) {

            AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(serverPlayerEntity);
            if(accessoriesCapability.isEquipped(MilkevsEssentials.TOOL_BELT)) {

                ItemStack toolBelt = accessoriesCapability.getFirstEquipped(MilkevsEssentials.TOOL_BELT).stack();

                ToolbeltComponent toolbeltComponent = toolBelt.getOrDefault(MilkevsEssentials.TOOLBELT_COMPONENT, new ToolbeltComponent(new SimpleInventory(9)));
                SimpleInventory inventory = toolbeltComponent.getSimpleInventory();
                
                Set<Item> check = new HashSet<>();
                check.add(itemStack.getItem());
                if(inventory.containsAny(check)) {
                    for (int i = 0; i < inventory.getHeldStacks().size(); i++) {
                        ItemStack stackToolbelt = inventory.getStack(i);
                        if(stackToolbelt.getItem().equals(itemStack.getItem()) && stackToolbelt.getCount() < stackToolbelt.getMaxCount()) {
                            if(stackToolbelt.getCount() + itemStack.getCount() <= stackToolbelt.getMaxCount()) {
                                stackToolbelt.increment(itemStack.getCount());
                                itemStack.decrement(itemStack.getCount());
                                serverPlayerEntity.getServerWorld().playSound(null, serverPlayerEntity.getBlockPos(), MilkevsEssentials.TOOLBELT_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
                            } else if(stackToolbelt.getCount() + itemStack.getCount() > stackToolbelt.getMaxCount()) {
                                int remainder = itemStack.getCount() - (stackToolbelt.getMaxCount() - stackToolbelt.getCount());
                                stackToolbelt.setCount(stackToolbelt.getMaxCount());
                                itemStack.decrement(remainder);
                            }
                        }
                    }
                }
                toolBelt.set(MilkevsEssentials.TOOLBELT_COMPONENT, new ToolbeltComponent(inventory));
            }
        }
    }

}
