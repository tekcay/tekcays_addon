package tekcays_addon.api.capability;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public interface AdjacentCapabilityHelper<T> {

    EnumFacing getOutputSide();
    Capability<T> getCapability();

    @Nullable
    default T getAdjacentCapabilityContainer(MetaTileEntity mte) {
        TileEntity te = mte.getWorld().getTileEntity(mte.getPos().offset(getOutputSide()));
        if (te != null) {
            return te.getCapability(getCapability(), getOutputSide().getOpposite());
        }
        return null;
    }
}
