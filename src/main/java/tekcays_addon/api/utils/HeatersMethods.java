package tekcays_addon.api.utils;

import gregtech.api.unification.material.Material;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.render.TKCYATextures;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.utils.FuelWithProperties.LIQUID_FUELS_BURNING;

public class HeatersMethods {


    /**
     * Calculate the burn time for this item taking into account the FuelHeater properties.
     * @param stack the fuel {@code ItemStack}.
     * @param fuelHeater the {@code FuelHeater} object to get both efficiency and power multiplier.
     * See {@link FuelHeater}.
     * @return
     */
    public static int getBurnTime(ItemStack stack, FuelHeater fuelHeater) {
        return (int) (TileEntityFurnace.getItemBurnTime(stack) * fuelHeater.getEfficiency() / fuelHeater.getPowerMultiplier());
    }

    public static int getBurnTime(FuelWithProperties fuelWithProperties, FuelHeater fuelHeater) {
        return (int) (fuelWithProperties.getBurnTime() * fuelHeater.getEfficiency() / fuelHeater.getPowerMultiplier());
    }

    public static SimpleOverlayRenderer getTextures(Material material) {
        if (material.equals(Bronze)) return TKCYATextures.BRONZE_STEAM_CASING;
        if (material.equals(Steel)) return TKCYATextures.STEEL_STEAM_CASING;
        if (material.equals(Invar)) return TKCYATextures.INVAR_STEAM_CASING;
        if (material.equals(Titanium)) return TKCYATextures.TITANIUM_STEAM_CASING;
        if (material.equals(TungstenSteel)) return TKCYATextures.TUNGSTEN_STEEL_STEAM_CASING;
        return TKCYATextures.BRONZE_STEAM_CASING;
    }


}
