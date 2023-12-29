package tekcays_addon.loaders.recipe.parts;

import static gregtech.api.recipes.RecipeMaps.LASER_ENGRAVER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.lens;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static tekcays_addon.gtapi.consts.TKCYAValues.ELECTRODE_MATERIALS;

import net.minecraft.item.ItemStack;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

public class ElectrodeHandler {

    public static void init() {
        for (Material m : ELECTRODE_MATERIALS) {

            ItemStack electrodeStack = TKCYAMetaItems.ELECTRODE.getStackForm();

            ElectrodeBehavior.getInstanceFor(electrodeStack).setPartMaterial(electrodeStack, m);

            LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(stickLong, m)
                    .notConsumable(lens, Materials.Glass)
                    .outputs(electrodeStack)
                    .duration((int) m.getMass() * 5)
                    .EUt(24)
                    .buildAndRegister();
        }
    }
}
