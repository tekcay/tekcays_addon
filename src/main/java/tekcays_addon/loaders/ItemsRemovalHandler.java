package tekcays_addon.loaders;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityCrackingUnit;

import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.CRACKER;

public class ItemsRemovalHandler {

    public static void molds() {

        SHAPE_MOLD_PLATE.setInvisible();
        SHAPE_MOLD_GEAR.setInvisible();
        SHAPE_MOLD_CREDIT.setInvisible();
        SHAPE_MOLD_BOTTLE.setInvisible();
        SHAPE_MOLD_INGOT.setInvisible();
        SHAPE_MOLD_BALL.setInvisible();
        SHAPE_MOLD_BLOCK.setInvisible();
        SHAPE_MOLD_NUGGET.setInvisible();
        SHAPE_MOLD_CYLINDER.setInvisible();
        SHAPE_MOLD_ANVIL.setInvisible();
        SHAPE_MOLD_NAME.setInvisible();
        SHAPE_MOLD_GEAR_SMALL.setInvisible();
        SHAPE_MOLD_ROTOR.setInvisible();

    }


}
