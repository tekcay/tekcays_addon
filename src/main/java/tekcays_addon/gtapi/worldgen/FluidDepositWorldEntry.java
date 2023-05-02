package tekcays_addon.gtapi.worldgen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

import static tekcays_addon.gtapi.consts.DepositValues.*;
import static tekcays_addon.gtapi.worldgen.FluidDepositHandler.fluidDepositionToWeight;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FluidDepositWorldEntry {
    private FluidDepositDefinition fluidDepositDefinition;
    private int fluidAmount;
    private int depth;

    public void decreaseFluidAmount(int amount) {
        fluidAmount = Math.max(0, fluidAmount - amount);
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(FLUID_AMOUNT, fluidAmount);
        tag.setInteger(DEPTH, depth);
        if (fluidDepositDefinition != null) {
            tag.setString(VEIN, fluidDepositDefinition.getDepositName());
        }
        return tag;
    }

    @Nonnull
    public static FluidDepositWorldEntry readFromNBT(@Nonnull NBTTagCompound tag) {
        FluidDepositWorldEntry info = new FluidDepositWorldEntry();
        info.fluidAmount = tag.getInteger(FLUID_AMOUNT);
        info.depth = tag.getInteger(DEPTH);

        if (tag.hasKey(VEIN)) {
            String s = tag.getString(VEIN);
            for (FluidDepositDefinition definition : fluidDepositionToWeight.keySet()) {
                if (s.equalsIgnoreCase(definition.getDepositName()))
                    info.fluidDepositDefinition = definition;
            }
        }
        return info;
    }
}
