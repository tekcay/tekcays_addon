package tekcays_addon.common.metatileentities.multi;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.metatileentity.predicates.BrickHatchesPredicates.*;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.gtapi.metatileentity.multiblock.ModulableRecipeMapController;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

public class MetaTileEntityTKCYACokeOven extends ModulableRecipeMapController {

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;

    public MetaTileEntityTKCYACokeOven(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.COKING, LogicType.NO_ENERGY, LogicType.NO_MAINTENANCE);
        this.brick = BlockBrick.BrickType.REINFORCED_BRICK;
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
        this.recipeMapWorkable.setParallelLimit(4);
    }

    /*
     * @Override
     * public void addInformation(@NotNull ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
     * boolean advanced) {
     * super.addInformation(stack, player, tooltip, advanced);
     * tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.1"));
     * tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.2"));
     * tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.3", "2"));
     * tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4", brick.getBrickTemperature()));
     * }
     * 
     */

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure"))
                    .setStyle((new Style()).setColor(TextFormatting.RED)
                            .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            if (this.recipeMapWorkable.getParallelLimit() != 1) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.parallel",
                        this.recipeMapWorkable.getParallelLimit()));
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
        return Textures.COKE_BRICKS;
    }

    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.COKE_OVEN_OVERLAY;
    }

    protected IBlockState getCasingState() {
        return iBlockState;
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("YYY", "YYY", "YYY")
                .aisle("YSY", "Y#Y", "YYY")
                .aisle("YYY", "YMY", "YYY")
                .where('S', selfPredicate())
                .where('Y', states(getCasingState())
                        .or(getOutputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(getOutputBrickItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(getInputBrickItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1)))
                .where('M', metaTileEntities(MetaTileEntities.MUFFLER_HATCH)
                        .or(getBrickMuffler(brick)))
                .where('#', air())
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityTKCYACokeOven(metaTileEntityId);
    }
}
