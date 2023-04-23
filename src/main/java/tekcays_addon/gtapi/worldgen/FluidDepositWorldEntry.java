package tekcays_addon.gtapi.worldgen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

import static tekcays_addon.gtapi.consts.FluidDepositValues.FLUID_AMOUNT;
import static tekcays_addon.gtapi.worldgen.FluidDepositHandler.veinList;

@Getter
@Setter
@AllArgsConstructor
public class FluidVeinWorldEntry {
    private FluidDepositDefinition vein;
    private int fluidAmount;

    private FluidVeinWorldEntry() {

    }

    @SuppressWarnings("unused")
    public void setOperationsRemaining(int amount) {
        this.fluidAmount = amount;
    }

    public void decreaseOperations(int amount) {
        fluidAmount = Math.max(0, fluidAmount - amount);
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(FLUID_AMOUNT, fluidAmount);
        if (vein != null) {
            tag.setString("vein", vein.getDepositName());
        }
        return tag;
    }

    @Nonnull
    public static FluidVeinWorldEntry readFromNBT(@Nonnull NBTTagCompound tag) {
        FluidVeinWorldEntry info = new FluidVeinWorldEntry();
        info.fluidAmount = tag.getInteger(FLUID_AMOUNT);

        if (tag.hasKey("vein")) {
            String s = tag.getString("vein");
            for (FluidDepositDefinition definition : veinList.keySet()) {
                if (s.equalsIgnoreCase(definition.getDepositName()))
                    info.vein = definition;
            }
        }

        return info;
    }
}
