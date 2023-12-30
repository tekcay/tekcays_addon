package tekcays_addon.gtapi.consts;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;

public class DangerFluids {

    public static void init() {
        // TOXIC_FLUIDS.forEach(material -> material.addFlags(TKCYAMaterialFlags.TOXIC));
        // INFLAMMABLE_FLUIDS.forEach(material -> material.addFlags(TKCYAMaterialFlags.INFLAMMABLE));
    }

    public static List<Material> TOXIC_FLUIDS = new ArrayList<>();
    public static List<Material> INFLAMMABLE_FLUIDS = new ArrayList<>();

    static {
        TOXIC_FLUIDS.add(SulfurDioxide);
        TOXIC_FLUIDS.add(SulfurTrioxide);
        TOXIC_FLUIDS.add(CarbonMonoxide);
        TOXIC_FLUIDS.add(NitrogenDioxide);
        TOXIC_FLUIDS.add(DinitrogenTetroxide);
        TOXIC_FLUIDS.add(Benzene);
        TOXIC_FLUIDS.add(Toluene);
        TOXIC_FLUIDS.add(Tetranitromethane);
        TOXIC_FLUIDS.add(HydrogenSulfide);
        TOXIC_FLUIDS.add(Mercury);
    }

    static {
        INFLAMMABLE_FLUIDS.add(Hydrogen);
        INFLAMMABLE_FLUIDS.add(Tetranitromethane);
        INFLAMMABLE_FLUIDS.add(Toluene);
        INFLAMMABLE_FLUIDS.add(Benzene);
        INFLAMMABLE_FLUIDS.add(Methane);
        INFLAMMABLE_FLUIDS.add(Ethane);
        INFLAMMABLE_FLUIDS.add(Ethylene);
        INFLAMMABLE_FLUIDS.add(HydroCrackedButadiene);
        INFLAMMABLE_FLUIDS.add(HydroCrackedButane);
        INFLAMMABLE_FLUIDS.add(HydroCrackedButene);
        INFLAMMABLE_FLUIDS.add(HydroCrackedEthane);
        INFLAMMABLE_FLUIDS.add(HydroCrackedEthylene);
        INFLAMMABLE_FLUIDS.add(Propane);
        INFLAMMABLE_FLUIDS.add(Propene);
    }
}
