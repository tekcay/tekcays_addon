package tekcays_addon.api.utils;

import gregtech.api.unification.material.Material;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;

public class FuelHeater {

    private final int efficiency;
    private final int powerMultiplier;
    private final Material material;

    public static FuelHeater BRICK = new FuelHeater(Brick, 50, 1);
    public static FuelHeater INVAR = new FuelHeater(Invar, 100, 2);
    public static FuelHeater BRONZE = new FuelHeater(Bronze, 80, 3);
    public static FuelHeater STEEL = new FuelHeater(Steel, 80, 4);
    public static FuelHeater TITANIUM = new FuelHeater(Titanium, 80, 8);
    public static FuelHeater TUNGSTEN_STEEL = new FuelHeater(TungstenSteel, 80, 16);

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

    public FuelHeater(Material material, int efficiency, int powerMultiplier) {
        this.material = material;
        this.efficiency = efficiency;
        this.powerMultiplier = powerMultiplier;
    }

    public Material getMaterial() {
        return material;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public int getPowerMultiplier() {
        return powerMultiplier;
    }

}
