package tekcays_addon.common.metatileentities.multi;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.items.TKCYAMetaItems;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.utils.MiscMethods.*;

public class MetaTileEntitySpiralSeparator extends RecipeMapMultiblockController {

    private int height;
    private final int ENERGY_COST = 120;
    private int inputSlot;
    private boolean isRunning;
    private int numberOutputs;
    private final OrePrefix PREFIX_OUTPUT = OrePrefix.dustTiny;
    private final long REQUIRED_VOLTAGE = GTValues.V[GTValues.MV];

    private List<ItemStack> outputStackPerSec = new ArrayList<>();
    private String compositionData;


    public MetaTileEntitySpiralSeparator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.SPIRAL_SEPARATION);
        isRunning = false;
        compositionData = "";
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySpiralSeparator(metaTileEntityId);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("F#F", "###", "F#F")
                .aisle("AMA", "AAA", "AAA")
                .aisle("SSS", "S#S", "SSS")
                .aisle("SSS", "SHS", "SSS").setRepeatable(1, 9)
                .aisle("CCC", "CIC", "CCC")
                .where('A', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)))
                .where('M', selfPredicate())
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('H', heightIndicatorPredicate())
                .where('F', states(getFrameState()))
                .where('C', states(getCasingState()))
                .where('S', states(getSpiralState()))
                .where('#', air())
                .build();
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return true;
    }

    // This function is highly useful for detecting the length of this multiblock.
    public static TraceabilityPredicate heightIndicatorPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment("spiralHeight", 1);
                return true;
            } else
                return false;
        });
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.WHITE_GT;
    }

    protected IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL);
    }

    protected IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    protected IBlockState getSpiralState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.MONEL_CASING);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        this.height = context.getOrDefault("spiralHeight", 1);
        isRunning = false;
        numberOutputs = 9;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (this.isStructureFormed()) textList.add(new TextComponentTranslation("tkcya.machine.height.tooltip", height));
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return false;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.CRACKING_UNIT_OVERLAY;
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();
        //Refresh only once per second
        if (getOffsetTimer() % 20 != 0) return;
        if (!isRunning) {
            inputSlot = hasAcceptedItemStackInSlot(TKCYAMetaItems.DUST_MIXTURE.getStackForm(), inputInventory);

            //If input == -1, it does not have the sought ItemStack
            if (inputSlot == -1) return;
        }

        if (!isRunning) compositionData = getComposition(inputInventory, inputSlot);
        if (!isRunning) outputStackPerSec = setOutputStackPerSec(compositionData, PREFIX_OUTPUT);

        //Check if there is enough energy and enough room in the output
        if (!canDoWork(energyContainer, REQUIRED_VOLTAGE, ENERGY_COST, outputStackPerSec, outputInventory)) return;

        //Remove input ItemStack
        if (!isRunning) inputInventory.extractItem(inputSlot, 1, false);

        //Drain energy
        energyContainer.removeEnergy(ENERGY_COST * 20);
        isRunning = true;

        GTTransferUtils.addItemsToItemHandler(outputInventory, false, outputStackPerSec);

        //doOutputItem(outputStackPerSec, outputInventory);

        outputStackPerSec.forEach(itemStack -> TKCYALog.logger.info("change ? " + itemStack.getCount()));
        TKCYALog.logger.info("numberOutputs : " + numberOutputs);
        numberOutputs --;

        if (numberOutputs == 0) {
            //Just to reset to number of tinyDust in a normal Dust
            numberOutputs = 9;
            isRunning = false;
            compositionData = "";
        }
    }


    ////Data
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setString("composition", this.compositionData);
        data.setInteger("numberOutputs", this.numberOutputs);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.compositionData = data.getString("composition");
        this.numberOutputs = data.getInteger("numberOutputs");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeString(this.compositionData);
        buf.writeInt(this.numberOutputs);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.numberOutputs = buf.readInt();
        this.compositionData = buf.readStringFromBuffer(120);
    }



}
