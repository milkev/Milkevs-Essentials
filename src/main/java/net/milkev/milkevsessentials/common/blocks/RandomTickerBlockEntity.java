package net.milkev.milkevsessentials.common.blocks;

import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class RandomTickerBlockEntity extends BlockEntity implements BlockEntityTicker<RandomTickerBlockEntity> {
    
    
    public RandomTickerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MilkevsEssentials.RANDOM_TICKER_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, RandomTickerBlockEntity blockEntity) {
        if(!world.isClient()) {
            world.getProfiler().push("randomTick");
            Random random = Random.create();
            BlockPos targetBlockPos = blockPos.add(random.nextBetween(-16, 16), random.nextBetween(-16, 16), random.nextBetween(-16, 16));
            BlockState targetBlockState = world.getBlockState(targetBlockPos);
            if (targetBlockState.hasRandomTicks()) {
                targetBlockState.randomTick((ServerWorld) world, targetBlockPos, random);
                //System.out.println("Applied random tick at; " + targetBlockPos);
            } else {
                FluidState fluidState = targetBlockState.getFluidState();
                if(fluidState.hasRandomTicks()) {
                    fluidState.onRandomTick(world, targetBlockPos, random);
                }
            }
            world.getProfiler().pop();
        }
    }
}
