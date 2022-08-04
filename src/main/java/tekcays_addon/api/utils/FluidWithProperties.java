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
