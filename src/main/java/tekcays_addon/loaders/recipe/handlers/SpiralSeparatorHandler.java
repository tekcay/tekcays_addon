package tekcays_addon.loaders.recipe.handlers;

import net.minecraft.item.ItemStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static tekcays_addon.gtapi.utils.MiscMethods.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.DUST_MIXTURE_WITH_NBT;

public class SpiralSeparatorHandler {

    public static void init() {

        for (ItemStack stack : DUST_MIXTURE_WITH_NBT) {
            List<ItemStack> list = new ArrayList<>(setOutputStack(stack.getTagCompound().getString("Composition"), dustTiny));
            TKCYARecipeMaps.SPIRAL_SEPARATION.recipeBuilder()
                    .inputs(stack)
                    .outputs(list)
                    .duration(10 * getItemStackListSize(list))
                    .EUt(120)
                    .buildAndRegister();
        }


    }
}
