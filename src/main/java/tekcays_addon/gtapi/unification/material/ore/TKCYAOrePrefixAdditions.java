package tekcays_addon.gtapi.unification.material.ore;

import static gregtech.common.items.MetaItems.addOrePrefix;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.*;

public class TKCYAOrePrefixAdditions {

    public static void moldsInit(){
        addOrePrefix(moldEmpty);
        addOrePrefix(moldIngot);
        addOrePrefix(moldPlate);
        addOrePrefix(moldStick);
        addOrePrefix(moldStickLong);
        addOrePrefix(moldGear);
        addOrePrefix(moldGearSmall);
        addOrePrefix(moldBolt);
        addOrePrefix(moldBall);
        addOrePrefix(moldCylinder);
        addOrePrefix(moldRing);
        addOrePrefix(moldRotor);
        addOrePrefix(moldCasing);
        addOrePrefix(moldBottle);
        addOrePrefix(moldBlock);

        //mold.setMarkerPrefix(true);
    }

    public static void miscInit(){
        addOrePrefix(bottleGlass);
        addOrePrefix(curvedPlate);
    }


    public static void moldsTooltip(){ //TODO show temperature for each mold

    /*
        for (Material m : MOLD_MATERIALS) {
            for (OrePrefix prefix : MOLD_PRODUCTION.values()) {

                OreDictUnifier.get(prefix, m).getTooltip.add(m.getFluid().getTemperature() + " K");

                        lines.add(I18n.format("metaitem.mold.tooltip", m.getFluid().getTemperature()));
            }

        }
    */
    }

}
