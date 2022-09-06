package tekcays_addon.api.utils.roasting;

import gregtech.api.unification.material.Material;

import static gregicality.science.api.GCYSValues.EARTH_PRESSURE;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class RoastableMaterial {


    public static final RoastableMaterial TETRAHEDRITE = new RoastableMaterial(Tetrahedrite, 800, EARTH_PRESSURE * 2);
    public static final RoastableMaterial KESTERITE = new RoastableMaterial(Kesterite, 900, EARTH_PRESSURE * 3);
    public static final RoastableMaterial STANNITE = new RoastableMaterial(Stannite, 1000, EARTH_PRESSURE * 4);
    public static final RoastableMaterial COBALTITE = new RoastableMaterial(Cobaltite, 1000, EARTH_PRESSURE * 3);
    public static final RoastableMaterial CHALCOPYRITE = new RoastableMaterial(Chalcopyrite, 1000, EARTH_PRESSURE * 4);
    public static final RoastableMaterial ARSENOPYRITE = new RoastableMaterial(Arsenopyrite, 1000, EARTH_PRESSURE * 2);
    public static final RoastableMaterial MOLYBDNITE = new RoastableMaterial(Molybdenite, 1000, EARTH_PRESSURE * 2);
    public static final RoastableMaterial PENTLANDITE = new RoastableMaterial(Pentlandite, 1000, EARTH_PRESSURE * 3);
    public static final RoastableMaterial STIBNITE = new RoastableMaterial(Stibnite, 1000, EARTH_PRESSURE * 3);
    public static final RoastableMaterial PYRITE = new RoastableMaterial(Pyrite, 1000, EARTH_PRESSURE * 2);

    private final Material material;
    private final int temperature;
    private final double pressure;

    public RoastableMaterial(Material material, int temperature, double pressure) {
        this.material = material;
        this.temperature = temperature;
        this.pressure = pressure;

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
}
