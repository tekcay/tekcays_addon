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
import tekcays_addon.api.block.IPumpMachineBlockStats;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
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


    public enum PumpType implements IStringSerializable, IPumpMachineBlockStats {

        PUMP_MACHINE_LV("pump_machine_lv", "Pump Machine LV", 10000, 32),
        PUMP_MACHINE_MV("pump_machine_mv", "Pump Machine MV", 5000, 128),
        PUMP_MACHINE_HV("pump_machine_hv", "Pump Machine HV", 2500, 512),
        PUMP_MACHINE_EV("pump_machine_ev", "Pump Machine EV", 1500, 2048);



        private final String name;
        private final String localizedName;
        private final int targetVacuum;
        private final int voltage;

        PumpType(String name, String localizedName, int targetVacuum, int voltage) {
            this.name = name;
            this.localizedName = localizedName;
            this.targetVacuum = targetVacuum;
            this.voltage = voltage;
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
        public int getTargetVacuum() {
            return targetVacuum;
        }

        @Override
        public int getVoltage() {
            return voltage;
        }



    }
}
