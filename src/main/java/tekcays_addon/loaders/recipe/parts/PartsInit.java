package tekcays_addon.loaders.recipe.parts;

import static gregtech.api.unification.ore.OrePrefix.foil;
import static gregtech.api.unification.ore.OrePrefix.rotor;
import static tekcays_addon.common.TKCYAConfigHolder.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.blade;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;

public class PartsInit {

    public static void init() {
        if (miscOverhaul.enableMagneticOverhaul) Polarizing.init();
        if (miscOverhaul.enableElectrolysisOverhaul) ElectrodeHandler.init();

        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {

            boolean curvedPlateCheck = curvedPlate.doGenerateItem(material);
            boolean bladeCheck = blade.doGenerateItem(material);
            boolean rotorCheck = rotor.doGenerateItem(material);
            boolean pipeCheck = material.hasProperty(PropertyKey.FLUID_PIPE) ||
                    material.hasProperty(PropertyKey.ITEM_PIPE);

            if (curvedPlateCheck) CurvedPlateHandler.init(material);
            if (bladeCheck && harderStuff.enableHarderRotors) BladeHandler.init(material);
            if (rotorCheck && curvedPlateCheck && bladeCheck && harderStuff.enableHarderRotors)
                RotorHandler.init(material);
            if (curvedPlateCheck && pipeCheck && harderStuff.enableHarderPipes) PipesHandler.init(material);
            if (harderStuff.enableHarderFoil && foil.doGenerateItem(material)) FoilHandler.process(material);
        }
    }
}
