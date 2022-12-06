package tekcays_addon.api.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.IPressureMachine;
import tekcays_addon.api.capability.impl.PressureContainerList;
import tekcays_addon.api.capability.impl.PressureContainerNoEnergyMultiblockRecipeLogic;

public abstract class PressureContainerNoEnergyMultiblockController extends RecipeMapMultiblockController implements IPressureMachine {

    private IPressureContainer pressureContainer;

    public PressureContainerNoEnergyMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new PressureContainerNoEnergyMultiblockRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.pressureContainer = new PressureContainerList(getAbilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(false, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate.or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER));
        return predicate;
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.pressureContainer;
    }

}
