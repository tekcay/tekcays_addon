package tekcays_addon.api.utils;

import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;

public class FluidWithProperties { //TODO add more Fluids

    public static final List<FluidWithProperties> FLUID_WITH_PROPERTIES = new ArrayList<>();

    public static final FluidWithProperties ACETIC_ACID = new FluidWithProperties(AceticAcid.getLocalizedName(), AceticAcid.getFluid(), 391, 290);
    public static final FluidWithProperties ACETONE = new FluidWithProperties(Acetone.getLocalizedName(), Acetone.getFluid(), 329, 195);
    public static final FluidWithProperties ETHANOL = new FluidWithProperties(Ethanol.getLocalizedName(), Ethanol.getFluid(), 351, 159);
    public static final FluidWithProperties METHANOL = new FluidWithProperties(Methanol.getLocalizedName(), Methanol.getFluid(), 334, 175);
    public static final FluidWithProperties METHYL_ACETATE = new FluidWithProperties(MethylAcetate.getLocalizedName(), MethylAcetate.getFluid(), 330, 175);
    public static final FluidWithProperties WATER = new FluidWithProperties(Water.getLocalizedName(), Water.getFluid(), 373, 273);
    public static final FluidWithProperties OIL = new FluidWithProperties(Oil.getLocalizedName(), Oil.getFluid(), 800);
    public static final FluidWithProperties OIL_LIGHT = new FluidWithProperties(OilLight.getLocalizedName(), OilLight.getFluid(), 800);
    public static final FluidWithProperties OIL_HEAVY = new FluidWithProperties(OilHeavy.getLocalizedName(), OilHeavy.getFluid(), 800);
    public static final FluidWithProperties RAW_OIL = new FluidWithProperties(RawOil.getLocalizedName(), RawOil.getFluid(), 800);
    public static final FluidWithProperties SULFURIC_GAS = new FluidWithProperties(SulfuricGas.getLocalizedName(), SulfuricGas.getFluid(), 313);
    public static final FluidWithProperties SULFURIC_NAPHTHA = new FluidWithProperties(SulfuricNaphtha.getLocalizedName(), SulfuricNaphtha.getFluid(), 600);
    public static final FluidWithProperties SULFURIC_LIGHT_FUEL = new FluidWithProperties(SulfuricLightFuel.getLocalizedName(), SulfuricLightFuel.getFluid(), 500);
    public static final FluidWithProperties SULFURIC_HEAVY_FUEL = new FluidWithProperties(SulfuricHeavyFuel.getLocalizedName(), SulfuricHeavyFuel.getFluid(), 800);




    private final String name;
    private final Fluid fluid;
    private final int boilingPoint;
    private int meltingPoint;
    public FluidWithProperties(String name, Fluid fluid, int boilingPoint, int meltingPoint) {
        this.name = name;
        this.fluid = fluid;
        this.boilingPoint = boilingPoint;
        this.meltingPoint = meltingPoint;
    }

    public FluidWithProperties(String name, Fluid fluid, int boilingPoint) {
        this.name = name;
        this.fluid = fluid;
        this.boilingPoint = boilingPoint;
    }

    public Fluid getFluid() {
        return this.fluid;
    }

    public String getName() {
        return this.name;
    }

    public int getBoilingPoint() {
        return this.boilingPoint;
    }

    public int getMeltingPoint() {
        return this.meltingPoint;
    }
}
