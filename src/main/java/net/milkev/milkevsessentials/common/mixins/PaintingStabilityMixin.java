package net.milkev.milkevsessentials.common.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractDecorationEntity.class)
public abstract class PaintingStabilityMixin {


    @Shadow protected abstract Box calculateBoundingBox(BlockPos blockPos, Direction direction);

    @Inject(at = @At("RETURN"), method = "canStayAttached", cancellable = true) 
    public void makeItStay(CallbackInfoReturnable<Boolean> cir) {
        AbstractDecorationEntity entity = (AbstractDecorationEntity) (Object) this;
        if(entity.age > 10) {
            if (!entity.getWorld().isSpaceEmpty(entity)) {
                boolean bl = (BlockPos.stream(this.calculateBoundingBox(entity.getAttachedBlockPos(), entity.getFacing()).contract(0.01)).anyMatch((blockPos) -> {
                    BlockState blockState = entity.getWorld().getBlockState(blockPos);
                    //System.out.println("Painting canStayAttached checked [" + blockPos + "] and the result was: " + (blockState.isFullCube(null, blockPos) ? "Full Cube" : "Non-Full Cube"));
                    return blockState.isFullCube(null, blockPos);
                }));
                //System.out.println("Painting canStayAttached recalculated to; " + !bl);
                cir.setReturnValue(!bl);
            }
        }
    }
}
