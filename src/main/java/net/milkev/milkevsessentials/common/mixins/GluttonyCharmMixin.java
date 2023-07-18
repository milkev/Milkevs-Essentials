package net.milkev.milkevsessentials.common.mixins;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class GluttonyCharmMixin {


    @Inject(at = @At("RETURN"), method = "canConsume", cancellable = true)
    public void canConsume(boolean bl, CallbackInfoReturnable<Boolean> cir) {
        TrinketComponent trinket = TrinketsApi.getTrinketComponent((PlayerEntity)(Object)this).get();
        if(trinket.isEquipped(MilkevsEssentials.GLUTTONY_CHARM) || trinket.isEquipped(MilkevsEssentials.OP_GLUTTONY_CHARM)) {
            bl = true;
            cir.setReturnValue(true);
        }
    }

}
