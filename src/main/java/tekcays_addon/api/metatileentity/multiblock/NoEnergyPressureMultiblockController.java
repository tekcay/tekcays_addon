package tekcays_addon.api.metatileentity.multiblock;

import gregicality.science.api.capability.impl.PressureMultiblockRecipeLogic;
import gregicality.science.api.metatileentity.multiblock.GCYSMultiblockAbility;
import gregicality.science.api.metatileentity.multiblock.PressureMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.impl.NoEnergyPressureMultiblockRecipeLogic;

import java.util.List;

public abstract class NoEnergyPressureMultiblockController extends PressureMultiblockController {


    public NoEnergyPressureMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new NoEnergyPressureMultiblockRecipeLogic(this);
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(false, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate.or(abilities(GCYSMultiblockAbility.PRESSURE_CONTAINER).setMaxGlobalLimited(1).setPreviewCount(1));
        return predicate;
    }

}
