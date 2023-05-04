package tekcays_addon.api.metatileentity.predicates;

import gregtech.api.pattern.TraceabilityPredicate;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityBrickCastingBus;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityBrickCastingFluidInput;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.metaTileEntities;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;

public class BrickCastingBusPredicates {

    public static TraceabilityPredicate getBrickCastingBus(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickCastingBus mte : BRICK_CASTING_BUS) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_CASTING_BUS[0]);
    }

    public static TraceabilityPredicate getBrickCastingFluidInput(BlockBrick.BrickType brick) {
        for (MetaTileEntityBrickCastingFluidInput mte : BRICK_CASTING_FLUID_INPUT) {
            if (mte.getBrick().equals(brick)) return metaTileEntities(mte);
        }
        return metaTileEntities(BRICK_CASTING_FLUID_INPUT[0]);
    }
}
