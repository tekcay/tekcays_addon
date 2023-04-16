package tekcays_addon.common.metatileentities.multi;

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
import org.lwjgl.input.Keyboard;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;
import tekcays_addon.gtapi.capability.list.HeatContainerList;
import tekcays_addon.gtapi.metatileentity.multiblock.HeatContainerNoEnergyMultiblockController;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.recipes.recipeproperties.NoEnergyTemperatureProperty;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.utils.TKCYAValues;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.ADVANCED_BLAST_FURNACE_RECIPES;

public class MetaTileEntityAdvancedBlastFurnace extends HeatContainerNoEnergyMultiblockController implements IHeatMachine, IDataInfoProvider{

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;
    public int height;
    private int maxTemp;
    private IHeatContainer heatContainer;
    private int blastFurnaceTemperature;
    private int currentHeat;
    private int currentTemp;
    private int heatMultiplier;
    private final int HEAT_BASE = 24;

    //TODO possibly to make it depend of heatMultiplier
    private final int HEAT_DROP = 5000;
    private final int PARALLEL_MULTIPLIER = 2;
    private boolean startedRecipe;

    public MetaTileEntityAdvancedBlastFurnace(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, ADVANCED_BLAST_FURNACE_RECIPES);
        this.brick = brick;
        this.maxTemp = brick.getBrickTemperature();
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.min_heat", heatContainer.getMinHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_heat", heatContainer.getMaxHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.current_heat", heatContainer.getHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.currentTemp", currentTemp));
        list.add(new TextComponentTranslation("behavior.tricorder.heatMultiplier", heatMultiplier));
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
        tooltip.add(I18n.format("tkcya.machine.parallel_ability.tooltip"));
        tooltip.add(I18n.format("tkcya.machine.advanced_blast_furnace.tooltip.1", PARALLEL_MULTIPLIER));
        tooltip.add(I18n.format("tkcya.machine.advanced_blast_furnace.tooltip.2", brick.getBrickTemperature()));
        tooltip.add(I18n.format("tekcays_addon.machine.temperature_explode.tooltitp"));
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add(I18n.format("tekcays_addon.machine.brick_density.tooltip"));
            tooltip.add(I18n.format("tekcays_addon.machine.heat_to_temperature.formula.tooltip", HEAT_BASE));
        } else {
            tooltip.add(I18n.format("gregtech.tooltip.hold_shift"));
        }
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

            //Display maxTemperature
            textList.add(new TextComponentTranslation("tkcya.max_temperature.display", maxTemp));

            /*
            //Display heat multiplier
            textList.add(new TextComponentTranslation("tkcya.heat_multiplier.display", this.heatMultiplier));

             */

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

    private void actualizeTemperature() {
        heatContainer.setTemperature(TKCYAValues.ROOM_TEMPERATURE + currentHeat / (heatMultiplier * height));
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();
        if (!getWorld().isRemote) {
            updateLogic();
        }
    }

    private void updateLogic() {
        heatContainer = new HeatContainerList(getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER));

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
                .aisle("AHHHA", "HHHHH", "HHHHH", "HHHHH", "AHHHA")
                .aisle("ACCCA", "CCCCC", "CCCCC", "CCCCC", "ACCCA")
                //.aisle("AYYYA", "YYYYY", "XYYYX", "YYYYY", "AYYYA")
                .aisle("AYSYA", "Y###Y", "Y###Y", "Y###Y", "AYMYA")
                .aisle("ACCCA", "C###C", "C#P#C", "C###C", "ACCCA").setRepeatable(1, 11)
                .aisle("AYYYA", "YOOOY", "YOUOY", "YOOOY", "AYYYA")
                .where('S', selfPredicate())
                .where('H', abilities(TKCYAMultiblockAbility.HEAT_CONTAINER))
                .where('C', heatingCoils())
                .where('Y', states(getCasingState()))
                .where('X', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setPreviewCount(1)))
                .where('O', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1)))
                .where('P', heightIndicatorPredicate())
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('U', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', air())
                .where('A', any())
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
        this.recipeMapWorkable.setParallelLimit(height < 2 ? 1 : height * PARALLEL_MULTIPLIER);

        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();

        this.maxTemp = Math.min(brick.getBrickTemperature(), this.blastFurnaceTemperature);
        this.heatMultiplier = HEAT_BASE * this.brick.getDensity() * this.blastFurnaceTemperature / 1000;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedBlastFurnace(metaTileEntityId, brick);
    }

}

