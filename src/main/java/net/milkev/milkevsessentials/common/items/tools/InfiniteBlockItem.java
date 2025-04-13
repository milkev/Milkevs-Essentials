package net.milkev.milkevsessentials.common.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class InfiniteBlockItem extends Item {
    Block block;
    public InfiniteBlockItem(Settings settings, Block placeBlock) {
        super(settings);
        block = placeBlock;
    }
    
    @Override
    public ActionResult useOnBlock(ItemUsageContext itemUsageContext) {
        BlockPos blockPos = itemUsageContext.getBlockPos();
        World world = itemUsageContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        if(!blockState.canReplace(new ItemPlacementContext(itemUsageContext))) {
            switch (itemUsageContext.getSide()) {
                case UP -> blockPos = blockPos.up();
                case DOWN -> blockPos = blockPos.down();
                case NORTH -> blockPos = blockPos.north();
                case SOUTH -> blockPos = blockPos.south();
                case WEST -> blockPos = blockPos.west();
                case EAST -> blockPos = blockPos.east();
            }
        }
        blockState = world.getBlockState(blockPos);
        if(world.canPlace(block.getDefaultState(), blockPos, ShapeContext.absent())) {
            if(blockState.canReplace(new ItemPlacementContext(itemUsageContext))) {
                
                world.setBlockState(blockPos, block.getDefaultState());
                
                BlockSoundGroup blockSoundGroup = block.getDefaultState().getSoundGroup();
                world.playSound(itemUsageContext.getPlayer(), 
                        blockPos, 
                        blockSoundGroup.getPlaceSound(), 
                        SoundCategory.BLOCKS, 
                        (blockSoundGroup.getVolume() + 1.0F) / 2.0F, 
                        blockSoundGroup.getPitch() * 0.8F);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(itemUsageContext.getPlayer(), blockState));
                
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }
    
    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return stack.copy();
    }
    
    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType tooltipType) {        
        tooltip.add(Text.translatable("item.milkevsessentials.singularity_descriptor"));
    }
    
    /*
    @Override
    public ActionResult place(ItemPlacementContext itemPlacementContext) {
        if (!this.getBlock().isEnabled(itemPlacementContext.getWorld().getEnabledFeatures())) {
            return ActionResult.FAIL;
        } else if (!itemPlacementContext.canPlace()) {
            return ActionResult.FAIL;
        } else {
            ItemPlacementContext itemPlacementContext2 = this.getPlacementContext(itemPlacementContext);
            if (itemPlacementContext2 == null) {
                return ActionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext2);
                if (blockState == null) {
                    return ActionResult.FAIL;
                } else if (!this.place(itemPlacementContext2, blockState)) {
                    return ActionResult.FAIL;
                } else {
                    BlockPos blockPos = itemPlacementContext2.getBlockPos();
                    World world = itemPlacementContext2.getWorld();
                    PlayerEntity playerEntity = itemPlacementContext2.getPlayer();
                    ItemStack itemStack = itemPlacementContext2.getStack();
                    BlockState blockState2 = world.getBlockState(blockPos);
                    if (blockState2.isOf(blockState.getBlock())) {
                        blockState2 = this.placeFromNbt(blockPos, world, itemStack, blockState2);
                        this.postPlacement(blockPos, world, playerEntity, itemStack, blockState2);
                        this.copyComponentsToBlockEntity(world, blockPos, itemStack);
                        blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                        }
                    }

                    BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
                    world.playSound(playerEntity, blockPos, this.getPlaceSound(blockState2), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));
                    //itemStack.decrementUnlessCreative(1, playerEntity);
                    return ActionResult.success(world.isClient);
                }
            }
        }
    }
     */
}
