package tekcays_addon.common.metatileentities.single.electric;

import static codechicken.lib.inventory.InventoryUtils.ITEM_HANDLER;
import static gregtech.api.util.GTTransferUtils.insertItem;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import tekcays_addon.api.metatileentity.TransferMetaTileEntity;

public class MetaTileEntityElectricConveyor extends TransferMetaTileEntity {

    private final int TRANSFER_RATE = (int) Math.pow((getTier() + 1), 4);

    public MetaTileEntityElectricConveyor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityElectricConveyor(metaTileEntityId, getTier());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        IVertexOperation[] coloredPipeline = ArrayUtils.add(pipeline, multiplier);

        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.ITEM_HATCH_INPUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
        Textures.ITEM_HATCH_OUTPUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
    }

    @Override
    public void update() {
        if (getOffsetTimer() % 20 == 0) super.update();
    }

    @Override
    protected boolean tryTransfer() {
        if (!getTileEntities()) return false;

        IItemHandler inputItemHandler = inputTe.getCapability(ITEM_HANDLER, getFrontFacing().getOpposite());
        if (inputItemHandler == null) return false;

        IItemHandler outputItemHandler = outputTe.getCapability(ITEM_HANDLER, getOutputSide().getOpposite());
        if (outputItemHandler == null) return false;

        return moveInventoryItems(inputItemHandler, outputItemHandler);
    }

    public boolean moveInventoryItems(IItemHandler sourceInventory, IItemHandler targetInventory) {
        for (int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, Integer.MAX_VALUE, true);
            if (sourceStack.isEmpty()) {
                continue;
            }
            ItemStack remainder = insertItem(targetInventory, sourceStack, true);
            int itemsAmount = sourceStack.getCount() - remainder.getCount();
            if (itemsAmount > 0) {
                sourceStack = sourceInventory.extractItem(srcIndex, Math.min(itemsAmount, TRANSFER_RATE), false);
                insertItem(targetInventory, sourceStack, false);
            }
        }
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.electric_conveyor.tooltip.1", TRANSFER_RATE));
        tooltip.add(I18n.format("tkcya.machine.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
        tooltip.add(
                I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
    }
}
