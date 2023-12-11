package tekcays_addon.gtapi.unification.material.info;

import gregtech.api.unification.material.Materials;

public class TKCYAChemicalFormula {

    public static void modifyChemicalFormula() {
        Materials.Steel.setFormula("Fe50C", true);
        Materials.Oxygen.setFormula("O2", true);
        Materials.Hydrogen.setFormula("H2", true);
        Materials.Nitrogen.setFormula("N2", true);
        Materials.Sulfur.setFormula("S8", true);
        Materials.Fluorine.setFormula("F2", true);
        Materials.Chlorine.setFormula("Cl2", true);
        Materials.Bromine.setFormula("Br2", true);
        Materials.Iodine.setFormula("I2", true);
    }
}
