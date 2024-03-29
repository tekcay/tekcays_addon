package tekcays_addon.gtapi.unification.material.ore;

import static gregtech.common.items.MetaItems.addOrePrefix;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.*;

public class TKCYAOrePrefixAdditions {

    public static void moldsInit() {
        addOrePrefix(moldEmpty);
        addOrePrefix(moldIngot);
        addOrePrefix(moldPlate);
        addOrePrefix(moldStick);
        addOrePrefix(moldStickLong);
        addOrePrefix(moldGear);
        addOrePrefix(moldGearSmall);
        addOrePrefix(moldBolt);
        addOrePrefix(moldRing);
        addOrePrefix(moldBlock);

        // mold.setMarkerPrefix(true);
    }

    public static void miscInit() {
        addOrePrefix(bottleGlass);
        addOrePrefix(curvedPlate);
        addOrePrefix(cutWood);
        addOrePrefix(blade);
    }

    public static void componentsInit() {
        addOrePrefix(lvComponents);
        addOrePrefix(mvComponents);
        addOrePrefix(hvComponents);
        addOrePrefix(evComponents);
        addOrePrefix(ivComponents);
        addOrePrefix(luvComponents);
        addOrePrefix(zpmComponents);
        addOrePrefix(uvComponents);
    }

    public static void moldsTooltip() {
        // TODO show temperature for each mold

        /*
         * for (Material m : MOLD_MATERIALS) {
         * for (OrePrefix prefix : MOLD_PRODUCTION.values()) {
         * 
         * OreDictUnifier.get(prefix, m).getTooltip.add(m.getFluid().getTemperature() + " K");
         * 
         * lines.add(I18n.format("metaitem.mold.tooltip", m.getFluid().getTemperature()));
         * }
         * 
         * }
         */
    }
}
