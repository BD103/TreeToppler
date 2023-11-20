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
    @Inject(at = @At("HEAD"), method = "createLogBlock", cancellable = true)
    private static void createLogBlock(MapColor topMapColor, MapColor sideMapColor, CallbackInfoReturnable<PillarBlock> cir) {
        cir.setReturnValue(ToppleBlock.createLogBlock(topMapColor, sideMapColor));
    }

    // Make cherry logs ToppleBlocks
    //
    // TODO(BD103): Rename to createLogBlock for next stable Minecraft release.
    // https://github.com/FabricMC/yarn/pull/3715
    @Inject(at = @At("HEAD"), method = "createBambooBlock", cancellable = true)
    private static void createBambooBlock(MapColor topMapColor, MapColor sideMapColor, BlockSoundGroup soundGroup, CallbackInfoReturnable<PillarBlock> cir) {
        // Only modify cherry wood, not bamboo blocks
        if (soundGroup == BlockSoundGroup.CHERRY_WOOD) {
            cir.setReturnValue(ToppleBlock.createBambooBlock(topMapColor, sideMapColor, soundGroup));
        }
    }

    // Make all nether log types ToppleBlocks
    @Inject(at = @At("HEAD"), method = "createNetherStemBlock", cancellable = true)
    private static void createNetherStemBlock(MapColor mapColor, CallbackInfoReturnable<PillarBlock> cir) {
        cir.setReturnValue(ToppleBlock.createNetherStemBlock(mapColor));
    }
}
