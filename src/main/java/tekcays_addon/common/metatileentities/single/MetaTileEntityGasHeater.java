package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.metatileentity.FuelHeater;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.FuelHeaterTiers;
import tekcays_addon.api.utils.FuelWithProperties;
import tekcays_addon.common.items.TKCYAMetaItems;

import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.utils.FuelWithProperties.*;

public class MetaTileEntityGasHeater extends FuelHeater implements IDataInfoProvider, IActiveOutputSide {

    public MetaTileEntityGasHeater(ResourceLocation metaTileEntityId, FuelHeaterTiers fuelHeater) {
        super(metaTileEntityId, fuelHeater);
        this.heatIncreaseRate = setHeatIncreaseRate(8);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityGasHeater(metaTileEntityId, fuelHeater);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.importFluids = this.createImportFluidHandler();
        this.importItems = this.createImportItemHandler();
        this.exportFluids = this.createExportFluidHandler();
        this.heatContainer = new HeatContainer(this, 0, 20 * heatIncreaseRate);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(1, this, false);
    }
    @Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, new FluidTank(1000));
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(1000));
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(new SlotWidget(importItems, 0, 50, 50, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT))
                .widget(new TankWidget(importFluids.getTankAt(0), 20, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .widget(new TankWidget(exportFluids.getTankAt(0), 80, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        TKCYATextures.GAS_FUEL_HEATER.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isBurning(), true);
    }

    private boolean isThereGasCollector() {
        return importItems.extractItem(0, 1, true).isItemEqual(TKCYAMetaItems.GAS_COLLECTOR.getStackForm());
    }

    @Override
    protected void tryConsumeNewFuel() {
        IFluidTank fuelFluidTank = importFluids.getTankAt(0);

        FluidStack input = fuelFluidTank.getFluid();
        if (input == null) return;;

        FuelWithProperties fuelWithProperties = getFuelWithProperties(GAS_FUELS_BURNING, input);
        if (fuelWithProperties == null) return;

        FluidStack fs = fuelWithProperties.getFluidStack();
        fuelFluidTank.drain(fs.amount, true);
        setBurnTimeLeft(fuelWithProperties.getBurnTime());

        if (!isThereGasCollector()) return;
        exportFluids.getTankAt(0).fill(CarbonDioxide.getFluid(10), true);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.gas_fuel_heater.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.gas_fuel_heater.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("BurnTimeLeft", burnTimeLeft);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.burnTimeLeft= data.getInteger("BurnTimeLeft");
        this.isBurning = burnTimeLeft > 0;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isBurning);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isBurning = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == IS_WORKING) {
            this.isBurning = buf.readBoolean();
            scheduleRenderUpdate();
        }
    }

}
