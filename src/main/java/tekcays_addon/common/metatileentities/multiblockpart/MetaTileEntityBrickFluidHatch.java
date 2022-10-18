package tekcays_addon.common.metatileentities.multiblockpart;

import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityBrickFluidHatch extends MetaTileEntityFluidHatch {

    private static final int INITIAL_INVENTORY_SIZE = 8000;

    private final FluidTank fluidTank;
    private final BlockBrick.BrickType brick;

    public MetaTileEntityBrickFluidHatch(ResourceLocation metaTileEntityId, boolean isExportHatch, BlockBrick.BrickType brick) {
        super(metaTileEntityId, 0, isExportHatch);
        this.fluidTank = new NotifiableFluidTank(INITIAL_INVENTORY_SIZE, this, isExportHatch);
        this.brick = brick;
        initializeInventory();
    }

    public BlockBrick.BrickType getBrick() {
        return this.brick;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBrickFluidHatch(metaTileEntityId, isExportHatch, brick);
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return TKCYATextures.BRICKS[brick.getTextureId()];
    }


    //To keep
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        if (this.isExportHatch)
            tooltip.add(I18n.format("gregtech.machine.fluid_hatch.export.tooltip"));
        else
            tooltip.add(I18n.format("gregtech.machine.fluid_hatch.import.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", INITIAL_INVENTORY_SIZE));
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }
}
