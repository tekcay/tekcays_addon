package tekcays_addon.common.metatileentities.primitive;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.covers.molds.CoverMoldWrapper;
import tekcays_addon.common.covers.CoverMold;
import tekcays_addon.gtapi.capability.containers.IMoldCoverable;
import tekcays_addon.gtapi.capability.impl.MoldCoverable;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static codechicken.lib.fluid.FluidUtils.FLUID_HANDLER;
import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.Materials.Air;
import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.api.consts.NBTKeys.HIT_NUMBER;
import static gregtech.api.fluids.MetaFluids.getMaterialFromFluid;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_MOLD_COVERABLE;


public class MetaTileEntityCast extends MetaTileEntity implements IActiveOutputSide {

    private IBlockState upperBlock;
    private int hitNumber = 0;
    private BlockPos upperBlockPos;
    protected TileEntity inputTe;
    private IMoldCoverable moldCoverable;

    public MetaTileEntityCast(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.moldCoverable = new MoldCoverable(this);
        initializeInventory();
    }

    @Override
    public void initializeInventory() {
        super.initializeInventory();
        this.exportItems = this.createExportItemHandler();
    }

    @Override
    public void update() {
        super.update();

        if (this.getOffsetTimer() % 20 == 0) {

        TKCYALog.logger.info("There is {} items in exportItems", exportItems.getStackInSlot(0).getCount());

        if (exportItems.getStackInSlot(0).getCount() > 0 ) return;

        CoverBehavior coverBehavior = getCoverAtSide(EnumFacing.UP);
        if (coverBehavior == null) return;

        if (coverBehavior instanceof CoverMold) {
            CoverMoldWrapper wrapper = ((CoverMold) coverBehavior).getWrapper();
            int neededFluidAmount = (int) (wrapper.getOutputPrefix().getMaterialAmount(Air) * GTValues.L / M);

            IFluidHandler fluidHandler = getAdjacentFluidHandler();
            if (fluidHandler == null) return;

            FluidStack fluidStack = fluidHandler.drain(neededFluidAmount, false);

            if (fluidStack == null) return;

            Material material = getMaterialFromFluid(fluidStack.getFluid());

            ItemStack stack = OreDictUnifier.get(wrapper.getOutputPrefix(), material);

            exportItems.insertItem(0, stack, false);
            fluidHandler.drain(neededFluidAmount, true);
            TKCYALog.logger.info("final");
        }
        }
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

/*
    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (facing.equals(EnumFacing.UP))
            return super.onRightClick(playerIn, hand, facing, hitResult);

        if (!super.onRightClick(playerIn, hand, facing, hitResult)) {

            ItemStack itemStack = playerIn.getHeldItem(hand);
            if (!playerIn.isSneaking() || itemStack.isEmpty()) return true;

            Set<String> toolClasses = itemStack.getItem().getToolClasses(itemStack);
            if (toolClasses.isEmpty()) return true;
            ToolHelper.damageItem(itemStack, playerIn);

            upperBlockPos = getPos().offset(EnumFacing.UP);

            if (isUpFaceFree()) return true;
        }
        return true;
    }

 */

    private boolean isUpFaceFree() {
        World world = this.getWorld();
        upperBlock = world.getBlockState(upperBlockPos);
        return upperBlock.equals(Blocks.AIR.getDefaultState());
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return TKCYATextures.BRICKS[0];
    }

    @Nullable
    protected IFluidHandler getAdjacentFluidHandler() {
        inputTe = getWorld().getTileEntity(getPos().offset(getFrontFacing().getOpposite()));
        if (inputTe == null) return null;
        return inputTe.getCapability(FLUID_HANDLER, getFrontFacing());
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {

        if (capability.equals(CAPABILITY_MOLD_COVERABLE) && side.equals(UP)) {
            return CAPABILITY_MOLD_COVERABLE.cast(moldCoverable);
        }
        return super.getCapability(capability, side);
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.PIPE_IN_OVERLAY.renderOriented(renderState, translation, pipeline, getFrontFacing().getOpposite());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCast(metaTileEntityId);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return false;
    }
}
