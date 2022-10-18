package tekcays_addon.common.metatileentities.multiblockpart;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nullable;
import java.util.List;

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

    public BlockBrick.BrickType getBrick() {
        return this.brick;
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return TKCYATextures.BRICKS[brick.getTextureId()];
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
