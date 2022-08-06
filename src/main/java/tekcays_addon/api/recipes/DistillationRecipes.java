package tekcays_addon.api.recipes;

import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.common.blocks.blocks.BlockPump;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.common.blocks.blocks.BlockPump.PumpType.*;


public class DistillationRecipes { //TODO add GTCEu recipes in there

    public static final List<DistillationRecipes> TKCYA_DISTILLATION_RECIPES = new ArrayList<>();
    public static final DistillationRecipes D_WOOD_VINEGAR = new DistillationRecipes(WoodVinegar.getLocalizedName(), WoodVinegar.getFluid(1000),
            new FluidStack[] {
            AceticAcid.getFluid(100), Water.getFluid(500), Ethanol.getFluid(10),
            Methanol.getFluid(300), MethylAcetate.getFluid(10), Acetone.getFluid(50)});

    public static final DistillationRecipes D_OIL = new DistillationRecipes(Oil.getLocalizedName(), Oil.getFluid(50),
            new FluidStack[] {
                    SulfuricGas.getFluid(60), SulfuricNaphtha.getFluid(20), SulfuricLightFuel.getFluid(50),
                    SulfuricHeavyFuel.getFluid(15)}, PUMP_MACHINE_HV);

    public static final DistillationRecipes D_OIL_LIGHT = new DistillationRecipes(Oil.getLocalizedName(), OilLight.getFluid(150),
            new FluidStack[] {
                    SulfuricGas.getFluid(240), SulfuricNaphtha.getFluid(30), SulfuricLightFuel.getFluid(20),
                    SulfuricHeavyFuel.getFluid(10)}, PUMP_MACHINE_HV);

    public static final DistillationRecipes D_OIL_HEAVY = new DistillationRecipes(Oil.getLocalizedName(), OilHeavy.getFluid(100),
            new FluidStack[] {
                    SulfuricGas.getFluid(60), SulfuricNaphtha.getFluid(15), SulfuricLightFuel.getFluid(45),
                    SulfuricHeavyFuel.getFluid(250)}, PUMP_MACHINE_HV);

    public static final DistillationRecipes D_RAW_OIL = new DistillationRecipes(Oil.getLocalizedName(), RawOil.getFluid(100),
            new FluidStack[] {
                    SulfuricGas.getFluid(60), SulfuricNaphtha.getFluid(20), SulfuricLightFuel.getFluid(50),
                    SulfuricHeavyFuel.getFluid(15)}, PUMP_MACHINE_HV);



    private final String fluidStackInputLocalizedName;
    private final FluidStack fluidStackInput;
    private FluidStack[] fluidStackOutput;
    private BlockPump.PumpType pumpType;
    private Map<Integer, FluidStack> output;
    public DistillationRecipes(String fluidStackInputLocalizedName, FluidStack fluidStackInput, FluidStack[] fluidStackOutput) {
        this.fluidStackInputLocalizedName = fluidStackInputLocalizedName;
        this.fluidStackInput = fluidStackInput;
        this.fluidStackOutput = fluidStackOutput;
    }

    public DistillationRecipes(String fluidStackInputLocalizedName, FluidStack fluidStackInput, FluidStack[] fluidStackOutput, BlockPump.PumpType pumpType) {
        this.fluidStackInputLocalizedName = fluidStackInputLocalizedName;
        this.fluidStackInput = fluidStackInput;
        this.fluidStackOutput = fluidStackOutput;
        this.pumpType = pumpType;
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

    public BlockPump.PumpType getPumpType() {
        return this.pumpType;
    }

    public Map<Integer, FluidStack> getOutput() {
        return this.output;
    }
    


}
