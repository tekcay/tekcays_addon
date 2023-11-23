package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.Logistic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LogisticImpl extends MTETrait implements Logistic {

    /**
     * Create a new MTE trait.
     *
     * @param metaTileEntity the MTE to reference, and add the trait to
     */
    public LogisticImpl(@Nonnull MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
    }

    @Nonnull
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
