package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.ItemHandlerProxy;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;

import static gregtech.api.unification.material.Materials.Iron;

public class MetaTileEntityAlloyingCrucible extends RecipeMapMultiblockController {

    public MetaTileEntityAlloyingCrucible(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES);
    }

    protected IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Iron).getBlock(Iron);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.PRIMITIVE_BRICKS;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TKCYATextures.ALLOYING_CRUCIBLE_OVERLAY;
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        ItemStackHandler emptyHandler = new ItemStackHandler(0);
        this.itemInventory = new ItemHandlerProxy(emptyHandler, emptyHandler);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXX", "XXXX", "XXXX", " XX ")
                .aisle("XXXX", "XFFX", "X##X", " XX ")
                .aisle("XXXX", "YFFX", "X##X", " XX ")
                .where('X', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS)))
                .where('F', states(getFrameState()))
                .where('#', air())
                .where(' ', any())
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveMelter(metaTileEntityId);
    }

    public boolean hasMaintenanceMechanics() {
        return false;
    }
}
