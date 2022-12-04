package tekcays_addon.api.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.capability.impl.HeatContainerMultiblockRecipeLogic;

import java.util.List;


public abstract class HeatContainerMultiblockController extends RecipeMapMultiblockController implements IHeatMachine {

    private IHeatContainer heatContainer;

    public HeatContainerMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new HeatContainerMultiblockRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IHeatContainer> list = getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER);
        if (list.isEmpty()) {
            this.heatContainer = new HeatContainer(this, 0, 2000000);
        } else {
            this.heatContainer = list.get(0);
        }
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate.or(abilities(TKCYAMultiblockAbility.HEAT_CONTAINER).setMinGlobalLimited(1).setMaxGlobalLimited(1).setPreviewCount(1));
        return predicate;
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.heatContainer;
    }
}
