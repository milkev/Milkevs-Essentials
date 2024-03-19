package net.milkev.milkevsessentials.common.items.trinkets;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class CharmWithTooltip extends TrinketItem {

    Text itemTooltip;
    Text slotType;

    public CharmWithTooltip(Settings settings, String itemTooltipID, String slotTypeID) {
        super(settings);
        itemTooltip = Text.translatable("item.milkevsessentials." + itemTooltipID);
        slotType = Text.translatable("item.milkevsessentials.slot." + slotTypeID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(this);
        });
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        tooltip.add(slotType);
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