package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;

import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;


import javax.annotation.Nonnull;

public class MetaTileEntityCastingTable extends RecipeMapPrimitiveMultiblockController {

    private boolean boostAllowed;

    public MetaTileEntityCastingTable(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.CASTING_TABLE_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCastingTable(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("Y")
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.PRIMITIVE_BRICKS;
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

    /*
    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && recipeMapWorkable.isActive() ? 15 : 0;
    }

     */

    /*
    @Override
    protected FluidTankList createImportFluidHandler() { //TODO Not working properly
        this.airInputFluidTank = new FilteredFluidHandler(2000).setFillPredicate(MiscMethods::isAir);
        this.moltenInputFluidTank = new FilteredFluidHandler(2000).setFillPredicate(MiscMethods::isNotAir);
        return new FluidTankList(true, moltenInputFluidTank);
    }

     */



    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))

                .widget(new SlotWidget(importItems, 0, 52, 38, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .widget(new TankWidget(importFluids.getTankAt(0), 20, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .widget(new TankWidget(importFluids.getTankAt(1), 20, 25, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))

                .widget(new RecipeProgressWidget(recipeMapWorkable::getProgressPercent, 77, 39, 20, 15,
                        GuiTextures.PROGRESS_BAR_BATH, ProgressWidget.MoveType.HORIZONTAL, TKCYARecipeMaps.CASTING_TABLE_RECIPES))

                .widget(new SlotWidget(exportItems, 0, 104, 38, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
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
