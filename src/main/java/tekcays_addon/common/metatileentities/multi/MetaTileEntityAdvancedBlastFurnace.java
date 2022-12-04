package tekcays_addon.common.metatileentities.multi;

import gregicality.science.api.GCYSValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.metatileentity.multiblock.HeatContainerNoEnergyMultiblockController;
import tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.api.recipes.recipeproperties.NoEnergyTemperatureProperty;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.ADVANCED_BLAST_FURNACE_RECIPES;
import static tekcays_addon.api.utils.BlastFurnaceUtils.*;

public class MetaTileEntityAdvancedBlastFurnace extends HeatContainerNoEnergyMultiblockController implements IHeatMachine, IDataInfoProvider{

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;
    public int height;
    private int maxTemp;
    private IHeatContainer heatContainer;
    private int blastFurnaceTemperature;
    private int currentHeat;
    private int currentTemp;
    private final int HEAT_MULTIPLIER = 24;
    private final int HEAT_DROP = 10000;
    private boolean startedRecipe;

    public MetaTileEntityAdvancedBlastFurnace(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, ADVANCED_BLAST_FURNACE_RECIPES);
        this.brick = brick;
        this.maxTemp = brick.getBrickTemperature();
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
        //this.recipeMapWorkable = new AdvancedBlastFurnaceLogic(this);
        //this.heatContainer = new HeatContainer(this, 0, 200000, maxTemp);
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
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        int recipeTemp = recipe.getProperty(NoEnergyTemperatureProperty.getInstance(), 0);
        return currentTemp >= recipeTemp;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.3", "2"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4", brick.getBrickTemperature()));
        tooltip.add(I18n.format("tekcays_addon.machine.temperature_explode.tooltitp"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure"))
                    .setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            //Display temperature
            textList.add(new TextComponentTranslation("tkcya.temperature.display", currentTemp));

            //textList.add(new TextComponentTranslation("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4", this.getBrick().getBrickTemperature()));
            textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                    new TextComponentTranslation(GTUtility.formatNumbers(blastFurnaceTemperature) + "K").setStyle(new Style().setColor(TextFormatting.RED))));
            if (this.recipeMapWorkable.getParallelLimit() != 1) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.parallel", this.recipeMapWorkable.getParallelLimit()));
            }
            if (!this.recipeMapWorkable.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
            } else if (this.recipeMapWorkable.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                int currentProgress = (int) (this.recipeMapWorkable.getProgressPercent() * 100.0D);


                textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
            }
        }
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.BRICKS[brick.getTextureId()];
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.COKE_OVEN_OVERLAY;
    }

    protected IBlockState getCasingState() {
        return iBlockState;
    }

    public BlockBrick.BrickType getBrick() {
        return this.brick;
    }

    //Depends of the height
    private void actualizeTemperature() {
        heatContainer.setTemperature(GCYSValues.EARTH_TEMPERATURE + currentHeat / (HEAT_MULTIPLIER * height));
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            updateLogic();
        }
    }

    private void updateLogic() {

        heatContainer = getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER).get(0);

        currentTemp = heatContainer.getTemperature();
        currentHeat = heatContainer.getHeat();

        if (currentTemp >= maxTemp && TKCYAConfigHolder.machinesOptions.enableBlastFurnaceFireExplosion) {
            this.setOnFire(100);
            this.doExplosion(0.1f);
        }

        if (startedRecipe) {
            heatContainer.changeHeat(-HEAT_DROP * this.recipeMapWorkable.getParallelLimit());
            actualizeTemperature();
            startedRecipe = false;
            return;
        }

        if (this.recipeMapWorkable.isWorking() && this.recipeMapWorkable.getProgress() == 1) startedRecipe = true;
        actualizeTemperature();
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("#HHH#", "HHHHH", "HHHHH", "HHHHH", "#HHH#")
                .aisle("#CCC#", "CCCCC", "CCCCC", "CCCCC", "#CCC#")
                .aisle("#YYY#", "YYYYY", "XYYYX", "YYYYY", "#YYY#")
                .aisle("#YSY#", "Y###Y", "Y###Y", "Y###Y", "#YMY#")
                .aisle("#YYY#", "Y###Y", "Y#P#Y", "Y###Y", "#YYY#").setRepeatable(1, 11)
                .aisle("#YYY#", "YOOOY", "YOUOY", "YOOOY", "#YYY#")
                .where('S', selfPredicate())
                .where('H', abilities(TKCYAMultiblockAbility.HEAT_CONTAINER).setMinGlobalLimited(1).setMaxGlobalLimited(1).setPreviewCount(1).or(states(getCasingState())))
                .where('C', heatingCoils())
                .where('Y', states(getCasingState()))
                .where('X', states(getCasingState())
                        .or(getOutputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(getOutputBrickItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1)))
                .where('O', states(getCasingState())
                        .or(getInputBrickItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(getInputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                .where('P', heightIndicatorPredicate())
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('U', abilities(MultiblockAbility.MUFFLER_HATCH)
                        .or(getBrickMuffler(brick)))
                .where('#', air())
                .build();
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.height = data.getInteger("height");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.height);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.height = buf.readInt();
    }


    // This function is highly useful for detecting the length of this multiblock.
    public static TraceabilityPredicate heightIndicatorPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment("blastFurnaceHeight", 1);
                return true;
            } else
                return false;
        });
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        this.height = context.getOrDefault("blastFurnaceHeight", 1);
        this.recipeMapWorkable.setParallelLimit(height < 2 ? 1 : height * 2);

        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
        this.maxTemp = Math.min(brick.getBrickTemperature(), blastFurnaceTemperature);
        this.heatContainer = getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER).get(0);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedBlastFurnace(metaTileEntityId, brick);
    }

}

