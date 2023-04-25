package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.machines.IPressureMachine;
import tekcays_addon.gtapi.capability.impl.PressureContainer;
import tekcays_addon.gtapi.logic.PressureContainerMultiblockRecipeLogic;

import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_PRESSURE;


public abstract class PressureContainerMultiblockController extends RecipeMapMultiblockController implements IPressureMachine {

    private IPressureContainer pressureContainer;

    public PressureContainerMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new PressureContainerMultiblockRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IPressureContainer> list = getAbilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER);
        if (list.isEmpty()) {
            this.pressureContainer = new PressureContainer(this, 0, MAX_PRESSURE);
        } else {
            this.pressureContainer = list.get(0);
        }
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate.or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER).setMinGlobalLimited(1).setMaxGlobalLimited(1).setPreviewCount(1));
        return predicate;
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.pressureContainer;
    }
}
