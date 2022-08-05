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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockPump extends VariantBlock<BlockPump.PumpType> {

    public BlockPump() {
        super(net.minecraft.block.material.Material.IRON);
        setTranslationKey("pump_machine");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 1);
        setDefaultState(getState(PumpType.PUMP_MACHINE_LV));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World worldIn, List<String> lines, @Nonnull ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        // noinspection rawtypes, unchecked
        VariantItemBlock itemBlock = (VariantItemBlock<PumpType, BlockPump>) itemStack.getItem();
        IBlockState stackState = itemBlock.getBlockState(itemStack);
        PumpType pumpType = getState(stackState);

        lines.add(I18n.format("tile.pump_machine.tooltip_targetVacuum", pumpType.targetVacuum));
    }

    public enum PumpType implements IStringSerializable {

        PUMP_MACHINE_LV("pump_machine_lv", 10000),
        PUMP_MACHINE_MV("pump_machine_mv", 5000),
        PUMP_MACHINE_HV("pump_machine_hv", 2500),
        PUMP_MACHINE_EV("pump_machine_ev", 1500);


        private final String name;
        private final int targetVacuum;

        PumpType(String name, int targetVacuum) {
            this.name = name;
            this.targetVacuum = targetVacuum;
        }

        @Nonnull
        @Override
        public String getName() {
            return this.name;
        }

        public int getTargetVacuum() {
            return targetVacuum;
        }

    }
}
