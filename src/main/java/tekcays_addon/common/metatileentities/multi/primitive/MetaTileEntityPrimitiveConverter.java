package tekcays_addon.common.metatileentities.multi.primitive;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.metatileentity.predicates.BrickHatchesPredicates.*;

import java.util.List;

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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;

public class MetaTileEntityPrimitiveConverter extends ModulableRecipeMapController {

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;

    public MetaTileEntityPrimitiveConverter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.PRIMITIVE_CONVERTING_RECIPES, LogicType.NO_ENERGY, LogicType.PRESSURE,
                LogicType.NO_MAINTENANCE);
        this.brick = BlockBrick.BrickType.REINFORCED_BRICK;
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveConverter(metaTileEntityId);
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.primitive_converter.tooltip.1"));
    }

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
            double pressure = this.getPressureContainer().getPressure();
            if (pressure > 100000) textList.add(new TextComponentTranslation("tkcya.machine.text.pressure",
                    String.format("%.3f", pressure / 1000D) + " kPa"));
            if (pressure > 1000000) textList.add(new TextComponentTranslation("tkcya.machine.text.pressure",
                    String.format("%.3f", pressure / 1000000D) + " MPa"));
        }
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.BRICKS[BlockBrick.REINFORCED_BRICK];
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
                .where('X', states(getCasingState()))
                .where('Y', states(getCasingState())
                        .or(getOutputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(getInputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER).setMaxGlobalLimited(1, 1)))
                .where('M', metaTileEntities(MetaTileEntities.MUFFLER_HATCH)
                        .or(getBrickMuffler(brick)))
                .where('#', air())
                .build();
    }
}
