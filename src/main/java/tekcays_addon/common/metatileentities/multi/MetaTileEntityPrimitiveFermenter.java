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
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.gtapi.logic.NoEnergyMultiblockLogic;
import tekcays_addon.gtapi.metatileentity.multiblock.NoEnergyRecipeMapMultiBlockController;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetaTileEntityPrimitiveFermenter extends NoEnergyRecipeMapMultiBlockController {

    public MetaTileEntityPrimitiveFermenter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.FERMENTATION_RECIPES);
        this.recipeMapWorkable = new NoEnergyMultiblockLogic(this);
        this.recipeMapWorkable.setParallelLimit(32);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.WOOD_WALL;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.ASSEMBLER_OVERLAY;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.primitive_fermenter.tooltip.1", "32"));
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

