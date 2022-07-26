package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapMultiblockNoEnergyController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

public class MetaTileEntityPrimitiveFermenter extends RecipeMapMultiblockNoEnergyController {

    public MetaTileEntityPrimitiveFermenter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.FERMENTATION_RECIPES);
    }

    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && recipeMapWorkable.isActive() ? 15 : 0;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.WOOD_WALL;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.ASSEMBLER_OVERLAY;
    }


    protected IBlockState getCasingState() {
        return MetaBlocks.STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.WOOD_WALL);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "S#X", "XXX")
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState())
                        .or(autoAbilities()))
                .where('#', air())
                .build();

    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveFermenter(metaTileEntityId);
    }

    public boolean hasMaintenanceMechanics() {
        return false;
    }
    public boolean hasMufflerMechanics() {
        return false;
    }
}

