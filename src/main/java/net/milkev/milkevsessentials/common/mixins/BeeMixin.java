package net.milkev.milkevsessentials.common.mixins;

import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(BeeEntity.class)
public class BeeMixin {
    
    @Shadow
    private int ticksInsideWater;
    
    @Unique
    private int timeSinceNesting;
    
    @Unique
    private BlockPos prevPos;
    @Unique
    private int timeStuck;
    
    @Unique
    BeeEntity thisBee = (BeeEntity) (Object) this;
    
    @Inject(at = @At("HEAD"), method = "mobTick") 
    protected void mobTick(CallbackInfo ci) {
        
        //disable water damage cus bees are dumb shits and always touch water somehow
        ticksInsideWater = 0;
        
        //is a bee has been outside the hive for >10min, just teleport back into the hive if the hive can take it. it probably wandered too far or its ai is refusing to return to the hive
        timeSinceNesting++;
        if(timeSinceNesting > 12000) {
            timeSinceNesting = 0;
            returnToHive(thisBee.getHivePos());
        }
        
        //if a bee has been in the same location for more than 30 seconds, also teleport it back to the hive if the hive can take it. its probably stuck trying to fly through some block it cant go through
        BlockPos pos = BlockPos.ofFloored(thisBee.getPos());
        if(pos != null) {
            //System.out.println("current position! " + pos);
            if(prevPos != null) {
                //System.out.println("Previous position! " + prevPos);
                if (Objects.equals(pos, prevPos)) {
                    timeStuck++;
                    if (timeStuck > 600) {
                        timeStuck = 0;
                        returnToHive(thisBee.getHivePos());
                    }
                } else {
                    timeStuck = 0;
                }
            }
            prevPos = pos;
        }
    }
    
    @Unique
    private void returnToHive(BlockPos blockPos) {
        if(blockPos == null) return;
        
        BlockEntity blockEntity = thisBee.getWorld().getBlockEntity(blockPos);
        if(blockEntity instanceof BeehiveBlockEntity beehiveBlockEntity) {
            beehiveBlockEntity.tryEnterHive(thisBee);
        }
    }
}
