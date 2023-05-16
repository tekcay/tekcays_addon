package tekcays_addon.gtapi.unification.material.ore;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;
import static gregtech.api.unification.OreDictUnifier.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.*;

public class OreDictAdditions {

    public static void misc() {
        registerOre(new ItemStack(Items.GLASS_BOTTLE, 1, W), bottleGlass, null);
    }



}
