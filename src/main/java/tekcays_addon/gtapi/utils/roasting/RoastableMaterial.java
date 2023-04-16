package tekcays_addon.gtapi.utils.roasting;

import gregtech.api.unification.material.Material;
import lombok.Getter;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;
import static tekcays_addon.gtapi.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

@Getter
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
    private final int roastingTemperature;
    private final int roastingPressure;
    private Material output;
    private Material fluidOutput;

    public RoastableMaterial(Material material, int roastingTemperature, int roastingPressure) {
        this.material = material;
        this.roastingTemperature = roastingTemperature;
        this.roastingPressure = roastingPressure;
    }

    public RoastableMaterial(Material material, int roastingTemperature, int roastingPressure, Material output) {
        this.material = material;
        this.roastingTemperature = roastingTemperature;
        this.roastingPressure = roastingPressure;
        this.output = output;
    }

    public RoastableMaterial(Material material, int roastingTemperature, int roastingPressure, Material output, Material fluidOutput) {
        this.material = material;
        this.roastingTemperature = roastingTemperature;
        this.roastingPressure = roastingPressure;
        this.output = output;
        this.fluidOutput = fluidOutput;
    }
}
