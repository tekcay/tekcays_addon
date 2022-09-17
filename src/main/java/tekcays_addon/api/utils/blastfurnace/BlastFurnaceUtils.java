package tekcays_addon.api.utils.blastfurnace;

import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.client.renderer.ICubeRenderer;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityBrickFluidHatch;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityBrickItemBus;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityPrimitiveMufflerHatch;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.metaTileEntities;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;

public class BlastFurnaceUtils {

    public static final List<BlockBrick.BrickType> BRICKS = new ArrayList<BlockBrick.BrickType>() {{
       add(BlockBrick.BrickType.BRICK);
       add(BlockBrick.BrickType.REINFORCED_BRICK);
       add(BlockBrick.BrickType.FIRECLAY_BRICK);
       add(BlockBrick.BrickType.STRONG_BRICK);
    }};


    public static ICubeRenderer getBrickTexture(BlockBrick.BrickType brick) {

        if (brick.equals(BlockBrick.BrickType.BRICK)) return TKCYATextures.BRICK;
        if (brick.equals(BlockBrick.BrickType.FIRECLAY_BRICK)) return TKCYATextures.FIRECLAY_BRICK;
        if (brick.equals(BlockBrick.BrickType.REINFORCED_BRICK)) return TKCYATextures.REINFORCED_BRICK;
        if (brick.equals(BlockBrick.BrickType.STRONG_BRICK)) return TKCYATextures.STRONG_BRICK;

        return TKCYATextures.BRICK;
    }

    public static TraceabilityPredicate getOutputBrickFluidHatch(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickFluidHatch mte : BRICK_EXPORT_FLUID_HATCH) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_EXPORT_FLUID_HATCH[0]);
    }

    public static TraceabilityPredicate getInputBrickItemBus(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickItemBus mte : BRICK_ITEM_BUS) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_ITEM_BUS[0]);
    }

    public static TraceabilityPredicate getBrickMuffler(BlockBrick.BrickType brick) {
        for (MetaTileEntityPrimitiveMufflerHatch mte : PRIMITIVE_MUFFLER) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(PRIMITIVE_MUFFLER[0]);
    }

}
