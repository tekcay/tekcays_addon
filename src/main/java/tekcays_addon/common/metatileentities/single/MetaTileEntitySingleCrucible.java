package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.impl.SingleBlockPrimitiveRecipeLogic;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.recipeproperties.NoEnergyTemperatureProperty;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntitySingleCrucible extends MetaTileEntity{

    protected IHeatContainer heatContainer;
    protected final CrucibleRecipeLogic workable;
    private final BlockBrick.BrickType brickType;
    private final int maxParallel = 8;
    private final int maxTemp;
    private int currentTemp;
    private int currentHeat;

    public MetaTileEntitySingleCrucible(ResourceLocation metaTileEntityId, BlockBrick.BrickType brickType) {
        super(metaTileEntityId);
        this.brickType = brickType;
        this.maxTemp = brickType.getBrickTemperature();
        this.workable = new CrucibleRecipeLogic(this, TKCYARecipeMaps.MELTER_RECIPES);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntitySingleCrucible(metaTileEntityId, brickType);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.exportFluids = this.createExportFluidHandler();
        this.importItems = this.createImportItemHandler();
        this.heatContainer = new HeatContainer(this, 0, 20000);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(2, this, false);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        this.exportFluids = new FluidTankList(false, new FluidTank(1000));
        return new FluidTankList(false, exportFluids);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .slot(this.importItems, 0, 30, 50, GuiTextures.SLOT)
                .slot(this.importItems, 1, 50, 50, GuiTextures.SLOT)
                .widget(new TankWidget(exportFluids.getTankAt(0), 90, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public int getActualComparatorValue() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return TKCYATextures.BRICKS[0];
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.max_parallel.tooltip", maxParallel));
        tooltip.add(I18n.format("tkcya.parallel.tooltip"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4", maxTemp));

    }

    @Override
    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(getBaseRenderer().getParticleSprite(), getPaintingColorForRendering());
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
    }



    @Override
    public void update() {
        super.update();
    }

    private class CrucibleRecipeLogic extends SingleBlockPrimitiveRecipeLogic {
        private CrucibleRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
            setParallelLimit(maxParallel);
        }

        @Override
        protected boolean checkRecipe(@Nonnull Recipe recipe) {
            if (!recipe.hasProperty(NoEnergyTemperatureProperty.getInstance())) return false;
            int recipeTemp = recipe.getProperty(NoEnergyTemperatureProperty.getInstance(), 0);
            return recipeTemp < maxTemp && recipeTemp <= currentTemp;
        }
    }

}
