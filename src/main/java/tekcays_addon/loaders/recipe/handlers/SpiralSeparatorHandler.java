package tekcays_addon.loaders.recipe.handlers;

import net.minecraft.item.ItemStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.common.items.TKCYAMetaItems;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.Stone;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static tekcays_addon.api.utils.MiscMethods.*;
import static tekcays_addon.api.utils.TKCYAValues.DUST_MIXTURE_WITH_NBT;

public class SpiralSeparatorHandler {

    public static void init() {

        for (ItemStack stack : DUST_MIXTURE_WITH_NBT) {

            List<ItemStack> list = setOutputStack(stack.getTagCompound().getString("Composition"), dustTiny);

            TKCYARecipeMaps.SPIRAL_SEPARATION.recipeBuilder()
                    .inputs(stack)
                    .outputs(new ArrayList<ItemStack>(list))
                    .duration(10 * getItemStackListSize(list))
                    .EUt(120)
                    .hidden()
                    .buildAndRegister();
        }

        //Exemple recipe
        TKCYARecipeMaps.SPIRAL_SEPARATION.recipeBuilder()
                .inputs(TKCYAMetaItems.DUST_MIXTURE.getStackForm())
                .output(dust, Stone)
                .duration(80)
                .EUt(120)
                .buildAndRegister();
    }

}
