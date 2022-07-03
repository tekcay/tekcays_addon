package tekcays_addon.api.unification.material.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static gregtech.api.unification.material.info.MaterialIconSet.SHINY;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYAMiscMaterials {

    public static void init(){

        HotAir = new Material.Builder(24101, "hot_air")
                .fluid(FluidTypes.GAS)
                .components(Materials.Air, 1)
                .color(0xe08b41)
                .build();

        PreciousMetal = new Material.Builder(24102, "precious_metal")
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper)
                .components(Materials.Gold, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        PreciousMetal.setFormula("Au?");

    }

}
