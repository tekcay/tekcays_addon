package tekcays_addon.common.metatileentities.multi;

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
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockHermeticCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.items.TKCYAMetaItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.utils.MiscMethods.*;

public class MetaTileEntitySpiralSeparator extends RecipeMapMultiblockController {

    private int height;
    private final int ENERGY_COST = 120;

    public MetaTileEntitySpiralSeparator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.SPIRAL_SEPARATION);
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
                        .or(autoAbilities()))
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
        this.height = context.getOrDefault("blastFurnaceHeight", 1);
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

        int inputSlot = hasAcceptedItemStackInSlot(TKCYAMetaItems.DUST_MIXTURE.getStackForm(), inputInventory);
        if (inputSlot == -1) return;
        ItemStack input = inputInventory.getStackInSlot(inputSlot);
        NBTTagCompound nbt = input.getTagCompound();
        List<MaterialStack> list = getMaterialStacksFromString(nbt.getString("Composition"));

        List<ItemStack> outputStack = getItemStacksFromMaterialStacks(list, OrePrefix.dust);

        //To make sure the energy input hatch is at least MV or LV 4A
        if (energyContainer.getInputVoltage() < 128 || !(energyContainer.getInputVoltage() == 32 && energyContainer.getInputAmperage() >= 4)) return;
        if (!hasEnoughEnergy(ENERGY_COST, energyContainer)) return;
        if (!canOutputItem(outputStack, outputInventory)) return;

        energyContainer.removeEnergy(ENERGY_COST * 20);

        //It will take 4 seconds to perfom the recipe and thus output the items
        if (getOffsetTimer() % 80 != 0) return;
        doOutputItem(outputStack, outputInventory);
    }



}
