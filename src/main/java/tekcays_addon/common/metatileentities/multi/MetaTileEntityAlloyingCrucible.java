package tekcays_addon.common.metatileentities.multi;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapNoEnergyMultiblockController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;

public class MetaTileEntityAlloyingCrucible extends RecipeMapNoEnergyMultiblockController {

    public MetaTileEntityAlloyingCrucible(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES);
    }
    
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAlloyingCrucible(metaTileEntityId);
    }


    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.PRIMITIVE_BRICKS;
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS);
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TKCYATextures.ALLOYING_CRUCIBLE_OVERLAY;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "Y#X", "X#X")
                .aisle("XXX", "XXX", "XXX")
                .where('X', states(getCasingState()).setMinGlobalLimited(9)
                        .or(autoAbilities()))
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }


}
