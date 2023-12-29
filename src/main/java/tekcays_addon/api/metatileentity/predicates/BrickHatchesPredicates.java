package tekcays_addon.api.metatileentity.predicates;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.metaTileEntities;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.pattern.TraceabilityPredicate;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.metatileentities.multiblockpart.brick.MetaTileEntityBrickFluidHatch;
import tekcays_addon.common.metatileentities.multiblockpart.brick.MetaTileEntityBrickItemBus;
import tekcays_addon.common.metatileentities.multiblockpart.brick.MetaTileEntityPrimitiveMufflerHatch;

public class BrickHatchesPredicates {

    public static final List<BlockBrick.BrickType> BRICKS = new ArrayList<BlockBrick.BrickType>() {

        {
            add(BlockBrick.BrickType.BRICK);
            add(BlockBrick.BrickType.REINFORCED_BRICK);
            add(BlockBrick.BrickType.FIRECLAY_BRICK);
            add(BlockBrick.BrickType.STRONG_BRICK);
        }
    };

    public static TraceabilityPredicate getInputBrickFluidHatch(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickFluidHatch mte : BRICK_IMPORT_FLUID_HATCH) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_IMPORT_FLUID_HATCH[0]);
    }

    public static TraceabilityPredicate getOutputBrickFluidHatch(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickFluidHatch mte : BRICK_EXPORT_FLUID_HATCH) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_EXPORT_FLUID_HATCH[0]);
    }

    public static TraceabilityPredicate getOutputBrickItemBus(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickItemBus mte : BRICK_EXPORT_ITEM_BUS) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_EXPORT_ITEM_BUS[0]);
    }

    public static TraceabilityPredicate getInputBrickItemBus(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickItemBus mte : BRICK_IMPORT_ITEM_BUS) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_IMPORT_ITEM_BUS[0]);
    }

    public static TraceabilityPredicate getBrickMuffler(BlockBrick.BrickType brick) {
        for (MetaTileEntityPrimitiveMufflerHatch mte : PRIMITIVE_MUFFLER) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(PRIMITIVE_MUFFLER[0]);
    }

    public static TraceabilityPredicate getHeatAcceptor() {
        return metaTileEntities(HEAT_ACCEPTOR[0]);
    }
}
