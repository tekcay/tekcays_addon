package tekcays_addon.api.utils.blastfurnace;

import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.client.renderer.ICubeRenderer;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.metaTileEntities;

public class BlastFurnaceUtils {


    public static ICubeRenderer getBrickTexture(BlockBrick.BrickType brick) {

        if (brick.equals(BlockBrick.BrickType.BRICK)) return TKCYATextures.BRICK;
        if (brick.equals(BlockBrick.BrickType.FIRECLAY_BRICK)) return TKCYATextures.FIRECLAY_BRICK;
        if (brick.equals(BlockBrick.BrickType.REINFORCED_BRICK)) return TKCYATextures.REINFORCED_BRICK;
        if (brick.equals(BlockBrick.BrickType.STRONG_BRICK)) return TKCYATextures.STRONG_BRICK;

        return TKCYATextures.BRICK;
    }

    public static TraceabilityPredicate getOutputBrickFluidHatch(BlockBrick.BrickType brick) {

        if (brick.equals(BlockBrick.BrickType.BRICK)) return metaTileEntities(TKCYAMetaTileEntities.BRICK_EXPORT_FLUID_HATCH);
        if (brick.equals(BlockBrick.BrickType.FIRECLAY_BRICK)) return metaTileEntities(TKCYAMetaTileEntities.FIRECLAY_BRICK_EXPORT_FLUID_HATCH);
        if (brick.equals(BlockBrick.BrickType.REINFORCED_BRICK)) return metaTileEntities(TKCYAMetaTileEntities.REINFORCED_BRICK_EXPORT_FLUID_HATCH);
        if (brick.equals(BlockBrick.BrickType.STRONG_BRICK)) return metaTileEntities(TKCYAMetaTileEntities.STRONG_BRICK_EXPORT_FLUID_HATCH);

        return metaTileEntities(TKCYAMetaTileEntities.BRICK_EXPORT_FLUID_HATCH);
    }

    public static TraceabilityPredicate getInputBrickItemBus(BlockBrick.BrickType brick) {

        if (brick.equals(BlockBrick.BrickType.BRICK)) return metaTileEntities(TKCYAMetaTileEntities.BRICK_IMPORT_ITEM_BUS);
        if (brick.equals(BlockBrick.BrickType.FIRECLAY_BRICK)) return metaTileEntities(TKCYAMetaTileEntities.FIRECLAY_BRICK_IMPORT_ITEM_BUS);
        if (brick.equals(BlockBrick.BrickType.REINFORCED_BRICK)) return metaTileEntities(TKCYAMetaTileEntities.REINFORCED_BRICK_IMPORT_ITEM_BUS);
        if (brick.equals(BlockBrick.BrickType.STRONG_BRICK)) return metaTileEntities(TKCYAMetaTileEntities.STRONG_BRICK_IMPORT_ITEM_BUS);

        return metaTileEntities(TKCYAMetaTileEntities.BRICK_IMPORT_ITEM_BUS);
    }

}
