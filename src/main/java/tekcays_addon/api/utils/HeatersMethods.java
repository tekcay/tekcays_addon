package tekcays_addon.api.utils;

import gregtech.api.unification.material.Material;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import tekcays_addon.api.render.TKCYATextures;

import static gregtech.api.unification.material.Materials.*;

public class HeatersMethods {

    //STEAM TIERS
    public static final int BRONZE = 0;
    public static final int STEEL = 1;
    public static final int INVAR = 2;
    public static final int TITANIUM = 3;
    public static final int TUNGSTEN_STEEL = 4;


    /**
     * Calculate the burn time for this item taking into account the FuelHeater properties.
     * @param stack the fuel {@code ItemStack}.
     * @param fuelHeater the {@code FuelHeater} object to get both efficiency and power multiplier.
     * See {@link FuelHeaterTiers}.
     * @return
     */
    public static int getBurnTime(ItemStack stack, FuelHeaterTiers fuelHeater) {
        return (int) (TileEntityFurnace.getItemBurnTime(stack) * fuelHeater.getEfficiency() / fuelHeater.getPowerMultiplier());
    }

    public static int getBurnTime(FuelWithProperties fuelWithProperties, FuelHeaterTiers fuelHeater) {
        return (int) (fuelWithProperties.getBurnTime() * fuelHeater.getEfficiency() / fuelHeater.getPowerMultiplier());
    }

}
