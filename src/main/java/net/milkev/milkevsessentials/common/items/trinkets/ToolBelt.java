package net.milkev.milkevsessentials.common.items.trinkets;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
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

    private static ItemStack n() {
        ItemStack stack = new ItemStack(Registries.ITEM.get(Identifier.of("minecraft:stick")));
        stack.setCount(1);
        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("empty_slot"));
        return stack;
    }

    public static void swap(ItemStack toolbelt, ServerPlayerEntity playerEntity) {

        ToolbeltComponent toolbeltComponent = toolbelt.getOrDefault(MilkevsEssentials.TOOLBELT_COMPONENT, new ToolbeltComponent(n(),n(),n(),n(),n(),n(),n(),n(),n()));


        ItemStack toPlayerInventory = toolbeltComponent.slot1();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory1 = playerEntity.getInventory().getStack(0);
        if(toToolbeltInventory1 == ItemStack.EMPTY) toToolbeltInventory1 = n();
        playerEntity.getInventory().setStack(0, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot2();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory2 = playerEntity.getInventory().getStack(1);
        if(toToolbeltInventory2 == ItemStack.EMPTY) toToolbeltInventory2 = n();
        playerEntity.getInventory().setStack(1, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot3();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory3 = playerEntity.getInventory().getStack(2);
        if(toToolbeltInventory3 == ItemStack.EMPTY) toToolbeltInventory3 = n();
        playerEntity.getInventory().setStack(2, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot4();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory4 = playerEntity.getInventory().getStack(3);
        if(toToolbeltInventory4 == ItemStack.EMPTY) toToolbeltInventory4 = n();
        playerEntity.getInventory().setStack(3, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot5();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory5 = playerEntity.getInventory().getStack(4);
        if(toToolbeltInventory5 == ItemStack.EMPTY) toToolbeltInventory5 = n();
        playerEntity.getInventory().setStack(4, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot6();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory6 = playerEntity.getInventory().getStack(5);
        if(toToolbeltInventory6 == ItemStack.EMPTY) toToolbeltInventory6 = n();
        playerEntity.getInventory().setStack(5, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot7();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory7 = playerEntity.getInventory().getStack(6);
        if(toToolbeltInventory7 == ItemStack.EMPTY) toToolbeltInventory7 = n();
        playerEntity.getInventory().setStack(6, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot8();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory8 = playerEntity.getInventory().getStack(7);
        if(toToolbeltInventory8 == ItemStack.EMPTY) toToolbeltInventory8 = n();
        playerEntity.getInventory().setStack(7, toPlayerInventory);

        toPlayerInventory = toolbeltComponent.slot9();
        if(Objects.equals(toPlayerInventory.get(DataComponentTypes.CUSTOM_NAME), Text.literal("empty_slot"))) toPlayerInventory = ItemStack.EMPTY;
        ItemStack toToolbeltInventory9 = playerEntity.getInventory().getStack(8);
        if(toToolbeltInventory9 == ItemStack.EMPTY) toToolbeltInventory9 = n();
        playerEntity.getInventory().setStack(8, toPlayerInventory);
        

        toolbelt.set(MilkevsEssentials.TOOLBELT_COMPONENT, new ToolbeltComponent(toToolbeltInventory1, toToolbeltInventory2, toToolbeltInventory3, toToolbeltInventory4, toToolbeltInventory5, toToolbeltInventory6, toToolbeltInventory7, toToolbeltInventory8, toToolbeltInventory9));

    }


}
