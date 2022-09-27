package tekcays_addon.api.utils;

import gregtech.api.unification.material.Material;
import net.minecraftforge.fluids.FluidStack;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.Creosote;

public class FuelWithProperties {

    private Material material;
    private final int burnTime;
    private FluidStack fluidStack;

    public FuelWithProperties(FluidStack fluidStack, Material material, int burnTime) {
        this.fluidStack = fluidStack;
        this.material = material;
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

    public static final FuelWithProperties CREOSOTE = new FuelWithProperties(new FluidStack(Creosote.getFluid(), 100), Creosote, 100);

    public static final List<FuelWithProperties> LIQUID_FUELS_BURNING = new ArrayList<FuelWithProperties>() {{
       add(CREOSOTE);
    }};

}
