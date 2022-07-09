package tekcays_addon.api.unification.material.ore;

import gregtech.common.items.MetaItems;


public class TKCYAOrePrefixAdditions {

    public static void moldsInit(){

        MetaItems.addOrePrefix(TKCYAOrePrefix.moldEmpty);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldIngot);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldPlate);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldStick);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldStickLong);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldGear);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldGearSmall);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldBolt);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldBall);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldCylinder);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldRing);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldRotor);
        MetaItems.addOrePrefix(TKCYAOrePrefix.moldCasing);
    }

    public static void moldsTooltip(){ //TODO show temperature for each mold

        /*

        for (Material m : MOLD_MATERIALS) {

            for (OrePrefix prefix : MOLD_PRODUCTION.values()) {

                OreDictUnifier.get(prefix, m).

                        lines.add(I18n.format("metaitem.generic.electric_item.tooltip",

            }

        }

         */
    }

}
