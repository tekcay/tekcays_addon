package tekcays_addon.api.utils.capability;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.containers.IContainer;
import tekcays_addon.api.capability.containers.IHeatContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GetCapabilityHelper {

    /**
     *
     * @param emitterSide
     * @param recieverSide
     * @param capabilityToCompareWith
     * @param container
     * @return
     * @param <T>
     * @param <U>
     */
    @Nullable
    public static <T, U extends T> T getCapabilityOnSide(EnumFacing emitterSide, EnumFacing recieverSide, @Nonnull Capability<T> capabilityToCompareWith, IContainer<T, U> container) {
        if (capabilityToCompareWith instanceof IContainer) {
            return container.retrieveCapability(capabilityToCompareWith, recieverSide, emitterSide);
        } else return null;
    }

    /**
     *
     * @param capabilityToCompare provided by the {@code getCapability()} method from {@link MetaTileEntity}.
     * @param container the container that implements {@link IHeatContainer}.
     * @return the {@link Capability} of the provided {@code container}.
     * @param <T>
     * @param <U> the type of {@code container} that implements {@code R}.
     * @param <R> the interface of the {@code container} that extends {@link IContainer}.
     */
    @Nullable
    public static  <T, U extends R, R extends IContainer<R, U>> T getCapabilityOfContainer(Capability<T> capabilityToCompare, IContainer<R, U> container) {
        if (capabilityToCompare == container.getContainerCapability()) {
            return container.getContainerCapability().cast(container.getContainer());
        }
        return null;
    }

}
