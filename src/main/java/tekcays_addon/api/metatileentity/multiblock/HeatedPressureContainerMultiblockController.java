package tekcays_addon.api.metatileentity.multiblock;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.containers.IHeatContainer;
import tekcays_addon.api.capability.containers.IPressureContainer;
import tekcays_addon.api.capability.containers.IPressureControl;
import tekcays_addon.api.capability.list.HeatContainerList;
import tekcays_addon.api.capability.machines.IHeatMachine;
import tekcays_addon.api.capability.machines.IPressureControlMachine;
import tekcays_addon.api.capability.machines.IPressureMachine;
import tekcays_addon.api.utils.TKCYALog;

import java.util.List;

import static tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility.*;

@Getter
public abstract class HeatedPressureContainerMultiblockController extends RecipeMapMultiblockController implements IPressureMachine, IHeatMachine, IPressureControlMachine {

    protected IPressureContainer pressureContainer;
    protected IHeatContainer heatContainer;
    protected IPressureControl pressureControl;

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

        List<IPressureControl> list2 = getAbilities(PRESSURE_CONTROL);
        this.pressureControl = list2.isEmpty() ? null : list2.get(0);
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
                .or(abilities(PRESSURE_CONTROL)).setExactLimit(1);
        return predicate;
    }


}
