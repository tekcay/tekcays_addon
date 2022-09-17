package tekcays_addon.common.metatileentities.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.utils.blastfurnace.BlastFurnaceUtils.getBrickTexture;

public class MetaTileEntityBrickItemBus extends MetaTileEntityItemBus {

    private final BlockBrick.BrickType brick;

    public MetaTileEntityBrickItemBus(ResourceLocation metaTileEntityId, boolean isExportHatch, BlockBrick.BrickType brick) {
        super(metaTileEntityId, 0, isExportHatch);
        this.brick = brick;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBrickItemBus(metaTileEntityId, isExportHatch, brick);
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return getBrickTexture(brick);
    }

    private int getInventorySize() {
        return 1;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        if (this.isExportHatch)
            tooltip.add(I18n.format("gregtech.machine.item_bus.export.tooltip"));
        else
            tooltip.add(I18n.format("gregtech.machine.item_bus.import.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.item_storage_capacity", getInventorySize()));
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }
}
