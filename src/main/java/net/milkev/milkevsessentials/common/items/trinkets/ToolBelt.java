package net.milkev.milkevsessentials.common.items.trinkets;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.tag.convention.v2.TagUtil;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
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

    public static void swap(ItemStack toolbelt, ServerPlayerEntity playerEntity) {

        SimpleInventory inventory = new SimpleInventory(9);
        ToolbeltComponent toolbeltComponent = toolbelt.getOrDefault(MilkevsEssentials.TOOLBELT_COMPONENT, new ToolbeltComponent(inventory));
        
        for(int i = 0; i < 9; i++) {
            ItemStack toHotBar = toolbeltComponent.getSimpleInventory().getStack(i);
            ItemStack toToolbelt = playerEntity.getInventory().getStack(i);
            if(TagUtil.isIn(MilkevsEssentials.TOOLBELT_BLACKLIST, toToolbelt.getItem())) {
                //by default this is just the toolbelt itself
                playerEntity.getInventory().offerOrDrop(toToolbelt);
                //System.out.println("oops!");
            } else {
                inventory.setStack(i, toToolbelt);
            }
            playerEntity.getInventory().setStack(i, toHotBar);
        }

        toolbelt.set(MilkevsEssentials.TOOLBELT_COMPONENT, new ToolbeltComponent(inventory));

    }


}
