package tekcays_addon.api.unification.material.materials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.api.unification.material.info.TKCYAMaterialFlags;

import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYAPolymers {

    public static void init() {

        HighDensityPolyethylene = new Material.Builder(24700, "high_density_polyethylene")
                .ingot().fluid()
                .fluidPipeProperties(410, 50, true)
                .flags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_FOIL,
                        MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING, MaterialFlags.DISABLE_DECOMPOSITION, TKCYAMaterialFlags.POLYMER)
                .components(Materials.Carbon, 2, Materials.Hydrogen, 4)
                .color(0xebf0ef)
                .build();
        HighDensityPolyethylene.setFormula("(" + HighDensityPolyethylene.getChemicalFormula() + ")n", true);

        Polypropylene = new Material.Builder(24701, "polypropylene")
                .ingot().fluid()
                .fluidPipeProperties(420, 50, true, true, false, false)
                .flags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_FOIL,
                        MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING, MaterialFlags.DISABLE_DECOMPOSITION, TKCYAMaterialFlags.POLYMER)
                .components(Materials.Carbon, 3, Materials.Hydrogen, 6)
                .color(0xe1e6e5)
                .build();
        Polypropylene.setFormula("(" + Materials.Propene.getChemicalFormula() + ")n", true);


    }
}
