package tekcays_addon.api.utils;

import static tekcays_addon.api.utils.MiscMethods.*;

public class RegistriesList {

    public static final Registries MAGNETIC_MATERIALS = new Registries(getMagneticMaterialsRegistryList(), getMagneticMaterialsRegistryMap());
    public static final Registries FOIL_MATERIALS = new Registries(getFoilMaterialsList());

}
