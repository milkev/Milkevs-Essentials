package net.milkev.milkevsessentials.common.items.trinkets;

import com.mojang.serialization.Codec;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.util.List;

public final class ToolbeltComponent {
    
    public static final Codec<ToolbeltComponent> CODEC = ItemStack.OPTIONAL_CODEC.listOf()
            .xmap(ToolbeltComponent::new, component -> component.simpleInventory.getHeldStacks());
    
    private final SimpleInventory simpleInventory;
    
    public ToolbeltComponent(SimpleInventory simpleInventory) {
        this.simpleInventory = simpleInventory;
    }
    
    public ToolbeltComponent(List<ItemStack> stacks) {
        this.simpleInventory = new SimpleInventory(9);
        for(int i = 0; i < 9; i++) {
            this.simpleInventory.setStack(i, stacks.get(i));
        }
    }
    
    public SimpleInventory getSimpleInventory() {
        return this.simpleInventory;
    }
}
