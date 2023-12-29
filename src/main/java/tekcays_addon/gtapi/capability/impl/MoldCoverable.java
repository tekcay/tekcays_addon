package tekcays_addon.gtapi.capability.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import lombok.Setter;
import tekcays_addon.api.covers.molds.CoverMoldWrapper;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IMoldCoverable;

@Setter
@Getter
public class MoldCoverable extends MTETrait implements IMoldCoverable {

    private CoverMoldWrapper coverMoldWrapper;
    private boolean canMoldCoverBePlaced;

    /**
     * Create a new MTE trait.
     *
     * @param metaTileEntity the MTE to reference, and add the trait to
     */
    public MoldCoverable(@NotNull MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
    }

    @NotNull
    @Override
    public String getName() {
        return "MoldCoverable";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_MOLD_COVERABLE) {
            return TKCYATileCapabilities.CAPABILITY_MOLD_COVERABLE.cast(this);
        }
        return null;
    }

    @Override
    public boolean canMoldCoverBePlaced() {
        return this.canMoldCoverBePlaced;
    }
}
