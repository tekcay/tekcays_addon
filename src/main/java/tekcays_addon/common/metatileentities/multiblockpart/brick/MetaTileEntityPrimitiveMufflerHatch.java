package tekcays_addon.common.metatileentities.multiblockpart.brick;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

import gregtech.api.capability.IMufflerHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMufflerHatch;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.render.TKCYATextures;

public class MetaTileEntityPrimitiveMufflerHatch extends MetaTileEntityMufflerHatch {

    private final int recoveryChance;
    private final ItemStackHandler inventory;
    private final BlockBrick.BrickType brick;

    public MetaTileEntityPrimitiveMufflerHatch(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, 0);
        this.recoveryChance = 30;
        this.inventory = new ItemStackHandler(1);
        this.brick = brick;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveMufflerHatch(metaTileEntityId, brick);
    }

    @Override
    public MultiblockAbility<IMufflerHatch> getAbility() {
        return TKCYAMultiblockAbility.BRICK_MUFFLER_HATCH;
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return TKCYATextures.BRICKS[brick.getTextureId()];
    }

    public BlockBrick.BrickType getBrick() {
        return this.brick;
    }
}
