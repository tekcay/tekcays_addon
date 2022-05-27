package tekcays_addon.multiblocks.api.unification;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;

public class TKCYAMaterialFlagAddition {

    public static void init() {

        // Frames
        Materials.TungstenCarbide.addFlags(MaterialFlags.GENERATE_FRAME);
        Materials.Tungsten.addFlags(MaterialFlags.GENERATE_FRAME);
        Materials.Brass.addFlags(MaterialFlags.GENERATE_FRAME);

        // Small Gears
        Materials.TungstenCarbide.addFlags(MaterialFlags.GENERATE_SMALL_GEAR);

        // Long Rods
        Materials.Neutronium.addFlags(MaterialFlags.GENERATE_LONG_ROD);

        // Rotors
        Materials.Iridium.addFlags(MaterialFlags.GENERATE_RING, MaterialFlags.GENERATE_ROTOR);

        // Springs
        Materials.Neutronium.addFlags(MaterialFlags.GENERATE_SPRING);

        // Dense Plates
        Materials.Neutronium.addFlags(MaterialFlags.GENERATE_DENSE);

        // Foils
        Materials.Graphene.addFlags(MaterialFlags.GENERATE_FOIL);
    }
}
