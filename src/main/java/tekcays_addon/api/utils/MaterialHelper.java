package tekcays_addon.api.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

public class MaterialHelper implements IMaterialHelper{

    static Map<Fluid, Material> MATERIAL_FLUID_MAP = new HashMap<>();

    /**
     * Creates a {@code Map} that allows retrieving a {@code Material} from a {@code Fluid}.
     */
    public static void init() {
        GregTechAPI.MATERIAL_REGISTRY.forEach(material -> {
            if (material.hasFluid()) MATERIAL_FLUID_MAP.put(material.getFluid(), material);
        });
    }

    @Override
    public Map<Fluid, Material> getMaterialFluidMap() {
        return MATERIAL_FLUID_MAP;
    }
}
