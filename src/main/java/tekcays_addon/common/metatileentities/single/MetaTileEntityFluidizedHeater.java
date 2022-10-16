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
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.metatileentity.FuelHeater;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.FuelHeaterTiers;

import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static gregtech.api.unification.material.Materials.Coke;
import static tekcays_addon.api.utils.FuelWithProperties.CALCITE;
import static tekcays_addon.api.utils.HeatersMethods.getBurnTime;

public class MetaTileEntityFluidizedHeater extends FuelHeater implements IDataInfoProvider, IActiveOutputSide {

    private final Item SOLID_FUEL = OreDictUnifier.get(OrePrefix.dustTiny, Coke).getItem();
    private final int CALCITE_AMOUNT = CALCITE.getFluidStack().amount;

    public MetaTileEntityFluidizedHeater(ResourceLocation metaTileEntityId, FuelHeaterTiers fuelHeater) {
        super(metaTileEntityId, fuelHeater);
        this.heatIncreaseRate = setHeatIncreaseRate(8);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityFluidizedHeater(metaTileEntityId, fuelHeater);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.importFluids = this.createImportFluidHandler();
        this.importItems = this.createImportItemHandler();
        this.exportItems = this.createExportItemHandler();
        this.heatContainer = new HeatContainer(this, 0, 20000);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(1, this, false);
    }
    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
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
                .widget(new SlotWidget(importItems, 0, 50, 10, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT))
                .widget(new SlotWidget(exportItems, 0, 50, 60, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT))
                .widget(new TankWidget(importFluids.getTankAt(0), 20, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        TKCYATextures.FLUIDIZED_FUEL_HEATER.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isBurning(), true);
    }

    private boolean isThereEnoughCalcite(IFluidTank fuelFluidTank) {
        return fuelFluidTank.getFluid() != null &&
                fuelFluidTank.getFluid().isFluidEqual(CALCITE.getFluidStack()) &&
                fuelFluidTank.getFluidAmount() >= CALCITE_AMOUNT;
    }

    private boolean isThereCoke() {
        return importItems.getStackInSlot(0).getItem().equals(SOLID_FUEL);
    }

    @Override
    protected void tryConsumeNewFuel() {
        IFluidTank fuelFluidTank = importFluids.getTankAt(0);
        if (isThereEnoughCalcite(fuelFluidTank) && isThereCoke()) {
            fuelFluidTank.drain(CALCITE_AMOUNT, true);
            importItems.extractItem(0, 1, false);
            setBurnTimeLeft(getBurnTime(CALCITE, fuelHeater));
        }
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.fluidized_fuel_heater.tooltip"));
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
