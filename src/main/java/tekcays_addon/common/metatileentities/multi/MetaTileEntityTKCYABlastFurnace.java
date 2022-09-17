package tekcays_addon.common.metatileentities.multi;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
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
import tekcays_addon.api.capability.impl.NoEnergyMultiblockLogic;
import tekcays_addon.api.metatileentity.multiblock.NoEnergyRecipeMapMultiBlockController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.recipeproperties.NoEnergyTemperatureProperty;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.utils.blastfurnace.BlastFurnaceUtils.*;

public class MetaTileEntityTKCYABlastFurnace extends NoEnergyRecipeMapMultiBlockController {

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;
    public int height;

    public MetaTileEntityTKCYABlastFurnace(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, TKCYARecipeMaps.BLASTING_RECIPES);
        this.brick = brick;
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
        this.recipeMapWorkable = new NoEnergyMultiblockLogic(this);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return brick.getBrickTemperature() >= recipe.getProperty(NoEnergyTemperatureProperty.getInstance(), 0);
    }
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.3", "2"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4", brick.getBrickTemperature()));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure"))
                    .setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
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
        return getBrickTexture(brick);
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
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

    protected TraceabilityPredicate getOutputFluidHatch(BlockBrick.BrickType brick) {
        return getOutputBrickFluidHatch(brick);
    }

    protected TraceabilityPredicate getInputItemBus(BlockBrick.BrickType brick) {
        return getInputBrickItemBus(brick);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("#YYY#", "YXXXY", "YXXXY", "YXXXY", "#YYY#")
                .aisle("#YSY#", "Y###Y", "Y###Y", "Y###Y", "#YYY#")
                .aisle("#YYY#", "Y###Y", "Y#H#Y", "Y###Y", "#YYY#").setRepeatable(1, 11)
                .aisle("#YYY#", "YOOOY", "YOMOY", "YOOOY", "#YYY#")
                .where('S', selfPredicate())
                .where('Y', states(getCasingState()))
                .where('X', states(getCasingState())
                        .or(getOutputFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1)))
                .where('O', states(getCasingState())
                        .or(getInputItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                .where('H', heightIndicatorPredicate())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
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
        this.recipeMapWorkable.setParallelLimit(height * 2);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityTKCYABlastFurnace(metaTileEntityId, brick);
    }

}

