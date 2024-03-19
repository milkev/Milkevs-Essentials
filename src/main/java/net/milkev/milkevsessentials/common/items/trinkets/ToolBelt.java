package net.milkev.milkevsessentials.common.items.trinkets;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.Objects;

public class ToolBelt extends CharmWithTooltip {

    public ToolBelt(Settings settings) {
        super(settings, "toolbelt.tooltip",  "toolbelt");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(this);
        });
    }

    public static void save(ToolBeltInventory toolBeltInventory, ItemStack itemStack) {
        for(int i = 0; i < 9; i++) {
            NbtCompound inventoryAsNbt = toolBeltInventory.getStack(i).writeNbt(new NbtCompound());
            //System.out.println(i);
            //System.out.println("Saving from: " + toolBeltInventory.getStack(i));
            //System.out.println("Saving as: " + inventoryAsNbt);
            itemStack.setSubNbt(String.valueOf(i), inventoryAsNbt);
        }
    }

    public static ToolBeltInventory load(ItemStack itemStack) {

        ToolBeltInventory toolBeltInventory = new ToolBeltInventory();

        if(itemStack.hasNbt()) {

            if(itemStack.getNbt().asString().contains("Slot_")) {
                itemStack = updateOldItem(itemStack);
            }

            NbtList nbtList = new NbtList();
            for (int i = 0; i < 9; i++) {
                //System.out.println("Nbt of " + i + ": " + itemStack.getNbt());
                nbtList.add(itemStack.getSubNbt(String.valueOf(i)));
            }

            toolBeltInventory.readNbtList(nbtList);
        }

        return toolBeltInventory;
    }

    public static ToolBeltInventory tryAddToExistingStack(ItemStack itemStack, ToolBeltInventory toolBeltInventory, PlayerEntity player) {
        //System.out.println("Attempting to add " + itemStack + " to Toolbelt");
        boolean success = false;
        for(int i = 0; i < 9; i++) {

            ItemStack toolbeltItem = toolBeltInventory.getStack(i);

            System.out.println(toolbeltItem.getNbt());
            System.out.println(itemStack.getNbt());

            //if the toolbelt stack and itemstack have the same nbt data
            boolean sameNbtorNoNbt = false;
            if(itemStack.hasNbt() && toolbeltItem.hasNbt()) {
                if(toolbeltItem.getNbt().equals(itemStack.getNbt())) {
                    //if nbt matches
                    sameNbtorNoNbt = true;
                }
            }  else if(!itemStack.hasNbt()) {
                //if there is no nbt
                sameNbtorNoNbt = true;
            }

                    //if items are the same item
            if(toolbeltItem.getItem() == itemStack.getItem()
                    //if we are actually picking up something
                    && !itemStack.isEmpty()
                    //if toolbelt stack isnt full
                    && toolbeltItem.getMaxCount() != toolbeltItem.getCount()
                    //if the toolbelt stack and itemstack have the same nbt data, or if there is no nbt data
                    && sameNbtorNoNbt) {

                    //if stacks add up to or less than max stack count
                    if(toolbeltItem.getMaxCount() >= toolbeltItem.getCount() + itemStack.getCount()) {
                        toolbeltItem.increment(itemStack.getCount());
                        itemStack.decrement(toolbeltItem.getCount() - itemStack.getCount());
                        success = true;

                    //if stacks add up to more than max stack count
                    } else {
                        itemStack.decrement(toolbeltItem.getMaxCount() - toolbeltItem.getCount());
                        toolbeltItem.setCount(toolbeltItem.getMaxCount());
                        success = true;

                    }
                    System.out.println("Added to " + toolbeltItem + " in toolbelt slot " + i);
            }
        }
        if(success && !player.getWorld().isClient) {
            player.getWorld().playSound(null, player.getBlockPos(), MilkevsEssentials.TOOLBELT_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
            //System.out.println("Attempted Sound: " + MilkevsEssentials.TOOLBELT_PICKUP.getId());
        }
        return toolBeltInventory;
    }

    //Updates old custom NBT storage logic into the new SimpleInventory implementation
    private static ItemStack updateOldItem(ItemStack toolBelt) {
        ToolBeltInventory toolBeltInventory = new ToolBeltInventory();
        for(int i = 0; i < 9; i++) {

            //init variable holding itemstack that is currently IN OLD TOOLBELT NBT
            ItemStack oldItem = ItemStack.EMPTY;
            //grab identifier of item IN OLD TOOLBELT NBT
            Identifier oldItemID = makeIdentifier(toolBelt, i);
            //grab nbt of item IN OLD TOOLBELT NBT
            NbtElement oldItemNbt = toolBelt.getOrCreateNbt().get("Slot_" + i + "_nbt");
            //grab count of item IN OLD TOOLBELT NBT
            int count = 0;
            assert toolBelt.getNbt() != null;
            if(toolBelt.getNbt().contains("Slot_" + i + "_count")) {
                count = Integer.parseInt(Objects.requireNonNull(toolBelt.getOrCreateNbt().get("Slot_" + i + "_count")).asString());
            }


            if (!oldItemID.equals(new Identifier("minecraft", "air"))) {
                oldItem = new ItemStack(Registries.ITEM.get(oldItemID), count);
                if (oldItemNbt != null) {
                    oldItem.setNbt((NbtCompound) oldItemNbt);
                }
            }
            toolBeltInventory.setStack(i, oldItem);
        }
        toolBelt.setNbt(new NbtCompound());
        save(toolBeltInventory, toolBelt);
        System.out.println("Old milkevsessentials:toolbelt has been successfully updated to new save format");
        System.out.println(toolBeltInventory);
        return toolBelt;
    }

    //Old custom NBT based code. Only used to update from old format
    private static Identifier makeIdentifier(ItemStack toolBelt, int i) {
        String toolBeltItemIDString = "minecraft:air";
        if(toolBelt.getOrCreateNbt().contains("Slot_" + i + "_identifier")) {
            toolBeltItemIDString = toolBelt.getOrCreateNbt().get("Slot_" + i + "_identifier").toString();
        }
        String toolBeltItemIDMOD_ID = toolBeltItemIDString.substring(1,toolBeltItemIDString.indexOf(":"));
        //System.out.println("MODID OF ITEM TO GO TO HOTBAR: " + toolBeltItemIDMOD_ID);
        String toolBeltItemIDNamespace = toolBeltItemIDString.substring(toolBeltItemIDString.indexOf(":")+1, toolBeltItemIDString.length()-1);
        //System.out.println("NAMESPACE OF ITEM TO GO TO HOTBAR: " + toolBeltItemIDNamespace);
        return new Identifier(toolBeltItemIDMOD_ID, toolBeltItemIDNamespace);
    }


}
