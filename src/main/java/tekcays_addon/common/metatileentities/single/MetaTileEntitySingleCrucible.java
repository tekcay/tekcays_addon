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
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import tekcays_addon.api.capability.containers.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.logic.SingleBlockPrimitiveRecipeLogic;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.recipeproperties.NoEnergyTemperatureProperty;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.utils.TKCYAValues.ROOM_TEMPERATURE;

public class MetaTileEntitySingleCrucible extends MetaTileEntity implements IDataInfoProvider {

    protected IHeatContainer heatContainer;
    protected final CrucibleRecipeLogic workable;
    private final BlockBrick.BrickType brick;
    //private final int maxParallel = 8; //TODO
    private final int maxTemp;
    private boolean onChange;
    private int recipeTemp;
    /**
     * currentTemp = currentHeat / (numberItem * heatMultiplier);
     */
    private int currentTemp;
    private int currentHeat;
    private final int HEAT_MULTIPLIER = 24;
    private final int HEAT_DROP = 10000;
    private final int HEAT_COOL = -100;
    /**
     * requiredHeat = numberItem * recipeTemp * heatMultiplier;
     */
    //private int requiredHeat;
    //private int numberItem;


    public MetaTileEntitySingleCrucible(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId);
        this.brick = brick;
        this.maxTemp = brick.getBrickTemperature();
        this.workable = new CrucibleRecipeLogic(this, TKCYARecipeMaps.MELTER_RECIPES);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntitySingleCrucible(metaTileEntityId, brick);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.exportFluids = this.createExportFluidHandler();
        this.importItems = this.createImportItemHandler();
        this.heatContainer = new HeatContainer(this, 0, 200000, maxTemp);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(1, this, false);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, new FluidTank(1000));
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
                //.slot(this.importItems, 1, 50, 50, GuiTextures.SLOT)
                .widget(new TankWidget(exportFluids.getTankAt(0), 90, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        //GetCapabilityHelper.getCapability(EnumFacing.DOWN, side, TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, heatContainer)
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(heatContainer);
        } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            IItemHandler itemHandler = importItems;
            if (itemHandler.getSlots() > 0) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
            }
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            IFluidHandler fluidHandler = exportFluids;
            if (fluidHandler.getTankProperties().length > 0) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
            }
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.single_crucible.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.single_crucible.tooltip.2"));
        tooltip.add(I18n.format("tkcya.machine.single_crucible.tooltip.3"));
        tooltip.add(I18n.format("tkcya.machine.single_crucible.tooltip.4"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4", maxTemp));
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return TKCYATextures.BRICKS[brick.getTextureId()];
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
        Textures.PIPE_OUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_heat", heatContainer.getHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_heat", heatContainer.getMinHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_heat", heatContainer.getMaxHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.currentTemp", currentTemp));
        return list;
    }

    @Override
    public int getActualComparatorValue() {
        float f = maxTemp == 0L ? 0.0f : currentTemp / (maxTemp * 1.0f);
        return MathHelper.floor(f * 14.0f) + (currentTemp > 0 ? 1 : 0);
    }
    
    private void actualizeTemperature() {
        heatContainer.setTemperature(ROOM_TEMPERATURE + currentHeat / HEAT_MULTIPLIER);
    }

    @Override
    public void update() {
        super.update();
        int previousHeat = currentHeat;
        currentTemp = heatContainer.getTemperature();
        currentHeat = heatContainer.getHeat();
        pushFluidsIntoNearbyHandlers(getFrontFacing());

        //Loses heat over time if no more heat is provided
        if (getOffsetTimer() % 20 == 0) {
            if (previousHeat == currentHeat && currentTemp > ROOM_TEMPERATURE) {
                heatContainer.changeHeat(HEAT_COOL);
            }
        }

        if (currentTemp >= maxTemp) {
            this.setOnFire(100);
            this.doExplosion(0.1f);
        }

        if (currentHeat == 0) {
            if (currentTemp > ROOM_TEMPERATURE) {
                if (getOffsetTimer() % 20 == 0) currentTemp -= 1;
            } else heatContainer.setTemperature(ROOM_TEMPERATURE);
           return;
        }

       if (onChange) {
           heatContainer.changeHeat(-HEAT_DROP);
           actualizeTemperature();
           onChange = false;
           return;
       }

       if (this.workable.isWorking() && this.workable.getProgress() == 1) onChange = true;
        actualizeTemperature();
    }


    private class CrucibleRecipeLogic extends SingleBlockPrimitiveRecipeLogic {
        private CrucibleRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
            //setParallelLimit(maxParallel); //TODO
        }

        @Override
        public boolean checkRecipe(@Nonnull Recipe recipe) {
            if (!recipe.hasProperty(NoEnergyTemperatureProperty.getInstance())) return false;
            recipeTemp = recipe.getProperty(NoEnergyTemperatureProperty.getInstance(), 0);
            //numberItem = recipe.getInputs().size();
            //requiredHeat = numberItem * recipeTemp * HEAT_MULTIPLIER;
            return recipeTemp <= currentTemp;
            //return recipeTemp < maxTemp && recipeTemp <= currentTemp;
        }
    }

}
