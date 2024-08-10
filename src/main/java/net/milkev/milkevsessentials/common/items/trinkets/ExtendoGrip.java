package net.milkev.milkevsessentials.common.items.trinkets;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class ExtendoGrip extends TrinketItem {
    private int reach;
    private int attack_reach;

    public ExtendoGrip(Settings settings, int reach_set, int attack_reach_set) {
        super(settings);
        reach = reach_set;
        attack_reach = attack_reach_set;
    }

    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {

        var modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);

        //+x block reach
        modifiers.put(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                new EntityAttributeModifier(
                        Identifier.of("milkevsessentials:reach"),
                        reach,
                        EntityAttributeModifier.Operation.ADD_VALUE));
        //+x attack reach

        modifiers.put(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                new EntityAttributeModifier(
                        Identifier.of("milkevsessentials:attack_reach"),
                        attack_reach,
                        EntityAttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }
}
