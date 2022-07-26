package tekcays_addon.api.unification.material.ore;

import gregtech.api.unification.OreDictUnifier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;

public class OreDictAdditions {

    public static void misc() {
        OreDictUnifier.registerOre(new ItemStack(Items.GLASS_BOTTLE, 1, W), "bottleGlass");
        OreDictUnifier.registerOre(new ItemStack(Items.WHEAT_SEEDS), "seed");
        OreDictUnifier.registerOre(new ItemStack(Items.BEETROOT_SEEDS), "seed");
        OreDictUnifier.registerOre(new ItemStack(Items.MELON_SEEDS), "seed");
        OreDictUnifier.registerOre(new ItemStack(Items.PUMPKIN_SEEDS), "seed");

    }



}
