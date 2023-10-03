package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.impl.PressureContainer;
import tekcays_addon.gtapi.capability.machines.IPressureMachine;
import tekcays_addon.gtapi.logic.MultiAmperageLogic;
import tekcays_addon.gtapi.logic.PressureContainerMultiblockRecipeLogic;

import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_PRESSURE;


public abstract class MultiAmperageMultiblockController extends RecipeMapMultiblockController {

    public MultiAmperageMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new MultiAmperageLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }
}
