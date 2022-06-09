package tekcays_addon.api.unification;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import net.minecraftforge.fluids.Fluid;

public class TKCYAMaterialFlagAddition {

    public static void init() {

        // Foils
        Materials.Titanium.addFlags(MaterialFlags.GENERATE_FOIL);

    }
}
