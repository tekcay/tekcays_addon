package tekcays_addon.gtapi.utils;

import gregtech.api.unification.material.Material;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;

@Getter
@AllArgsConstructor
public class FuelHeaterTiers {

    private final Material material;
    private final float efficiency;
    private final int powerMultiplier;

    private int textureId;

    public static final FuelHeaterTiers BRICK = new FuelHeaterTiers(Brick, 0.25F, 1);
    public static final FuelHeaterTiers INVAR = new FuelHeaterTiers(Invar, 1.00F, 2, HeatersMethods.INVAR);
    public static final FuelHeaterTiers BRONZE = new FuelHeaterTiers(Bronze, 0.75F, 3, HeatersMethods.BRONZE);
    public static final FuelHeaterTiers STEEL = new FuelHeaterTiers(Steel, 0.70F, 4, HeatersMethods.STEEL);
    public static final FuelHeaterTiers TITANIUM = new FuelHeaterTiers(Titanium, 0.85F, 8, HeatersMethods.TITANIUM);
    public static final FuelHeaterTiers TUNGSTEN_STEEL = new FuelHeaterTiers(TungstenSteel, 0.90F, 16, HeatersMethods.TUNGSTEN_STEEL);

    /**
     * Contains all the {@code FuelHeater}s registered.
     */
    public static List<FuelHeaterTiers> FUEL_HEATERS = new ArrayList<FuelHeaterTiers>() {{
        add(INVAR);
        add(BRONZE);
        add(STEEL);
        add(TITANIUM);
        add(TUNGSTEN_STEEL);
    }};

    public FuelHeaterTiers(Material material, float efficiency, int powerMultiplier) {
        this.material = material;
        this.efficiency = efficiency;
        this.powerMultiplier = powerMultiplier;
    }

    public String getMaterialName() {
        return this.getMaterial().getUnlocalizedName();
    }


}
