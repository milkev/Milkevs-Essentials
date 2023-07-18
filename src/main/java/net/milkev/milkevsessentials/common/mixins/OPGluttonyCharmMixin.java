package net.milkev.milkevsessentials.common.mixins;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class OPGluttonyCharmMixin {


    @Shadow public abstract void add(int i, float f);

    @Inject(at = @At("HEAD"), method = "update")
    public void update(PlayerEntity playerEntity, CallbackInfo ci) {
        TrinketComponent trinket = TrinketsApi.getTrinketComponent(playerEntity).get();
        if(trinket.isEquipped(MilkevsEssentials.OP_GLUTTONY_CHARM)) {
            add(1, 1.0F);
        }
    }
}
