package tekcays_addon.common.metatileentities.single.heaters;

import static gregtech.api.unification.material.Materials.Coke;
import static tekcays_addon.gtapi.utils.FuelWithProperties.CALCITE;
import static tekcays_addon.gtapi.utils.HeatersMethods.getBurnTime;
import static tekcays_addon.gtapi.utils.HeatersMethods.isThereEnoughLiquidFuel;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
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
import tekcays_addon.gtapi.capability.impl.HeatContainer;
import tekcays_addon.gtapi.metatileentity.FuelHeater;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.utils.FuelHeaterTiers;

public class MetaTileEntityFluidizedHeater extends FuelHeater implements IDataInfoProvider, IActiveOutputSide {

    protected IFluidTank importFluidTank;

    private final Item SOLID_FUEL = OreDictUnifier.get(OrePrefix.dustTiny, Coke).getItem();

    public MetaTileEntityFluidizedHeater(ResourceLocation metaTileEntityId, FuelHeaterTiers fuelHeater) {
        super(metaTileEntityId, fuelHeater);
        this.heatIncreaseRate = setHeatIncreaseRate(32);
        this.importFluidTank = new NotifiableFluidTank(1000, this, false);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityFluidizedHeater(metaTileEntityId, fuelHeater);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.heatContainer = new HeatContainer(this, 0, 20 * heatIncreaseRate);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        this.importFluidTank = new NotifiableFluidTank(1000, this, false);
        return new FluidTankList(false, importFluidTank);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(new SlotWidget(importItems, 0, 20, 50, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT))
                .widget(new SlotWidget(exportItems, 0, 80, 50, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT))
                .widget(new TankWidget(importFluidTank, 50, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        TKCYATextures.FLUIDIZED_FUEL_HEATER.renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                isBurning(), true);
    }

    private boolean isThereCoke() {
        return importItems.getStackInSlot(0).getItem().equals(SOLID_FUEL);
    }

    @Override
    protected void tryConsumeNewFuel() {
        IFluidTank fuelFluidTank = importFluids.getTankAt(0);
        if (isThereEnoughLiquidFuel(fuelFluidTank, CALCITE) && isThereCoke()) {
            fuelFluidTank.drain(CALCITE.getFluidStack().amount, true);
            importItems.extractItem(0, 1, false);
            setBurnTimeLeft(getBurnTime(CALCITE, fuelHeater));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.fluidized_fuel_heater.tooltip"));
        super.addInformation(stack, player, tooltip, advanced);
    }
}
