package tekcays_addon.api.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.IPressureMachine;
import tekcays_addon.api.capability.impl.*;

import java.util.List;

public abstract class HeatedPressureContainerMultiblockController extends RecipeMapMultiblockController implements IPressureMachine, IHeatMachine {

    private IPressureContainer pressureContainer;
    private IHeatContainer heatContainer;

    public HeatedPressureContainerMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new HeatedPressureContainerMultiblockRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.heatContainer = new HeatContainerList(getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER));
        List<IPressureContainer> list = getAbilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER);
        this.pressureContainer = list.isEmpty() ? null : list.get(0);
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        //predicate = predicate.or(abilities(TKCYAMultiblockAbility.HEAT_CONTAINER));
        //predicate = predicate.or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER)).setMaxGlobalLimited(1);
        return predicate;
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.pressureContainer;
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.heatContainer;
    }
}
