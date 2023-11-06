package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.gtapi.capability.machines.IRotationMachine;
import tekcays_addon.gtapi.logic.ModulableLogic;

import java.util.List;

import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.ROTATION_CONTAINER;

public abstract class RotationNoEnergyMultiblockController extends RecipeMapMultiblockController implements IRotationMachine {

    private IRotationContainer rotationContainer;

    public RotationNoEnergyMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new ModulableLogic(this, LogicType.NO_ENERGY);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();

        List<IRotationContainer> list = getAbilities(ROTATION_CONTAINER);
        this.rotationContainer = list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(false, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate.or(abilities(ROTATION_CONTAINER).setExactLimit(1));
        return predicate;
    }

    @Override
    public IRotationContainer getRotationContainer() {
        return this.rotationContainer;
    }

}
