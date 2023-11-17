package tekcays_addon.common;

import gregtech.api.items.metaitem.MetaOreDictItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.IBlockOre;
import gregtech.common.blocks.BlockCompressed;
import gregtech.common.blocks.BlockFrame;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
        TKCYATextures.preInit();
        OrePrefixValues.init();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TKCYAMetaBlocks.registerItemModels();
    }

    @SubscribeEvent
    public static void addMaterialFormulaHandler(@Nonnull ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock) itemStack.getItem()).getBlock();
            if (!(block instanceof BlockFrame) && !(block instanceof BlockCompressed) && !(block instanceof IBlockOre) && !(block instanceof IFluidBlock)) {
                // Do not apply this tooltip to blocks other than:
                // - Frames
                // - Compressed Blocks
                // - Ores
                // - Fluids
                return;
            }
        }

        // Handles Item tooltips
        List<String> tooltips = new ArrayList<>();

        // Test for Items
        UnificationEntry unificationEntry = OreDictUnifier.getUnificationEntry(itemStack);
        if (unificationEntry == null) return;
        OrePrefix orePrefix = unificationEntry.orePrefix;
        Double units = OrePrefixValues.ORE_PREFIX_TO_UNITS.get(orePrefix);
        if (units == null) return;

        if (itemStack.getItem() instanceof MetaOreDictItem) { // Test for OreDictItems
        MetaOreDictItem oreDictItem = (MetaOreDictItem) itemStack.getItem();
        Optional<String> oreDictName = OreDictUnifier.getOreDictionaryNames(itemStack).stream().findFirst();
        if (oreDictName.isPresent() && oreDictItem.OREDICT_TO_FORMULA.containsKey(oreDictName.get()) && !oreDictItem.OREDICT_TO_FORMULA.get(oreDictName.get()).isEmpty()) {
            //already done in GTCEu  //tooltips.add(TextFormatting.YELLOW + oreDictItem.OREDICT_TO_FORMULA.get(oreDictName.get()));
            tooltips.add(TextFormatting.AQUA + String.format("%.3f units", units));

        }
    } else if (unificationEntry.material != null) {
        if (unificationEntry.material.getChemicalFormula() != null && !unificationEntry.material.getChemicalFormula().isEmpty()) {
            //already done in GTCEu //tooltips.add(TextFormatting.YELLOW + unificationEntry.material.getChemicalFormula());
            tooltips.add(TextFormatting.AQUA + String.format("%.3f units", units));
        }

        if (tooltips != null) {
            for (String s : tooltips) {
                if (s == null || s.isEmpty()) continue;
                event.getToolTip().add(s);
            }
        }
    }
    }

}
