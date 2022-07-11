package tekcays_addon.api.unification.material.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;

import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYAPolymers {

    public static void init() {

        HighDensityPolyethylene = new Material.Builder(24700, "high_density_polyethylene")
                .ingot()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Materials.Carbon, 2, Materials.Hydrogen, 4)
                .color(0xe08b41)
                .build();
        HighDensityPolyethylene.setFormula("(" + HighDensityPolyethylene.getChemicalFormula() + ")n", true);

        Polypropylene = new Material.Builder(24701, "high_density_polyethylene")
                .ingot()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Materials.Propene)
                .color(0xe08b41)
                .build();
        HighDensityPolyethylene.setFormula("(" + Materials.Propene.getChemicalFormula() + ")n", true);


    }
}
