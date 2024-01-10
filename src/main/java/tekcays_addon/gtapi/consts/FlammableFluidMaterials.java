package tekcays_addon.gtapi.consts;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;
import tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags;

public class FlammableFluidMaterials {

    public static void addFlag() {
        flammableFluidMaterials.forEach(material -> material.addFlags(TKCYAMaterialFlags.FLAMMABLE));
    }

    public static List<Material> flammableFluidMaterials = new ArrayList<>();

    static {
        flammableFluidMaterials.add(HydroCrackedButadiene);
        flammableFluidMaterials.add(HydroCrackedButene);
        flammableFluidMaterials.add(HydroCrackedButane);
        flammableFluidMaterials.add(HydroCrackedEthane);
        flammableFluidMaterials.add(HydroCrackedEthylene);
        flammableFluidMaterials.add(HydroCrackedPropane);
        flammableFluidMaterials.add(HydroCrackedPropene);
        flammableFluidMaterials.add(Ethane);
        flammableFluidMaterials.add(HydroCrackedEthane);
        flammableFluidMaterials.add(SteamCrackedEthane);
        flammableFluidMaterials.add(Ethylene);
        flammableFluidMaterials.add(HydroCrackedEthylene);
        flammableFluidMaterials.add(SteamCrackedEthylene);
        flammableFluidMaterials.add(Propene);
        flammableFluidMaterials.add(HydroCrackedPropene);
        flammableFluidMaterials.add(SteamCrackedPropene);
        flammableFluidMaterials.add(Propane);
        flammableFluidMaterials.add(HydroCrackedPropane);
        flammableFluidMaterials.add(SteamCrackedPropane);
        flammableFluidMaterials.add(Butane);
        flammableFluidMaterials.add(HydroCrackedButane);
        flammableFluidMaterials.add(SteamCrackedButane);
        flammableFluidMaterials.add(Butene);
        flammableFluidMaterials.add(HydroCrackedButene);
        flammableFluidMaterials.add(SteamCrackedButene);
        flammableFluidMaterials.add(Butadiene);
        flammableFluidMaterials.add(HydroCrackedButadiene);
        flammableFluidMaterials.add(SteamCrackedButadiene);
        flammableFluidMaterials.add(HeavyFuel);
        flammableFluidMaterials.add(LightlyHydroCrackedHeavyFuel);
        flammableFluidMaterials.add(LightlySteamCrackedHeavyFuel);
        flammableFluidMaterials.add(HeavyFuel);
        flammableFluidMaterials.add(SeverelyHydroCrackedHeavyFuel);
        flammableFluidMaterials.add(SeverelySteamCrackedHeavyFuel);
        flammableFluidMaterials.add(LightFuel);
        flammableFluidMaterials.add(LightlyHydroCrackedLightFuel);
        flammableFluidMaterials.add(LightlySteamCrackedLightFuel);
        flammableFluidMaterials.add(LightFuel);
        flammableFluidMaterials.add(SeverelyHydroCrackedLightFuel);
        flammableFluidMaterials.add(SeverelySteamCrackedLightFuel);
        flammableFluidMaterials.add(Naphtha);
        flammableFluidMaterials.add(LightlyHydroCrackedNaphtha);
        flammableFluidMaterials.add(LightlySteamCrackedNaphtha);
        flammableFluidMaterials.add(Naphtha);
        flammableFluidMaterials.add(SeverelyHydroCrackedNaphtha);
        flammableFluidMaterials.add(SeverelySteamCrackedNaphtha);
        flammableFluidMaterials.add(RefineryGas);
        flammableFluidMaterials.add(LightlyHydroCrackedGas);
        flammableFluidMaterials.add(LightlySteamCrackedGas);
        flammableFluidMaterials.add(RefineryGas);
        flammableFluidMaterials.add(SeverelyHydroCrackedGas);
        flammableFluidMaterials.add(SeverelySteamCrackedGas);
        flammableFluidMaterials.add(SeverelySteamCrackedMethane);
        flammableFluidMaterials.add(ModeratelySteamCrackedMethane);
        flammableFluidMaterials.add(LightlySteamCrackedMethane);
        flammableFluidMaterials.add(Methane);
        flammableFluidMaterials.add(Benzene);
        flammableFluidMaterials.add(Acetone);
        flammableFluidMaterials.add(Toluene);
    }
}
