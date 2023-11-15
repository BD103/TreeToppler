package io.github.bd103.treetoppler;

import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ToppleBlock extends PillarBlock {
    private static final int FALL_DELAY = 2;

    private ToppleBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public static ToppleBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new ToppleBlock(
                AbstractBlock.Settings.create()
                        .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                        .instrument(Instrument.BASS)
                        .strength(2.0F)
                        .sounds(BlockSoundGroup.WOOD)
                        .burnable()
        );
    }

    public static ToppleBlock createBambooBlock(MapColor topMapColor, MapColor sideMapColor, BlockSoundGroup soundGroup) {
        return new ToppleBlock(
                AbstractBlock.Settings.create()
                .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .instrument(Instrument.BASS)
                .strength(2.0F)
                .sounds(soundGroup)
                .burnable()
        );
    }

    public static ToppleBlock createNetherStemBlock(MapColor mapColor) {
        return new ToppleBlock(
                AbstractBlock.Settings.create()
                        .mapColor(state -> mapColor)
                        .instrument(Instrument.BASS)
                        .strength(2.0F)
                        .sounds(BlockSoundGroup.NETHER_STEM)
        );
    }

    // Make block possibly fall when state changed / created
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, FALL_DELAY);
    }

    // Make block possibly fall when neighboring blocks update
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleBlockTick(pos, this, FALL_DELAY);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // Make block fall if possible
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (FallingBlock.canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
            FallingBlockEntity.spawnFromBlock(world, pos, state);
        }
    }
}
