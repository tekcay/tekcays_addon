package tekcays_addon.common.metatileentities.multi;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import tekcays_addon.gtapi.metatileentity.multiblock.NoEnergyRecipeMapMultiBlockController;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.recipes.recipeproperties.NoEnergyTemperatureProperty;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.metatileentity.predicates.BrickHatchesPredicates.*;

public class MetaTileEntityTKCYABlastFurnace extends NoEnergyRecipeMapMultiBlockController {

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;

    public MetaTileEntityTKCYABlastFurnace(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, TKCYARecipeMaps.BLASTING_RECIPES);
        this.brick = brick;
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return brick.getBrickTemperature() >= recipe.getProperty(NoEnergyTemperatureProperty.getInstance(), 0);
    }
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.1"));
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

    public BlockBrick.BrickType getBrick() {
        return this.brick;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("YYY", "YOY", "YYY")
                .aisle("YSY", "Y#Y", "YYY")
                .aisle("AIA", "IAI", "AIA")
                .aisle("AAA", "AMA", "AAA")
                .where('S', selfPredicate())
                .where('Y', states(getCasingState()))
                .where('O', getOutputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                .where('I', getInputBrickItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(2)
                        .or(states(getCasingState())))
                .where('M', getBrickMuffler(brick))
                .where('#', air())
                .where('A', any())
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityTKCYABlastFurnace(metaTileEntityId, brick);
    }
}

