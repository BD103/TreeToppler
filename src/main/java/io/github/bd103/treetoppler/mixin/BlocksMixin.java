package io.github.bd103.treetoppler.mixin;

import io.github.bd103.treetoppler.ToppleBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    // Make all log types ToppleBlocks
    @Inject(at = @At("HEAD"), method = "createLogBlock(Lnet/minecraft/block/MapColor;Lnet/minecraft/block/MapColor;)Lnet/minecraft/block/Block;", cancellable = true)
    private static void createLogBlock(MapColor topMapColor, MapColor sideMapColor, CallbackInfoReturnable<PillarBlock> cir) {
        cir.setReturnValue(ToppleBlock.createLogBlock(topMapColor, sideMapColor));
    }

    // Make cherry logs ToppleBlocks
    @Inject(at = @At("HEAD"), method = "createLogBlock(Lnet/minecraft/block/MapColor;Lnet/minecraft/block/MapColor;Lnet/minecraft/sound/BlockSoundGroup;)Lnet/minecraft/block/Block;", cancellable = true)
    private static void createBambooBlock(MapColor topMapColor, MapColor sideMapColor, BlockSoundGroup soundGroup, CallbackInfoReturnable<PillarBlock> cir) {
        // Only modify cherry wood, not bamboo blocks
        if (soundGroup == BlockSoundGroup.CHERRY_WOOD) {
            cir.setReturnValue(ToppleBlock.createLogBlock(topMapColor, sideMapColor, soundGroup));
        }
    }

    // Make all nether log types ToppleBlocks
    @Inject(at = @At("HEAD"), method = "createNetherStemBlock", cancellable = true)
    private static void createNetherStemBlock(MapColor mapColor, CallbackInfoReturnable<PillarBlock> cir) {
        cir.setReturnValue(ToppleBlock.createNetherStemBlock(mapColor));
    }
}
