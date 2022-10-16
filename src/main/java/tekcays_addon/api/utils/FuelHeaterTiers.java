package tekcays_addon.api.utils;

import gregtech.api.unification.material.Material;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;

public class FuelHeaterTiers {

    private final float efficiency;
    private final int powerMultiplier;
    private final Material material;
    private int textureId;

    public static FuelHeaterTiers BRICK = new FuelHeaterTiers(Brick, 0.50F, 1);
    public static FuelHeaterTiers INVAR = new FuelHeaterTiers(Invar, 1.00F, 2, HeatersMethods.INVAR);
    public static FuelHeaterTiers BRONZE = new FuelHeaterTiers(Bronze, 0.80F, 3, HeatersMethods.BRONZE);
    public static FuelHeaterTiers STEEL = new FuelHeaterTiers(Steel, 0.80F, 4, HeatersMethods.STEEL);
    public static FuelHeaterTiers TITANIUM = new FuelHeaterTiers(Titanium, 0.80F, 8, HeatersMethods.TITANIUM);
    public static FuelHeaterTiers TUNGSTEN_STEEL = new FuelHeaterTiers(TungstenSteel, 0.80F, 16, HeatersMethods.TUNGSTEN_STEEL);

    /**
     * Contains all the {@code FuelHeater}s registered.
     */
    public static List<FuelHeaterTiers> FUEL_HEATERS = new ArrayList<FuelHeaterTiers>() {{
       add(BRICK);
       add(INVAR);
       add(BRONZE);
       add(STEEL);
       add(TITANIUM);
       add(TUNGSTEN_STEEL);
    }};

    public FuelHeaterTiers(Material material, float efficiency, int powerMultiplier, int textureId) {
        this.material = material;
        this.efficiency = efficiency;
        this.powerMultiplier = powerMultiplier;
        this.textureId = textureId;
    }

    public FuelHeaterTiers(Material material, float efficiency, int powerMultiplier) {
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

    public int getTextureId() {
        return textureId;
    }
}
