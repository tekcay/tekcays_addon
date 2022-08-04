package tekcays_addon.api.recipes;

import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gregtech.api.unification.material.Materials.*;


public class DistillationRecipes {

    public static final List<DistillationRecipes> TKCYA_DISTILLATION_RECIPES = new ArrayList<>();
    public static final DistillationRecipes WOOD_VINEGAR = new DistillationRecipes(WoodVinegar.getLocalizedName(), WoodVinegar.getFluid(1000),
            new FluidStack[] {
            AceticAcid.getFluid(100), Water.getFluid(500), Ethanol.getFluid(10),
            Methanol.getFluid(300), MethylAcetate.getFluid(10), Acetone.getFluid(50)});


    private final String fluidStackInputLocalizedName;
    private final FluidStack fluidStackInput;
    private FluidStack[] fluidStackOutput;
    private Map<Integer, FluidStack> output;
    public DistillationRecipes(String fluidStackInputLocalizedName, FluidStack fluidStackInput, FluidStack[] fluidStackOutput) {
        this.fluidStackInputLocalizedName = fluidStackInputLocalizedName;
        this.fluidStackInput = fluidStackInput;
        this.fluidStackOutput = fluidStackOutput;
    }

    public DistillationRecipes(String fluidStackInputLocalizedName, FluidStack fluidStackInput, Map<Integer, FluidStack> output) {
        this.fluidStackInputLocalizedName = fluidStackInputLocalizedName;
        this.fluidStackInput = fluidStackInput;
        this.output = output;
    }
    
    public String getFluidStackInputName() {
        return this.fluidStackInputLocalizedName;
    }

    public FluidStack getFluidStackInput() {
        return this.fluidStackInput;
    }

    public FluidStack[] getFluidStackOutput() {
        return this.fluidStackOutput;
    }

    public Map<Integer, FluidStack> getOutput() {
        return this.output;
    }
    


}
