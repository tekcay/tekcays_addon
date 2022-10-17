package tekcays_addon.api.utils;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import net.minecraftforge.fluids.FluidStack;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.Calcite;
import static gregtech.api.unification.material.Materials.Creosote;
import static tekcays_addon.api.utils.TKCYAValues.SECOND;

public class FuelWithProperties {

    private Material material;
    private final int burnTime;
    private final FluidStack fluidStack;

    public FuelWithProperties(FluidStack fluidStack, Material material, int burnTime) {
        this.fluidStack = fluidStack;
        this.material = material;
        this.burnTime = burnTime;
    }

    public FuelWithProperties(FluidStack fluidStack, int burnTime) {
        this.fluidStack = fluidStack;
        this.burnTime = burnTime;
    }

    public FluidStack getFluidStack() {
        return this.fluidStack;
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public Material getMaterial() {
        return this.material;
    }

    public static final FuelWithProperties CREOSOTE = new FuelWithProperties(new FluidStack(Creosote.getFluid(), 100), Creosote, 300);
    public static final FuelWithProperties CALCITE = new FuelWithProperties(new FluidStack(Calcite.getFluid(), 8), Calcite, 100 * SECOND);

    public static final List<FuelWithProperties> GAS_FUELS_BURNING = new ArrayList<>();
    public static final List<FuelWithProperties> COMBUSTION_FUELS_BURNING = new ArrayList<>();

    public static void addCombustionRecipeToList() {
        RecipeMaps.COMBUSTION_GENERATOR_FUELS
                .getRecipeList()
                .forEach(recipe -> addToList(COMBUSTION_FUELS_BURNING,
                        recipe.getFluidInputs().get(0).getInputFluidStack(),
                        recipe.getDuration()));
    }

    public static void addGasTurbineRecipeToList() {
        RecipeMaps.GAS_TURBINE_FUELS
                .getRecipeList()
                .forEach(recipe -> addToList(GAS_FUELS_BURNING,
                        recipe.getFluidInputs().get(0).getInputFluidStack(),
                        recipe.getDuration()));
    }

    private static void addToList(List<FuelWithProperties> list, FluidStack fluidStack, int burnTime) {
        list.add(new FuelWithProperties(fluidStack, burnTime));
    }

    /**
     * Retrieve the {@code FuelWithProperties} in LIQUID_FUELS_BURNING (see {@link FuelWithProperties})
     * with a {@code FluidStack}.
     * @param list the list to seek in, i.e. LIQUID_FUELS_BURNING, GAS_FUELS_BURNING or COMBUSTION_FUELS_BURNING
     * @param fluidStack
     * @return {@code null} if not found
     */
    public static FuelWithProperties getFuelWithProperties(List<FuelWithProperties> list, FluidStack fluidStack) {
        return list.stream()
                .filter(fuelWithProperties -> fuelWithProperties.getFluidStack().isFluidEqual(fluidStack))
                .findAny()
                .orElse(null);
    }



}
