package tekcays_addon.gtapi.utils;

import gregtech.api.GTValues;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix;

import java.util.ArrayList;
import java.util.List;

public class Glass {

    public static final List<Glass> GLASS_STUFF = new ArrayList<>();
    public static final Glass BOTTLE = new Glass(TKCYAOrePrefix.moldBottle, new ItemStack(Items.GLASS_BOTTLE), GTValues.L / 4);
    public static final Glass BLOCK_GLASS = new Glass(TKCYAOrePrefix.moldBlock, new ItemStack(Blocks.GLASS), GTValues.L);
    public static final Glass GLASS_TUBE = new Glass(TKCYAOrePrefix.moldBall, MetaItems.GLASS_TUBE.getStackForm(), GTValues.L / 2);
    public OrePrefix mold;
    public ItemStack output;
    public int fluidAmount;
    public Glass(OrePrefix mold, ItemStack output, int fluidAmount) {
        this.mold = mold;
        this.output = output;
        this.fluidAmount = fluidAmount;
    }

}