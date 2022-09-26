package tekcays_addon.api.utils;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;

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


}
