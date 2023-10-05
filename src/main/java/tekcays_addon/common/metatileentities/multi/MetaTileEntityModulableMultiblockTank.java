package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.PropertyFluidFilter;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static gregtech.api.unification.material.Materials.TreatedWood;
import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.metatileentity.Predicates.isAir;
import static tekcays_addon.api.metatileentity.TankMethods.*;

public class MetaTileEntityModulableMultiblockTank extends MultiblockWithDisplayBase {
    private final int capacity;
    private int actualCapacity;
    private final Material material;
    private FilteredFluidHandler tank;

    public MetaTileEntityModulableMultiblockTank(ResourceLocation metaTileEntityId, Material material, int capacity) {
        super(metaTileEntityId);
        this.material = material;
        this.capacity = capacity;
        this.actualCapacity = capacity;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityModulableMultiblockTank(metaTileEntityId, material, capacity);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        tank = new FilteredFluidHandler(this.capacity);
        tank.setFilter(new PropertyFluidFilter(340, false, false, false, false));
        this.exportFluids = this.importFluids = new FluidTankList(true, tank);
        this.fluidInventory = tank;
    }

    @Override
    protected void updateFormedValid() {
        tank.setCapacity(actualCapacity);
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
                .where('I', isAir("modulableTankHeight"))
                .where('X', states(getBlockState(material))
                        .or(metaTileEntities(getTankValve(material)).setExactLimit(2)))
                .where(' ', air())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        int height = context.getOrDefault("modulableTankHeight", 0) + 1;
        this.actualCapacity = this.capacity * height;
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
        return this.importFluids.getTankAt(0).drain(Integer.MAX_VALUE, false) == null;
    }

    public String getFillPercentage() {
       return isTankEmpty() ? "0% Filled"
                : BigDecimal.valueOf(100 * this.importFluids.getTankAt(0).getFluidAmount())
               .divide(BigDecimal.valueOf(this.actualCapacity), 1, BigDecimal.ROUND_UP).toString()
               + "% Filled";
    }

    public String getTankContent() {
        return isTankEmpty() ? "Empty"
                : this.importFluids.getTankAt(0).getFluidAmount() / 1000
                + " kL of "
                + this.importFluids.getTankAt(0).getFluid().getLocalizedName();
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
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.capacity", this.actualCapacity / 1000));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.fill.percentage", getFillPercentage()));
        }

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.multiblock.modulable_tank.tooltip"));
        tooltip.add(I18n.format("tkcya.machine.modulable_tank.capacity", actualCapacity, "per layer."));
        if (material.equals(TreatedWood)) {
            tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature", 340));
        } else {
            FluidPipeProperties pipeProperties = material.getProperty(PropertyKey.FLUID_PIPE);
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature", pipeProperties.getMaxFluidTemperature()));
                if (pipeProperties.isAcidProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.acid_proof"));
            } else {
                tooltip.add(I18n.format("gregtech.tooltip.fluid_pipe_hold_shift"));
            }
        }
    }
}
