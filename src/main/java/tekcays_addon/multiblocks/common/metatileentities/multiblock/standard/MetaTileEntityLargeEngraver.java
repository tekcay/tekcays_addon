package tekcays_addon.multiblocks.common.metatileentities.multiblock.standard;

import tekcays_addon.multiblocks.api.metatileentity.TKCYARecipeMapMultiblockController;
import tekcays_addon.multiblocks.api.render.TKCYATextures;
import tekcays_addon.multiblocks.common.block.TKCYAMetaBlocks;
import tekcays_addon.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MetaTileEntityLargeEngraver extends TKCYARecipeMapMultiblockController {

    public MetaTileEntityLargeEngraver(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.LASER_ENGRAVER_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityLargeEngraver(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "XXGXX", "XXGXX", "XXXXX")
                .aisle("XXXXX", "XAAAX", "XAAAX", "XCCCX")
                .aisle("XXXXX", "GATAG", "GAPAG", "XCXCX")
                .aisle("XXXXX", "XAAAX", "XAAAX", "XCCCX")
                .aisle("XXSXX", "XXGXX", "XXGXX", "XXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(50)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('P', states(getCasingState2()))
                .where('G', states(getCasingState3()))
                .where('C', states(getCasingState4()))
                .where('T', tieredCasing().or(air()))
                .where('A', air())
                .build();
    }

    private IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ENGRAVER_CASING);
    }

    private IBlockState getCasingState2() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    private IBlockState getCasingState3() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    private IBlockState getCasingState4() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return TKCYATextures.ENGRAVER_CASING;
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return TKCYATextures.LARGE_ENGRAVER_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}
