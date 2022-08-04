package tekcays_addon.api.recipes;

import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.utils.FluidWithProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.utils.FluidWithProperties.*;


public class DistillationRecipes {

    public static final List<DistillationRecipes> TKCYA_DISTILLATION_RECIPES = new ArrayList<>();
    public static final DistillationRecipes WOOD_VINEGAR = new DistillationRecipes("Wood Vinegar", WoodVinegar.getFluid(1000),
            new FluidStack[] {
            AceticAcid.getFluid(100), Water.getFluid(500), Ethanol.getFluid(10),
            Methanol.getFluid(300), MethylAcetate.getFluid(10)});

    public static final DistillationRecipes WOOD_VINEGAR2 = new DistillationRecipes("Wood Vinegar", WoodVinegar.getFluid(1000),
            new TreeMap<>() {
                FluidWithProperties.ACETIC_ACID.getBoilingPoint(), AceticAcid.getFluid(100) });







    public String fluidStackInputName;
    public FluidStack fluidStackInput;
    public FluidStack[] fluidStackOutput;
    public Map<FluidStack, Integer> output;
    public DistillationRecipes(String fluidStackInputName, FluidStack fluidStackInput, FluidStack[] fluidStackOutput) {
        this.fluidStackInputName = fluidStackInputName;
        this.fluidStackInput = fluidStackInput;
        this.fluidStackOutput = fluidStackOutput;
    }

    public DistillationRecipes(String fluidStackInputName, FluidStack fluidStackInput, Map<Integer, FluidStack> output) {
        this.fluidStackInputName = fluidStackInputName;
        this.fluidStackInput = fluidStackInput;
        this.output = output;
    }


}
