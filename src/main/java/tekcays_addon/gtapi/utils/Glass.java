package tekcays_addon.gtapi.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix;

public class Glass {

    public static final List<Glass> GLASS_STUFF = new ArrayList<>();
    public static final Glass BLOCK_GLASS = new Glass(TKCYAOrePrefix.moldBlock, new ItemStack(Blocks.GLASS),
            GTValues.L);
    public OrePrefix mold;
    public ItemStack output;
    public int fluidAmount;

    public Glass(OrePrefix mold, ItemStack output, int fluidAmount) {
        this.mold = mold;
        this.output = output;
        this.fluidAmount = fluidAmount;
    }
}
