package tekcays_addon.api.metatileentity.mutiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.capability.impl.AtmosphericHeatContainer;
import tekcays_addon.api.capability.impl.HeatMultiblockRecipeLogic;

import java.util.List;

public abstract class HeatMultiblockController extends RecipeMapMultiblockController implements IHeatMachine {

    private IHeatContainer container;

    public HeatMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new HeatMultiblockRecipeLogic(this);
        
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IHeatContainer> list = getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER);
        if (list.isEmpty()) {
            this.container = new AtmosphericHeatContainer(this);
        } else {
            this.container = list.get(0);
        }
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate.or(abilities(TKCYAMultiblockAbility.HEAT_CONTAINER).setMaxGlobalLimited(1).setPreviewCount(1));
        return predicate;
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.container;
    }
}
