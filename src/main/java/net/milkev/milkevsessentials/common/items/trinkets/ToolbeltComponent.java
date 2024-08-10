package net.milkev.milkevsessentials.common.items.trinkets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public record ToolbeltComponent(ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot4, ItemStack slot5, ItemStack slot6, ItemStack slot7, ItemStack slot8, ItemStack slot9) {

    public static final Codec<ToolbeltComponent> CODEC = RecordCodecBuilder.create( builder -> {
        return builder.group(
                ItemStack.CODEC.fieldOf("slot1").forGetter(ToolbeltComponent::slot1),
                ItemStack.CODEC.fieldOf("slot2").forGetter(ToolbeltComponent::slot2),
                ItemStack.CODEC.fieldOf("slot3").forGetter(ToolbeltComponent::slot3),
                ItemStack.CODEC.fieldOf("slot4").forGetter(ToolbeltComponent::slot4),
                ItemStack.CODEC.fieldOf("slot5").forGetter(ToolbeltComponent::slot5),
                ItemStack.CODEC.fieldOf("slot6").forGetter(ToolbeltComponent::slot6),
                ItemStack.CODEC.fieldOf("slot7").forGetter(ToolbeltComponent::slot7),
                ItemStack.CODEC.fieldOf("slot8").forGetter(ToolbeltComponent::slot8),
                ItemStack.CODEC.fieldOf("slot9").forGetter(ToolbeltComponent::slot9)
        ).apply(builder, ToolbeltComponent::new);
    });
}
