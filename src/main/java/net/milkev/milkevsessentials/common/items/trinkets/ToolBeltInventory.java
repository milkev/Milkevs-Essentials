package net.milkev.milkevsessentials.common.items.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

public class ToolBeltInventory extends SimpleInventory {

    public ToolBeltInventory() {
        super(9);
    }

    public void swapItems(ServerPlayerEntity player, ItemStack toolBelt) {

        PlayerInventory inventory = player.getInventory();

        for(int i = 0; i < 9; i++) {
            //itemstack that is currently in the belt, and will be moved out of the belt at the end
            ItemStack toHotbar = this.getStack(i);

            if (!toHotbar.isEmpty()) {
                this.setStack(i, inventory.getStack(i));
            } else {
                ItemStack fromHotbar = inventory.getStack(i);
                this.setStack(i, fromHotbar);
                inventory.setStack(i, toHotbar);
            }
            inventory.setStack(i, toHotbar);
        }
    }

    @Override
    public void readNbtList(NbtList nbtList) {
        this.clear();

        for(int i = 0; i < nbtList.size(); ++i) {
            ItemStack itemStack = ItemStack.fromNbt(nbtList.getCompound(i));
            if (!itemStack.isEmpty()) {
                this.setStack(i, itemStack);
            }
        }

    }
}
