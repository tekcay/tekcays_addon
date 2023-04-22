package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;
import tekcays_addon.gtapi.capability.list.HeatContainerList;
import tekcays_addon.gtapi.logic.HeatContainerNoEnergyMultiblockRecipeLogic;

public abstract class HeatContainerNoEnergyMultiblockController extends RecipeMapMultiblockController implements IHeatMachine {

    private IHeatContainer heatContainer;

    public HeatContainerNoEnergyMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new HeatContainerNoEnergyMultiblockRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.heatContainer = new HeatContainerList(getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(false, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        return predicate;
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.heatContainer;
    }

}
