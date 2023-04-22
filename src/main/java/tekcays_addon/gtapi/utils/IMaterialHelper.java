package tekcays_addon.gtapi.utils;

import gregtech.api.unification.material.Material;
import net.minecraftforge.fluids.Fluid;

import java.util.Map;

public interface IMaterialHelper {

    Map<Fluid, Material> getMaterialFluidMap();

    default Material getMaterial(Fluid fluid) {
        return getMaterialFluidMap().get(fluid);
    }



}
