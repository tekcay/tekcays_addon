package tekcays_addon.api.unification.material.properties;

import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import net.minecraftforge.fluids.Fluid;

import static gregtech.api.unification.material.Materials.*;

public class TKCYAPropertyAddition {

    public static void init() {

        Barium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Barium.getProperty(PropertyKey.FLUID).setFluidTemperature(1000);

        Boron.setProperty(PropertyKey.FLUID, new FluidProperty());
        Boron.getProperty(PropertyKey.FLUID).setFluidTemperature(2347);

        Calcium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Calcium.getProperty(PropertyKey.FLUID).setFluidTemperature(1115);

        Phosphorus.setProperty(PropertyKey.FLUID, new FluidProperty());
        Phosphorus.getProperty(PropertyKey.FLUID).setFluidTemperature(860);

        //For electrodes

        Carbon.setProperty(PropertyKey.INGOT, new IngotProperty());

        FerriteMixture.setProperty(PropertyKey.FLUID, new FluidProperty());
        FerriteMixture.getProperty(PropertyKey.FLUID).setFluidTemperature(1400);

        TitaniumTrifluoride.setProperty(PropertyKey.FLUID, new FluidProperty());
        TitaniumTrifluoride.getProperty(PropertyKey.FLUID).setFluidTemperature(1470);

    }
}
