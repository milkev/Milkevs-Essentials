package net.milkev.milkevsessentials.common.mixins;

import io.wispforest.accessories.api.AccessoriesCapability;
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
        try {
            AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(playerEntity);
            if (accessoriesCapability.isEquipped(MilkevsEssentials.OP_GLUTTONY_CHARM)) {
                add(1, 1.0F);
            }
        } catch(Exception e) {
            //this is here incase accessoriesCapability produces null, which is intended by accessories, and just means that the entity we are working on cant have accessories equipped
            //this should never happen for this mixin in particular, but who knows
        }
    }
}
