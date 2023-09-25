package tekcays_addon.api;

import gregtech.api.unification.material.Material;
import gregtech.core.unification.material.internal.MaterialRegistryManager;

import java.util.Collection;

public class MaterialHelper {

    public static Collection<Material> getAllMaterials() {
        return MaterialRegistryManager.getInstance().getRegisteredMaterials();
    }
}
