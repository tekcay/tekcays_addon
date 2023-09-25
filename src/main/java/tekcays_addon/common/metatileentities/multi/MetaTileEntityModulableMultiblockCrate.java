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
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static gregtech.api.unification.material.Materials.TreatedWood;
import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.metatileentity.Predicates.isAir;
import static tekcays_addon.api.metatileentity.TankMethods.*;

public class MetaTileEntityModulableMultiblockCrate extends MultiblockWithDisplayBase {
    private final int capacity;
    private int actualCapacity;
    private Material material;

    public MetaTileEntityModulableMultiblockCrate(ResourceLocation metaTileEntityId, Material material, int capacity) {
        super(metaTileEntityId);
        this.material = material;
        this.capacity = capacity;
        this.actualCapacity = capacity;
        initializeInventory();
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        ItemStackHandler crate = createFilteredItemHandler(actualCapacity);

        this.importItems = crate;
        this.exportItems = crate;
        this.itemInventory = crate;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityModulableMultiblockCrate(metaTileEntityId, material, capacity);
    }

    @Override
    protected void updateFormedValid() {
     ;
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
                        .or(metaTileEntities(getValve(material)).setExactLimit(2)))
                .where(' ', air())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        int height = context.getOrDefault("modulableTankHeight", 1) + 1;
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
        return this.importItems.getStackInSlot(0).getCount() == 0;
    }

    public String getFillPercentage() {
       return isTankEmpty() ? "0% Filled"
                : BigDecimal.valueOf(100 * this.importItems.getStackInSlot(0).getCount())
               .divide(BigDecimal.valueOf(this.actualCapacity), 1, BigDecimal.ROUND_UP).toString()
               + "% Filled";
    }

    public String getCrateContent() {
        return isTankEmpty() ? "Empty"
                : this.importItems.getStackInSlot(0).getCount() / 1000
                + " k of "
                + this.importItems.getStackInSlot(0).getDisplayName();
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
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.content", getCrateContent()));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_crate.capacity", this.actualCapacity / 1000));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.fill.percentage", getFillPercentage()));
        }

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.multiblock.modulable_tank.tooltip"));
        tooltip.add(I18n.format("tkcya.machine.modulable_tank.capacity", capacity, "per layer."));
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
