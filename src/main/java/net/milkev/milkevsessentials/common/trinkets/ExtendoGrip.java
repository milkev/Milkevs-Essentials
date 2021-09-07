package net.milkev.milkevsessentials.common.trinkets;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class ExtendoGrip extends TrinketItem {
    private int reach;
    private int attack_reach;
    public ExtendoGrip(Settings settings, int reach_set, int attack_reach_set) {
        super(settings);
        reach = reach_set;
        attack_reach = attack_reach_set;
    }
    public Multimap<EntityAttribute, EntityAttributeModifier>
            getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {

        var modifiers = super.getModifiers(stack, slot, entity, uuid);

        //+x block reach
        modifiers.put(ReachEntityAttributes.REACH,
                new EntityAttributeModifier(uuid, "milkevsessentials:reach", reach,
                        EntityAttributeModifier.Operation.ADDITION));
        //+x attack reach
        modifiers.put(ReachEntityAttributes.ATTACK_RANGE,
                new EntityAttributeModifier(uuid, "milkevsessentials:attack_reach", attack_reach,
                        EntityAttributeModifier.Operation.ADDITION));

        return modifiers;
    }
}
