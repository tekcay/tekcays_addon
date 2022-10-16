package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
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
import net.minecraftforge.fluids.FluidTank;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.metatileentity.FuelHeater;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.FuelHeaterTiers;

import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static gregtech.api.unification.material.Materials.Calcite;
import static tekcays_addon.api.utils.FuelWithProperties.CREOSOTE;
import static tekcays_addon.api.utils.HeatersMethods.getBurnTime;

public class MetaTileEntityLiquidFuelHeater extends FuelHeater implements IDataInfoProvider, IActiveOutputSide {

    private FluidTank fuelFluidTank;

    public MetaTileEntityLiquidFuelHeater(ResourceLocation metaTileEntityId, FuelHeaterTiers fuelHeater) {
        super(metaTileEntityId, fuelHeater);
        this.heatIncreaseRate = setHeatIncreaseRate(8);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityLiquidFuelHeater(metaTileEntityId, fuelHeater);
    }


    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.importFluids = this.createImportFluidHandler();
        this.heatContainer = new HeatContainer(this, 0, 20000);
    }


    @Override
    protected FluidTankList createImportFluidHandler() {
        FluidTankList superHandler = super.createImportFluidHandler();
        this.fuelFluidTank = new FilteredFluidHandler(1000);
        fuelFluidTank.setFluid(Calcite.getFluid(0));
        return new FluidTankList(false, superHandler, fuelFluidTank);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(new TankWidget(importFluids.getTankAt(0), 20, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        TKCYATextures.LIQUID_FUEL_HEATER.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isBurning(), true);
    }

    @Override
    protected void tryConsumeNewFuel() {
        if (fuelFluidTank.getFluid() != null &&
                fuelFluidTank.getFluid().isFluidEqual(CREOSOTE.getFluidStack()) &&
                        fuelFluidTank.getFluidAmount() >= CREOSOTE.getFluidStack().amount) {
            fuelFluidTank.drain(CREOSOTE.getFluidStack(), true);
            setBurnTimeLeft(getBurnTime(CREOSOTE, fuelHeater));
        }
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.liquid_fuel_heater.tooltip"));
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
