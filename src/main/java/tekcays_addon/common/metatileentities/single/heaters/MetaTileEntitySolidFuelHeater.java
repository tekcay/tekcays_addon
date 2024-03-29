package tekcays_addon.common.metatileentities.single.heaters;

import static tekcays_addon.gtapi.utils.HeatersMethods.getBurnTime;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.gtapi.capability.impl.HeatContainer;
import tekcays_addon.gtapi.metatileentity.FuelHeater;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.utils.FuelHeaterTiers;

public class MetaTileEntitySolidFuelHeater extends FuelHeater implements IDataInfoProvider, IActiveOutputSide {

    private final int HEAT_BASE_INCREASE = 8;
    private final ItemStackHandler containerInventory;

    public MetaTileEntitySolidFuelHeater(ResourceLocation metaTileEntityId, FuelHeaterTiers fuelHeater) {
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
        this.heatContainer = new HeatContainer(this, 0, 20 * heatIncreaseRate);
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

                .widget(new SlotWidget(importItems, 0, 20, 50, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .widget(new SlotWidget(exportItems, 0, 80, 50, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        TKCYATextures.SOLID_FUEL_HEATER.renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                isBurning(), true);
    }

    @Override
    protected void tryConsumeNewFuel() {
        ItemStack fuelInSlot = importItems.extractItem(0, 1, true);
        if (fuelInSlot.isEmpty()) return;
        // Prevent consuming buckets with burn time
        if (FluidUtil.getFluidHandler(fuelInSlot) != null) {
            return;
        }
        // Calculate the burn time for this item taking into account the FuelHeater properties
        int burnTime = getBurnTime(fuelInSlot, fuelHeater);
        if (burnTime <= 0) return;
        importItems.extractItem(0, 1, false);
        ItemStack remainderAsh = ModHandler.getBurningFuelRemainder(fuelInSlot);
        if (!remainderAsh.isEmpty()) { // we don't care if we can't insert ash - it's chanced anyway
            exportItems.insertItem(0, remainderAsh, false);
        }
        setBurnTimeLeft(burnTime);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return fuelHeater.equals(FuelHeaterTiers.BRICK) ? TKCYATextures.BRICKS[BlockBrick.BRICK] :
                TKCYATextures.STEAM_CASING[fuelHeater.getTextureId()];
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.solid_fuel_heater.tooltip"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("ContainerInventory", containerInventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
    }

    @Override
    public boolean isAutoOutputItems() {
        return true;
    }
}
