package net.milkev.milkevsessentials.common.mixins;

import io.wispforest.accessories.api.AccessoriesCapability;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class GluttonyCharmMixin {


    @Inject(at = @At("RETURN"), method = "canConsume", cancellable = true)
    public void canConsume(boolean bl, CallbackInfoReturnable<Boolean> cir) {
        try {
            LivingEntity livingEntity = (LivingEntity) (Object) this;

            AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(livingEntity);
            if (accessoriesCapability.isEquipped(MilkevsEssentials.GLUTTONY_CHARM) || accessoriesCapability.isEquipped(MilkevsEssentials.OP_GLUTTONY_CHARM)) {
                bl = true;
                cir.setReturnValue(true);
            }
        }  catch(Exception e) {
            //this is here incase accessoriesCapability produces null, which is intended by accessories, and just means that the entity we are working on cant have accessories equipped
        }
    }

}
