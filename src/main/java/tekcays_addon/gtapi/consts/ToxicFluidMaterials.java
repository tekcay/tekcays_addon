package tekcays_addon.gtapi.consts;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;
import tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags;

public class ToxicFluidMaterials {

    public static void addFlag() {
        toxicFluidMaterials.forEach(material -> material.addFlags(TKCYAMaterialFlags.TOXIC));
    }

    public static List<Material> toxicFluidMaterials = new ArrayList<>();

    static {
        toxicFluidMaterials.add(SulfurDioxide);
        toxicFluidMaterials.add(SulfurTrioxide);
        toxicFluidMaterials.add(CarbonMonoxide);
        toxicFluidMaterials.add(NitrogenDioxide);
        toxicFluidMaterials.add(DinitrogenTetroxide);
        toxicFluidMaterials.add(Benzene);
        toxicFluidMaterials.add(Toluene);
        toxicFluidMaterials.add(Tetranitromethane);
        toxicFluidMaterials.add(HydrogenSulfide);
        toxicFluidMaterials.add(Mercury);
        toxicFluidMaterials.add(SulfurDioxide);
        toxicFluidMaterials.add(SulfurTrioxide);
        toxicFluidMaterials.add(CarbonMonoxide);
        toxicFluidMaterials.add(CarbonMonoxide);
        toxicFluidMaterials.add(NitrogenDioxide);
        toxicFluidMaterials.add(DinitrogenTetroxide);
        toxicFluidMaterials.add(Benzene);
        toxicFluidMaterials.add(Toluene);
    }
}
