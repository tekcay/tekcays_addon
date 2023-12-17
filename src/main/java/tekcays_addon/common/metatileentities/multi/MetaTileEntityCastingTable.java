package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;

import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;


import javax.annotation.Nonnull;

import static tekcays_addon.api.metatileentity.predicates.BrickCastingBusPredicates.getBrickCastingBus;
import static tekcays_addon.api.metatileentity.predicates.BrickCastingBusPredicates.getBrickCastingFluidInput;

public class MetaTileEntityCastingTable extends RecipeMapPrimitiveMultiblockController {

    private boolean boostAllowed;
    private final IBlockState iBlockState;
    private final BlockBrick.BrickType brick;

    public MetaTileEntityCastingTable(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, TKCYARecipeMaps.CASTING_TABLE_RECIPES);
        this.brick = brick;
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCastingTable(metaTileEntityId, brick);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CFC")
                .aisle("CBC").setRepeatable(0, 10)
                .aisle("CYC")
                .where('Y', selfPredicate())
                .where('B', states(getCasingState()))
                .where('F', getBrickCastingFluidInput(brick))
                .where('C', getBrickCastingBus(brick)
                        .or(any()))
                .build();
    }

    protected IBlockState getCasingState() {
        return iBlockState;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.BRICKS[brick.getTextureId()];
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_BLAST_FURNACE_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }


}
