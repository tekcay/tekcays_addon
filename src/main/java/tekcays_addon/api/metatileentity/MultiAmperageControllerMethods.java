package tekcays_addon.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.recipes.Recipe;
import tekcays_addon.gtapi.metatileentity.multiblock.ModulableRecipeMapController;
import tekcays_addon.gtapi.recipes.recipeproperties.MultiAmperageProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.VoltageProperty;

import javax.annotation.Nonnull;
import java.util.List;

public class MultiAmperageControllerMethods {

    public static boolean areAllEnergyHatchesTheSameVoltage(List<IEnergyContainer> energyContainers) {
        return energyContainers
                .stream()
                .map(IEnergyContainer::getInputVoltage)
                .distinct()
                .count() == 1;
    }

    public static long getMaxInputAmperage(List<IEnergyContainer> energyContainers) {
        return energyContainers.stream()
                .mapToLong(IEnergyContainer::getInputAmperage)
                .sum();
    }

    public static boolean isVoltageTierValidForRecipe(Recipe recipe, boolean areAllEnergyHatchesTheSameVoltage, List<IEnergyContainer> energyContainers) {
        return areAllEnergyHatchesTheSameVoltage && energyContainers.get(0).getInputVoltage() == getRecipeVoltage(recipe);
    }

    public static int getRecipeAmperage(Recipe recipe) {
        return recipe.getProperty(MultiAmperageProperty.getInstance(), 0);
    }

    public static long getRecipeVoltage(Recipe recipe) {
        return GTValues.V[recipe.getProperty(VoltageProperty.getInstance(), 0)];
    }

    public static boolean multiAmperageRecipeChecker(@Nonnull Recipe recipe, List<IEnergyContainer> energyContainers) {
        return getMaxInputAmperage(energyContainers) >= getRecipeAmperage(recipe) &&
                isVoltageTierValidForRecipe(recipe, areAllEnergyHatchesTheSameVoltage(energyContainers), energyContainers);
    }

    public static boolean multiAmperageRecipeChecker(@Nonnull Recipe recipe, List<IEnergyContainer> energyContainers, boolean areAllEnergyHatchesTheSameVoltage) {
        return getMaxInputAmperage(energyContainers) >= getRecipeAmperage(recipe) && isVoltageTierValidForRecipe(recipe, areAllEnergyHatchesTheSameVoltage, energyContainers);
    }
}
