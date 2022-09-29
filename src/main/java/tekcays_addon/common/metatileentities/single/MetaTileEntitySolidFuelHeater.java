package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.ItemFuelInfo;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.metatileentity.FuelHeater;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static tekcays_addon.api.utils.HeatersMethods.getBurnTime;

public class MetaTileEntitySolidFuelHeater extends FuelHeater implements IDataInfoProvider, IActiveOutputSide, IFuelable {

    private final int HEAT_BASE_INCREASE = 8;
    private final ItemStackHandler containerInventory;

    public MetaTileEntitySolidFuelHeater(ResourceLocation metaTileEntityId, tekcays_addon.api.utils.FuelHeater fuelHeater) {
        super(metaTileEntityId, fuelHeater);
        this.heatIncreaseRate = setHeatIncreaseRate(HEAT_BASE_INCREASE);
        this.containerInventory = new ItemStackHandler(2);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntitySolidFuelHeater(metaTileEntityId, fuelHeater);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.importItems = this.createImportItemHandler();
        this.exportItems = this.createExportItemHandler();
        this.heatContainer = new HeatContainer(this, 0, 20000);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
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

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        TKCYATextures.SOLID_FUEL_HEATER.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isBurning(), true);
    }

    @Override
    protected void tryConsumeNewFuel() {
        ItemStack fuelInSlot = importItems.extractItem(0, 1, true);
        if (fuelInSlot.isEmpty()) return;
        // Prevent consuming buckets with burn time
        if(FluidUtil.getFluidHandler(fuelInSlot) != null) {
            return;
        }
        //Calculate the burn time for this item taking into account the FuelHeater properties
        int burnTime = getBurnTime(fuelInSlot, fuelHeater);
        if (burnTime <= 0) return;
        importItems.extractItem(0, 1, false);
        ItemStack remainderAsh = ModHandler.getBurningFuelRemainder(fuelInSlot);
        if (!remainderAsh.isEmpty()) { //we don't care if we can't insert ash - it's chanced anyway
            exportItems.insertItem(0, remainderAsh, false);
        }
        setBurnTimeLeft(burnTime);
    }


    //For TOP, needs to implement IFuelable
    @Override
    public Collection<IFuelInfo> getFuels() {
        TKCYALog.logger.info("TOP got here");
        ItemStack fuelInSlot = importItems.extractItem(0, Integer.MAX_VALUE, true);
        TKCYALog.logger.info("TOP : stackSize : " + fuelInSlot.getCount());
        if (fuelInSlot == ItemStack.EMPTY)
            return Collections.emptySet();
        final int fuelRemaining = fuelInSlot.getCount();
        TKCYALog.logger.info("TOP : fuelInSlot = " + fuelInSlot);
        final int fuelCapacity = importItems.getSlotLimit(0);
        TKCYALog.logger.info("TOP : fuelRemaining = " + fuelRemaining);
        final long burnTime = (long) fuelRemaining * getBurnTime(fuelInSlot, fuelHeater);
        TKCYALog.logger.info("TOP : fuelCapacity = " + fuelCapacity);
        return Collections.singleton(new ItemFuelInfo(fuelInSlot, fuelRemaining, fuelCapacity, 1, burnTime));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("BurnTimeLeft", burnTimeLeft);
        data.setTag("ContainerInventory", containerInventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.burnTimeLeft= data.getInteger("BurnTimeLeft");
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
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

    @Override
    public boolean isAutoOutputItems() {
        return true;
    }

}
