package tekcays_addon.api.utils.roasting;

import gregtech.api.unification.material.Material;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public class RoastableMaterial {


    public static final RoastableMaterial TETRAHEDRITE = new RoastableMaterial(Tetrahedrite, 800, ATMOSPHERIC_PRESSURE * 2);
    public static final RoastableMaterial KESTERITE = new RoastableMaterial(Kesterite, 900, ATMOSPHERIC_PRESSURE * 3);
    public static final RoastableMaterial STANNITE = new RoastableMaterial(Stannite, 1000, ATMOSPHERIC_PRESSURE * 4);
    public static final RoastableMaterial COBALTITE = new RoastableMaterial(Cobaltite, 1000, ATMOSPHERIC_PRESSURE * 3);
    public static final RoastableMaterial CHALCOPYRITE = new RoastableMaterial(Chalcopyrite, 1000, ATMOSPHERIC_PRESSURE * 4);
    public static final RoastableMaterial ARSENOPYRITE = new RoastableMaterial(Arsenopyrite, 1000, ATMOSPHERIC_PRESSURE * 2);

    public static final RoastableMaterial PENTLANDITE = new RoastableMaterial(Pentlandite, 1000, ATMOSPHERIC_PRESSURE * 3);
    public static final RoastableMaterial STIBNITE = new RoastableMaterial(Stibnite, 1000, ATMOSPHERIC_PRESSURE * 3);
    public static final RoastableMaterial PYRITE = new RoastableMaterial(Pyrite, 1000, ATMOSPHERIC_PRESSURE * 2);
    public static final RoastableMaterial REALGAR = new RoastableMaterial(Realgar, 1000, ATMOSPHERIC_PRESSURE * 2);
    public static final RoastableMaterial BORNITE = new RoastableMaterial(Bornite, 1000, ATMOSPHERIC_PRESSURE * 2);

    //With special output
    public static final RoastableMaterial CHALCOCITE = new RoastableMaterial(Chalcocite, 1000, ATMOSPHERIC_PRESSURE * 2, Cuprite);
    public static final RoastableMaterial MOLYBDENITE = new RoastableMaterial(Molybdenite, 1000, ATMOSPHERIC_PRESSURE * 2, MolybdenumTrioxide, MolybdenumFlue);


    private final Material material;
    private final int temperature;
    private final double pressure;
    private Material output;
    private Material fluidOutput;

    public RoastableMaterial(Material material, int temperature, double pressure) {
        this.material = material;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    public RoastableMaterial(Material material, int temperature, double pressure, Material output) {
        this.material = material;
        this.temperature = temperature;
        this.pressure = pressure;
        this.output = output;
    }

    public RoastableMaterial(Material material, int temperature, double pressure, Material output, Material fluidOutput) {
        this.material = material;
        this.temperature = temperature;
        this.pressure = pressure;
        this.output = output;
        this.fluidOutput = fluidOutput;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getRoastingTemperature() {
        return this.temperature;
    }

    public double getRoastingPressure() {
        return this.pressure;
    }
    public Material getOutput() {
        return this.output;
    }

    public Material getFluidOutput() {
        return this.fluidOutput;
    }
}
