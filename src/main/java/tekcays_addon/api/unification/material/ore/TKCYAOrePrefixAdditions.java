package tekcays_addon.api.unification.material.ore;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static tekcays_addon.api.unification.TKCYAMaterialFlagAddition.MOLD_MATERIALS;
import static tekcays_addon.loaders.recipe.handlers.TKCYACastingTableRecipeHandler.MOLD_PRODUCTION;

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
