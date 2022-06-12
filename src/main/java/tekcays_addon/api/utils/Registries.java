package tekcays_addon.api.utils;

import gregtech.api.unification.material.Material;

import java.util.List;
import java.util.Map;

public class Registries {

    public List<Material> list;
    public Map<Material, Material> map;


    Registries(List<Material> list) {

        this.list = list;
    }
    Registries(List<Material> list, Map<Material, Material> map) {

        this.list = list;
        this.map = map;
    }

}
