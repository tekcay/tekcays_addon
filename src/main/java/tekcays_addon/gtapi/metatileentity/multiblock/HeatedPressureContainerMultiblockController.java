package tekcays_addon.gtapi.metatileentity.multiblock;


import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipe.PressureContainerCheckRecipeHelper;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.list.HeatContainerList;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;
import tekcays_addon.gtapi.capability.machines.IContainerDetectorMachine;
import tekcays_addon.gtapi.capability.machines.IPressureMachine;

import javax.annotation.Nonnull;
import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.*;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.*;

public abstract class HeatedPressureContainerMultiblockController extends RecipeMapMultiblockController implements PressureContainerCheckRecipeHelper, IPressureMachine, IHeatMachine, IContainerDetectorMachine {

    @Getter
    protected IPressureContainer pressureContainer;
    @Getter
    protected IHeatContainer heatContainer;
    @Getter
    protected IContainerDetector containerDetector;
    @Getter
    protected int currentTemp;
    @Getter
    protected int currentPressure;
    protected int currentHeat;
    protected int volume;


    public HeatedPressureContainerMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new MultiblockRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.heatContainer = new HeatContainerList(getAbilities(HEAT_CONTAINER));

        List<IPressureContainer> list = getAbilities(PRESSURE_CONTAINER);
        this.pressureContainer = list.isEmpty() ? null : list.get(0);

        List<IContainerDetector> list2 = getAbilities(CONTAINER_CONTROL);
        this.containerDetector = list2.isEmpty() ? null : list2.get(0);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        this.currentTemp = ROOM_TEMPERATURE;
        this.currentPressure = ATMOSPHERIC_PRESSURE;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate
                .or(abilities(PRESSURE_CONTAINER)).setExactLimit(1)
                .or(abilities(CONTAINER_CONTROL)).setExactLimit(1);
        return predicate;
    }

    protected void updateLogic() {

        this.pressureContainer = getPressureContainer();
        this.containerDetector = getContainerDetector();
        this.heatContainer = getHeatContainer();

        if (getOffsetTimer() % 20 == 0) {
            if (containerDetector != null) containerDetector.setCurrentValue(currentPressure);
        }

        if (pressureContainer == null) return;
        currentPressure = pressureContainer.getPressure();

        if (heatContainer == null) return;
        currentHeat = heatContainer.getHeat();
        actualizeTemperature();
        currentTemp = heatContainer.getTemperature();

        if (recipeMapWorkable.isWorking()) {
            pressureContainer.changePressurizedFluidStack(getPressurizedFluidStack(), - getPressurizedFluidStackRate());
        }
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return checkRecipeHelper(recipe, consumeIfSuccess);
    }

    private FluidStack getPressurizedFluidStack() {
        return this.pressureContainer.getPressurizedFluidStack();
    }

    //TODO find a formula
    private int getPressurizedFluidStackRate() {
        return getPressurizedFluidStack().amount * currentPressure;
    }

    private void actualizeTemperature() {
        heatContainer.setTemperature(ROOM_TEMPERATURE + currentHeat / (20));
    }

    @Override
    public FluidStack getFluidStack() {
        return pressureContainer.getPressurizedFluidStack();
    }
}