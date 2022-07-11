package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.fluids.MaterialFluid;
import gregtech.api.fluids.fluidType.FluidTypes;
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
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.input.Keyboard;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.unification.TKCYAMaterials;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TKCYAMetaTileEntityMultiblockTank extends MultiblockWithDisplayBase {

    private final Material material;
    private final IBlockState iBlockState;
    private final MetaTileEntity valve;
    private final int capacity;

    public TKCYAMetaTileEntityMultiblockTank(ResourceLocation metaTileEntityId, Material material, IBlockState iBlockState, MetaTileEntity valve, int capacity) {
        super(metaTileEntityId);
        this.material = material;
        this.iBlockState = iBlockState;
        this.valve = valve;
        this.capacity = capacity;
        initializeAbilities();
    }

    protected void initializeAbilities() {
        this.importFluids = new FluidTankList(true, makeFluidTanks());
        this.exportFluids = importFluids;
        this.fluidInventory = new FluidHandlerProxy(this.importFluids, this.exportFluids);
    }

    @Nonnull
    private List<FluidTank> makeFluidTanks() { //TODO
        List<FluidTank> fluidTankList = new ArrayList<>(1);
        FluidPipeProperties pipeProperties = material.getProperty(PropertyKey.FLUID_PIPE);
        fluidTankList.add(new FilteredFluidHandler(capacity).setFillPredicate(
                //fluidStack -> (!fluidStack.getFluid().isGaseous() && fluidStack.getFluid().getTemperature() <= 325)
                fluidStack -> ((fluidStack.getFluid().isGaseous() && pipeProperties.isGasProof() && fluidStack.getFluid().getTemperature() < material.getFluid().getTemperature())
                || ((((MaterialFluid) fluidStack.getFluid()).getFluidType().equals(FluidTypes.ACID) && pipeProperties.isAcidProof()) && fluidStack.getFluid().getTemperature() < material.getFluid().getTemperature())
                || (((MaterialFluid) fluidStack.getFluid()).getFluidType().equals(FluidTypes.PLASMA) && pipeProperties.isPlasmaProof()))
        ));
        return fluidTankList;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new TKCYAMetaTileEntityMultiblockTank(metaTileEntityId, material, iBlockState, valve, capacity);
    }

    @Override
    protected void updateFormedValid() {
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(23)
                        .or(metaTileEntities(getValve()).setMaxGlobalLimited(2)))
                .where(' ', air())
                .build();
    }

    private IBlockState getCasingState() {
        return iBlockState;
    }

    private MetaTileEntity getValve() {
        return valve;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (material.equals(Materials.TreatedWood)) return Textures.WOOD_WALL;
        if (material.equals(Materials.Steel)) return TKCYATextures.STEEL_GT;
        if (material.equals(TKCYAMaterials.GalvanizedSteel)) return TKCYATextures.WHITE_GT;
        return null;
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

    @Override
    protected ModularUI.Builder createUITemplate(@Nonnull EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
                .widget(new LabelWidget(6, 6, getMetaFullName()))
                .widget(new TankWidget(importFluids.getTankAt(0), 52, 18, 72, 61)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
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
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.multiblock.tank.tooltip"));
        tooltip.add(I18n.format("gregtech.machine.quantum_tank.capacity", capacity));
        if (material.equals(Materials.TreatedWood)) {
            tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature", 340));
            tooltip.add(I18n.format("gregtech.fluid_pipe.not_gas_proof"));
        } else {
            FluidPipeProperties pipeProperties = material.getProperty(PropertyKey.FLUID_PIPE);
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature", pipeProperties.getMaxFluidTemperature()));
                if (pipeProperties.isGasProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.gas_proof"));
                if (pipeProperties.isAcidProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.acid_proof"));
                if (pipeProperties.isCryoProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.cryo_proof"));
                if (pipeProperties.isPlasmaProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.plasma_proof"));
            } else {
                tooltip.add(I18n.format("gregtech.tooltip.fluid_pipe_hold_shift"));
            }
        }

        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("Fluid", Constants.NBT.TAG_COMPOUND)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag("Fluid"));
            if (fluidStack == null) return;
            tooltip.add(I18n.format("gregtech.machine.fluid_tank.fluid", fluidStack.amount, I18n.format(fluidStack.getUnlocalizedName())));
        }
    }
}
