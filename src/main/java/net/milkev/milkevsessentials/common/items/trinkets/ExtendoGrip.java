package net.milkev.milkevsessentials.common.items.trinkets;

import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.components.AccessoryItemAttributeModifiers;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ExtendoGrip extends AccessoryItem {
    private int reach;
    private int attack_reach;

    public ExtendoGrip(Settings settings, int reach_set, int attack_reach_set) {
        super(settings);
        reach = reach_set;
        attack_reach = attack_reach_set;
    }

    public void getStaticModifiers(Item item, AccessoryItemAttributeModifiers.Builder builder) {
        
        builder.addForAny(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, 
                new EntityAttributeModifier(
                Identifier.of("milkevsessentials:reach"),
                reach,
                EntityAttributeModifier.Operation.ADD_VALUE), 
                false);
        
        builder.addForAny(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, 
                new EntityAttributeModifier(
                Identifier.of("milkevsessentials:attack_reach"),
                attack_reach,
                EntityAttributeModifier.Operation.ADD_VALUE),
                false);
        
    }
}
