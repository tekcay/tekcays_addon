package tekcays_addon.gtapi.capability.containers;

import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IDecompression {

    IFluidHandler getFluidHandler();
    boolean isActive();
    void setActivity(boolean willBeActive);
    boolean isAbleToDecompress();
    void setCompressAbility(boolean willBeAbleToCompress);
}
