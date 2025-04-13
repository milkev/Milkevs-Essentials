package net.milkev.milkevsessentials.common.items.trinkets;

import io.wispforest.accessories.api.AccessoryItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class CharmWithTooltip extends AccessoryItem {

    Text itemTooltip;

    public CharmWithTooltip(Settings settings, String itemTooltipID) {
        super(settings);
        itemTooltip = Text.translatable("item.milkevsessentials." + itemTooltipID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(this);
        });
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Text> tooltip, Item.TooltipContext tooltipContext, TooltipType tooltipType) {
        String multilineTooltip = itemTooltip.getString();
        if(multilineTooltip.contains("\n")) {
            String[] multilineTooltipArray = multilineTooltip.split("\n");
            for(int i = 0; i < multilineTooltipArray.length; i++) {
                tooltip.add(Text.of(multilineTooltipArray[i]));
            }
        } else {
            tooltip.add(itemTooltip);
        }

    }

}