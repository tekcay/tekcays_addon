package tekcays_addon.common.metatileentities.single.electric;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.common.metatileentities.storage.MetaTileEntityBuffer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.Logistic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityLogisticMachine extends TieredMetaTileEntity implements Logistic {

    protected IEnergyContainer energyContainer;
    private static final int TANK_SIZE = 16000;
    private final int tier;
    private FluidTankList fluidTankList;
    private ItemStackHandler itemStackHandler;

    public MetaTileEntityLogisticMachine(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier = tier;
        initializeInventory();
    }

    /**
     * Code from {@link MetaTileEntityBuffer}
     */
    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        FilteredFluidHandler[] fluidHandlers = new FilteredFluidHandler[tier];
        for (int i = 0; i < tier; i++) {
            fluidHandlers[i] = new FilteredFluidHandler(TANK_SIZE);
        }
        fluidInventory = fluidTankList = new FluidTankList(false, fluidHandlers);
        itemInventory = itemStackHandler = new ItemStackHandler((int)Math.pow(tier, 2));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLogisticMachine(metaTileEntityId, getTier());
    }

    /**
     * Code from {@link MetaTileEntityBuffer}
     */
    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int invTier = tier;
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND,
                176, Math.max(166, 18 + 18 * invTier + 94));//176, 166
        for (int i = 0; i < this.fluidTankList.getTanks(); i++) {
            builder.widget(new TankWidget(this.fluidTankList.getTankAt(i), 176 - 8 - 18, 18 + 18 * i, 18, 18)
                    .setAlwaysShowFull(true)
                    .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                    .setContainerClicking(true, true));
        }
        for (int y = 0; y < invTier; y++) {
            for (int x = 0; x < invTier; x++) {
                int index = y * invTier + x;
                builder.slot(itemStackHandler, index, 8 + x * 18, 18 + y * 18, GuiTextures.SLOT);
            }
        }
        return builder.label(6, 6, getMetaFullName())
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 18 + 18 * invTier + 12)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC) {
            return TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC.cast(this);
        }
        if (capability == TKCYATileCapabilities.CAPABILITY_FLUID_LOGISTIC) {
            return TKCYATileCapabilities.CAPABILITY_FLUID_LOGISTIC.cast(this);
        }

        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.logistic_machine.tooltip.description.1"));
        tooltip.add(I18n.format("tkcya.machine.logistic_machine.tooltip.cover_consumption", GTValues.V[this.tier - 1]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", super.energyContainer.getInputVoltage(), GTValues.VNF[getTier()]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity",super.energyContainer.getEnergyCapacity()));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("Inventory", itemStackHandler.serializeNBT());
        tag.setTag("FluidInventory", fluidTankList.serializeNBT());
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.itemStackHandler.deserializeNBT(tag.getCompoundTag("Inventory"));
        this.fluidTankList.deserializeNBT(tag.getCompoundTag("FluidInventory"));
    }
}
