package tekcays_addon.api.utils;

import gregtech.api.unification.material.Material;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;

public class FuelHeater {

    private final float efficiency;
    private final int powerMultiplier;
    private final Material material;

    public static FuelHeater BRICK = new FuelHeater(Brick, 0.50F, 1);
    public static FuelHeater INVAR = new FuelHeater(Invar, 1.00F, 2);
    public static FuelHeater BRONZE = new FuelHeater(Bronze, 0.80F, 3);
    public static FuelHeater STEEL = new FuelHeater(Steel, 0.80F, 4);
    public static FuelHeater TITANIUM = new FuelHeater(Titanium, 0.80F, 8);
    public static FuelHeater TUNGSTEN_STEEL = new FuelHeater(TungstenSteel, 0.80F, 16);

    /**
     * Contains all the {@code FuelHeater}s registered.
     */
    public static List<FuelHeater> FUEL_HEATERS = new ArrayList<FuelHeater>() {{
       add(BRICK);
       add(INVAR);
       add(BRONZE);
       add(STEEL);
       add(TITANIUM);
       add(TUNGSTEN_STEEL);
    }};

    public FuelHeater(Material material, float efficiency, int powerMultiplier) {
        this.material = material;
        this.efficiency = efficiency;
        this.powerMultiplier = powerMultiplier;
    }

    public Material getMaterial() {
        return material;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public int getPowerMultiplier() {
        return powerMultiplier;
    }

}
