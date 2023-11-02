package tekcays_addon.common.metatileentities.multi;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.MetaTileEntities;
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
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.metatileentity.multiblock.PressureContainerNoEnergyMultiblockController;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.metatileentity.predicates.BrickHatchesPredicates.*;

public class MetaTileEntityPrimitiveRoastOven extends PressureContainerNoEnergyMultiblockController {

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;
    private IPressureContainer pressureContainer;

    public MetaTileEntityPrimitiveRoastOven(ResourceLocation metaTileEntityId, BlockBrick.BrickType brickType) {
        super(metaTileEntityId, TKCYARecipeMaps.ROASTING);
        this.brick = brickType;
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveRoastOven(metaTileEntityId, brick);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.primitive_converter.tooltip.1"));
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
            double pressure = this.getPressureContainer().getPressure();
            if (pressure > 100000) textList.add(new TextComponentTranslation("tkcya.machine.text.pressure", String.format("%.3f", pressure/1000D) + " kPa"));
            if (pressure > 1000000) textList.add(new TextComponentTranslation("tkcya.machine.text.pressure", String.format("%.3f", pressure/1000000D) + " MPa"));
        }
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            updateLogic();
        }
    }

    private void updateLogic() {
        IPressureContainer pressureContainer = getAbilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER).get(0);
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

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.COKE_OVEN_OVERLAY;
    }

    protected IBlockState getCasingState() {
        return iBlockState;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("YYY", "YYY", "YYY")
                .aisle("YSY", "Y#Y", "YYY")
                .aisle("YYY", "YMY", "YYY")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()))
                .where('Y', states(getCasingState())
                        .or(getInputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(getOutputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(getInputBrickItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(getOutputBrickItemBus(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER).setMaxGlobalLimited(1, 1)))
                .where('M', getBrickMuffler(brick))
                .where('#', air())
                .build();
    }


}

