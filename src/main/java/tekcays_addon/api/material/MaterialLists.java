package tekcays_addon.api.material;

import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;

public class MaterialLists {

    public static List<Material> ALLOYS = new ArrayList<>();

    static {
        ALLOYS.add(PigIron);
        ALLOYS.add(Monel);
        ALLOYS.add(Constantan);
        ALLOYS.add(SiliconCarbide);
        ALLOYS.add(BT6);
        ALLOYS.add(HastelloyC276); // Hastelloy C-276
        ALLOYS.add(HastelloyN); // Hastelloy N
        ALLOYS.add(Inconel600);
        ALLOYS.add(Inconel690); // Inconel-690
        ALLOYS.add(EglinSteel);
        ALLOYS.add(AF1410); // AF-1410
        ALLOYS.add(Aermet100); // Aermet-100
        ALLOYS.add(HY180); // HY-180
        ALLOYS.add(HP9420); // HP9-4-20
        ALLOYS.add(StelliteStarJ);
        ALLOYS.add(Mangalloy);
        ALLOYS.add(Talonite); // talonite alloy
        ALLOYS.add(Nitinol);
        ALLOYS.add(Nitinol60); // Nitinol-60
        ALLOYS.add(TC4); // TiAl6V4, search for Ti-6Al-4V, Ti24Al3V
        ALLOYS.add(Aluminium5052); // Al9MgMn4
        ALLOYS.add(Aluminium6061); // Al914Mg10Si4FeCu //Common
    }
}
