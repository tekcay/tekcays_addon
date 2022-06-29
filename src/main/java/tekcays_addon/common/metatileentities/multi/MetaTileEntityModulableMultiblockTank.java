package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityModulableMultiblockTank extends MultiblockWithDisplayBase {

    private final boolean isMetal;
    private final int capacity;
    private int actualCapacity, height;

    public MetaTileEntityModulableMultiblockTank(ResourceLocation metaTileEntityId, boolean isMetal, int capacity) {
        super(metaTileEntityId);
        this.isMetal = isMetal;
        this.capacity = capacity;
        //initializeAbilities();
    }

    protected void initializeAbilities() {
        this.importFluids = new FluidTankList(true, makeFluidTanks());
        this.exportFluids = importFluids;
        this.fluidInventory = new FluidHandlerProxy(this.importFluids, this.exportFluids);
    }

    @Nonnull
    private List<FluidTank> makeFluidTanks() {
        List<FluidTank> fluidTankList = new ArrayList<>(1);
        fluidTankList.add(new FilteredFluidHandler(capacity).setFillPredicate(
                fluidStack -> isMetal || (!fluidStack.getFluid().isGaseous() && fluidStack.getFluid().getTemperature() <= 325)
        ));
        return fluidTankList;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityModulableMultiblockTank(metaTileEntityId, isMetal, capacity);
    }

    @Override
    protected void updateFormedValid() {
     ;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XSX", "X X", "XXX")
                .aisle("XXX", "XIX", "XXX").setRepeatable(1,11)
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('I', isIndicatorPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(23)
                        .or(metaTileEntities(getValve()).setMaxGlobalLimited(2)))
                .where(' ', air())
                .build();
    }


    // This function is highly useful for detecting the length of this multiblock. FROM GTFO

    public static TraceabilityPredicate isIndicatorPredicate() {

        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment("modulableTankHeight", 1);
                return true;
            } else
                return false;
        });
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        this.height = context.getOrDefault("modulableTankHeight", 1);
        this.actualCapacity = this.capacity * this.height;
    }


    private IBlockState getCasingState() {
        if (isMetal)
            return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
        return MetaBlocks.STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.WOOD_WALL);
    }

    private MetaTileEntity getValve() {
        if (isMetal)
            return MetaTileEntities.STEEL_TANK_VALVE;
        return MetaTileEntities.WOODEN_TANK_VALVE;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (isMetal)
            return Textures.SOLID_STEEL_CASING;
        return Textures.WOOD_WALL;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!isStructureFormed())
            return false;
        return super.onRightClick(playerIn, hand, facing, hitResult);
    }

    public int getFillPercentage() {
       return (int) (100.0D * this.importFluids.drain(Integer.MAX_VALUE, false).amount / this.actualCapacity);
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
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("actualCapacity", this.actualCapacity);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.actualCapacity = data.getInteger("actualCapacity");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.actualCapacity);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.actualCapacity = buf.readInt();
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {

        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure")).setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.content", fluidInventory.drain(Integer.MAX_VALUE, false)));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.capacity", this.actualCapacity));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.fill.percentage", getFillPercentage()));
        }

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.multiblock.modulable_tank.tooltip"));
        tooltip.add(I18n.format("tkcya.machine.modulable_tank.capacity", capacity, "per layer."));
    }
}
