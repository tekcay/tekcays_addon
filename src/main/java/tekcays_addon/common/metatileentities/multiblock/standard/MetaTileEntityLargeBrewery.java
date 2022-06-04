package tekcays_addon.common.metatileentities.multiblock.standard;

import tekcays_addon.api.metatileentity.TKCYARecipeMapMultiblockController;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.block.TKCYAMetaBlocks;
import tekcays_addon.common.block.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.block.blocks.BlockUniqueCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MetaTileEntityLargeBrewery extends TKCYARecipeMapMultiblockController {

    public MetaTileEntityLargeBrewery(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{RecipeMaps.BREWING_RECIPES, RecipeMaps.FERMENTING_RECIPES, RecipeMaps.FLUID_HEATER_RECIPES});
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityLargeBrewery(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("#XXX#", "#XXX#", "#XXX#", "#XXX#", "#####")
                .aisle("XXXXX", "XCCCX", "XAAAX", "XXAXX", "##X##")
                .aisle("XXXXX", "XCPCX", "XATAX", "XAPAX", "#XMX#")
                .aisle("XXXXX", "XCCCX", "XAAAX", "XXAXX", "##X##")
                .aisle("#XXX#", "#XSX#", "#XXX#", "#XXX#", "#####")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(50).or(autoAbilities(true, true, true, true, true, true, false)))
                .where('C', states(getCasingState2()))
                .where('P', states(getCasingState3()))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('T', tieredCasing().or(states(getCasingState3())))
                .where('A', air())
                .where('#', any())
                .build();
    }

    private IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.CORROSION_PROOF_CASING);
    }

    private IBlockState getCasingState2() {
        return TKCYAMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.MOLYBDENUM_DISILICIDE_COIL);
    }

    private IBlockState getCasingState3() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return TKCYATextures.CORROSION_PROOF_CASING;
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return TKCYATextures.LARGE_BREWERY_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }
}
