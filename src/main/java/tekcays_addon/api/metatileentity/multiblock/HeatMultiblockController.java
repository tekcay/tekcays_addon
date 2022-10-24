package tekcays_addon.api.metatileentity.multiblock;

import com.google.common.collect.Lists;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.capability.impl.HeatContainerList;
import tekcays_addon.api.capability.impl.HeatMultiblockRecipeLogic;


public abstract class HeatMultiblockController extends RecipeMapMultiblockController implements IHeatMachine {

    private IHeatContainer heatContainer;

    public HeatMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new HeatMultiblockRecipeLogic(this);
        resetTileAbilities();
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.heatContainer = new HeatContainerList(getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER));
    }


    private void resetTileAbilities() {
        this.heatContainer = new HeatContainerList(Lists.newArrayList());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate.or(abilities(TKCYAMultiblockAbility.HEAT_CONTAINER));
        return predicate;
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.heatContainer;
    }
}
