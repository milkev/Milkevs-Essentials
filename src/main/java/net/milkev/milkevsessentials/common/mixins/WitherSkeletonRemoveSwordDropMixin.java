package net.milkev.milkevsessentials.common.mixins;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonRemoveSwordDropMixin {
    @Inject(at = @At("TAIL"), method = "initEquipment")
    private void removeSwordDrop(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        ((MobEntity) (Object) this).setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
    }
}
