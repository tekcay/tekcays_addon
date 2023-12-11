package tekcays_addon.gtapi.unification.material.properties;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;

import java.util.function.Function;

import static gregtech.api.unification.material.Materials.*;

public class TKCYAPropertyAddition {

    public static void addFluid(Material m, Function<FluidBuilder, FluidBuilder> buildFluid) {
        if (!m.hasProperty(PropertyKey.FLUID)) {
            FluidProperty property = new FluidProperty();
            property.getStorage().enqueueRegistration(FluidStorageKeys.LIQUID, buildFluid.apply(new FluidBuilder()));
            m.setProperty(PropertyKey.FLUID, property);
        }
    }

    public static void init() {

        addFluid(Barium, fluidBuilder -> fluidBuilder.temperature(1000));
        addFluid(Boron, fluidBuilder -> fluidBuilder.temperature(2347));
        addFluid(Calcium, fluidBuilder -> fluidBuilder.temperature(1115));
        addFluid(Phosphorus, fluidBuilder -> fluidBuilder.temperature(860));
        addFluid(FerriteMixture, fluidBuilder -> fluidBuilder.temperature(1400));
        addFluid(TitaniumTrifluoride, fluidBuilder -> fluidBuilder.temperature(1470));
        addFluid(Calcite, fluidBuilder -> fluidBuilder.temperature(1612));

        Carbon.setProperty(PropertyKey.INGOT, new IngotProperty());

    }
}
