package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import lombok.Getter;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import tekcays_addon.api.units.IPressureFormatting;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static gregtech.api.unification.material.Materials.Hydrogen;
import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.metatileentity.Predicates.isAir;
import static tekcays_addon.api.metatileentity.TankMethods.*;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.*;

public class MetaTileEntityPressurizedMultiblockTank extends MultiblockWithDisplayBase implements IPressureFormatting {

    private final Material material;
    private final int maxPressure;
    @Getter
    private IPressureContainer pressureContainer;

    public MetaTileEntityPressurizedMultiblockTank(ResourceLocation metaTileEntityId, Material material, int maxPressure) {
        super(metaTileEntityId);
        this.material = material;
        this.maxPressure = maxPressure;
        initializeInventory();
        initializeAbilities();
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
    }

    private String getPressureWithUnit(int pressure) {
        return convertPressureToBar(pressure, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPressurizedMultiblockTank(metaTileEntityId, material, maxPressure);
    }

    private void initializeAbilities() {
        List<IPressureContainer> list = getAbilities(PRESSURE_CONTAINER);
        this.pressureContainer = list.isEmpty() ? null : list.get(0);
    }

    @Override
    protected void updateFormedValid() {
        if (getOffsetTimer() % 20 == 0) {
            pressureContainer = getPressureContainer();
            if (pressureContainer != null) {
                //pressureContainer.changePressurizedFluidStack(Hydrogen.getFluid(1), 10);

                if (pressureContainer.getPressure() > maxPressure) {
                    this.explodeMultiblock(5);
                }
            }
        }
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XSX", "X X", "XXX")
                .aisle("XXX", "XIX", "XXX").setRepeatable(1,11)
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('I', isAir("height"))
                .where('X', states(getBlockState(material))
                        .or(abilities(PRESSURE_CONTAINER).setExactLimit(1))
                        .or(abilities(DECOMPRESSOR_CONTAINER).setExactLimit(1))
                        .or(abilities(CONTAINER_CONTROL).setMaxGlobalLimited(1)))
                .where(' ', air())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        if (this.pressureContainer != null) {
            this.pressureContainer.setVolume(context.getOrDefault("height", 1) + 1);
        }
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return getBaseTextureForTank(material);
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean isTankEmpty() {
        return this.pressureContainer == null || this.pressureContainer.getPressurizedFluidStackAmount() == 0;
    }

    private String getFillPercentage() {
       return isTankEmpty() ? "0% Filled"
                : BigDecimal.valueOf(100 * this.pressureContainer.getPressure())
               .divide(BigDecimal.valueOf(this.pressureContainer.getMaxPressure()), 1, BigDecimal.ROUND_UP).toString()
               + "% Filled";
    }

    private int getFluidStackAmount() {
        return isTankEmpty() ? 0 : this.pressureContainer.getPressurizedFluidStack().amount;
    }

    private String getTankContent() {
        return isTankEmpty() ? "Empty" :
                getPressureWithUnit(this.pressureContainer.getPressure())
                        + " of "
                + this.pressureContainer.getPressurizedFluidStackLocalizedName();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.MULTIBLOCK_TANK_OVERLAY;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {

        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure")).setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.content", getTankContent()));
            textList.add(new TextComponentTranslation("tkcya.general.pressure.tooltip.max_pressure", getPressureWithUnit(maxPressure)));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.fill.percentage", getFillPercentage()));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.bucket_equivalent", getFluidStackAmount()));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.multiblock.modulable_tank.tooltip"));
        tooltip.add(I18n.format("tkcya.general.pressure.tooltip.max_pressure", getPressureWithUnit(maxPressure)));
        tooltip.add(I18n.format("tkcya.multiblock.pressurized_tank.volume_layer"));
        tooltip.add(I18n.format("tkcya.multiblock.pressurized_tank.explosion"));
    }
}
