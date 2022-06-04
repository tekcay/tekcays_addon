package tekcays_addon.api.capability.impl;

import tekcays_addon.api.capability.IParallelMultiblock;
import tekcays_addon.api.metatileentity.TKCYAMultiblockAbility;
import tekcays_addon.api.metatileentity.TKCYARecipeMapMultiblockController;
import tekcays_addon.common.TKCYAConfigHolder;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;

import java.util.List;

public class TKCYAMultiblockRecipeLogic extends MultiblockRecipeLogic {

    public TKCYAMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    @Override
    public int getParallelLimit() {
        if (metaTileEntity instanceof IParallelMultiblock && ((IParallelMultiblock) metaTileEntity).isParallel())
            return ((IParallelMultiblock) metaTileEntity).getMaxParallel();

        return 1;
    }

    @Override
    public RecipeMapMultiblockController getMetaTileEntity() {
        return (RecipeMapMultiblockController) super.getMetaTileEntity();
    }

    @Override
    protected long getMaxVoltage() {
        if (!TKCYAConfigHolder.globalMultiblocks.enableTieredCasings)
            return super.getMaxVoltage();

        if (getMetaTileEntity() instanceof TKCYARecipeMapMultiblockController && !((TKCYARecipeMapMultiblockController) getMetaTileEntity()).isTiered())
            return super.getMaxVoltage();

        List<ITieredMetaTileEntity> list = getMetaTileEntity().getAbilities(TKCYAMultiblockAbility.TIERED_HATCH);

        if (list.isEmpty())
            return super.getMaxVoltage();

        return Math.min(GTValues.V[list.get(0).getTier()], super.getMaxVoltage());
    }
}
