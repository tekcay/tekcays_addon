package tekcays_addon.gtapi.capability.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.LogisticContainer;

public class LogisticImpl extends MTETrait implements LogisticContainer {

    /**
     * Create a new MTE trait.
     *
     * @param metaTileEntity the MTE to reference, and add the trait to
     */
    public LogisticImpl(@NotNull MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
    }

    @NotNull
    @Override
    public String getName() {
        return "LOGISTIC";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC) {
            return TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC.cast(this);
        }
        return null;
    }
}
