package tekcays_addon.common.blocks.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.block.VariantItemBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tekcays_addon.api.block.IDistillationTraysBlockStats;
import tekcays_addon.api.block.IPumpMachineBlockStats;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockDistillationTrays extends VariantBlock<BlockDistillationTrays.DistillationTraysType> {

    public BlockDistillationTrays() {
        super(net.minecraft.block.material.Material.IRON);
        setTranslationKey("distillation_trays");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 1);
        setDefaultState(getState(DistillationTraysType.DISTILLATION_TRAYS_LV));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World worldIn, List<String> lines, @Nonnull ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        // noinspection rawtypes, unchecked
        VariantItemBlock itemBlock = (VariantItemBlock<DistillationTraysType, BlockDistillationTrays>) itemStack.getItem();
        IBlockState stackState = itemBlock.getBlockState(itemStack);
        DistillationTraysType distillationTraysType = getState(stackState);

        lines.add(I18n.format("tile.pump_machine.tooltip_targetVacuum", distillationTraysType.traysNumber));
    }


    public enum DistillationTraysType implements IStringSerializable, IDistillationTraysBlockStats {

        DISTILLATION_TRAYS_LV("pump_machine_lv", "Pump Machine LV", 10000),
        DISTILLATION_TRAYS_MV("pump_machine_mv", "Pump Machine MV", 5000),
        DISTILLATION_TRAYS_HV("pump_machine_hv", "Pump Machine HV", 2500),
        DISTILLATION_TRAYS_EV("pump_machine_ev", "Pump Machine EV", 1500);



        private final String name;
        private final String localizedName;
        private final int traysNumber;


        DistillationTraysType(String name, String localizedName, int traysNumber) {
            this.name = name;
            this.localizedName = localizedName;
            this.traysNumber = traysNumber;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }

        @Nonnull
        @Override
        public String getLocalizedName() {
            return localizedName;
        }

        @Override
        public int getTraysNumber() {
            return traysNumber;
        }





    }
}
