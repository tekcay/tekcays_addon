package tekcays_addon.common.metatileentities.primitive;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.covers.molds.CoverMoldWrapper;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.covers.CoverMold;
import tekcays_addon.gtapi.capability.containers.IMoldCoverable;
import tekcays_addon.gtapi.capability.impl.MoldCoverable;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static codechicken.lib.fluid.FluidUtils.FLUID_HANDLER;
import static gregtech.api.GTValues.M;
import static gregtech.api.fluids.MetaFluids.getMaterialFromFluid;
import static gregtech.api.unification.material.Materials.Air;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_MOLD_COVERABLE;


public class MetaTileEntityCast extends MetaTileEntity implements IActiveOutputSide {

    protected TileEntity inputTe;
    private final IMoldCoverable moldCoverable;
    private final BlockBrick.BrickType brickType;
    private final int brickMaxTemp;
    private final int brickTier;
    private IFluidHandler adjacentFluidHandler;
    private int adjacentFluidTemp;
    private Material adjacentFluidMaterial;
    private CoverMold coverMold;
    

    public MetaTileEntityCast(ResourceLocation metaTileEntityId, int brickTier) {
        super(metaTileEntityId);
        this.brickTier = brickTier;
        this.brickType = BlockBrick.BrickType.values()[brickTier];
        this.brickMaxTemp = brickType.getBrickTemperature();
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

        if (exportItems.getStackInSlot(0).getCount() > 0 ) return;
        setAdjacentFluidHandler();
        if (adjacentFluidHandler == null) return;
        if (!setAdjacentFluidTemp()) return;

        if (adjacentFluidTemp > brickMaxTemp) {
            setOnFireAndDestroy();
            return;
        }

        if (!setMoldCover()) return;

        CoverMoldWrapper wrapper = coverMold.getWrapper();
        int neededFluidAmount = (int) (wrapper.getOutputPrefix().getMaterialAmount(Air) * GTValues.L / M);

        FluidStack fluidStack = adjacentFluidHandler.drain(neededFluidAmount, false);

        if (fluidStack == null) return;

        int coverMaxTemp = wrapper.getMaterial().getFluid().getTemperature();

        if (adjacentFluidTemp > coverMaxTemp) {
            setOnFireAndDestroy(coverMold);
            return;
        }

        if (wrapper.getOutputPrefix().doGenerateItem(adjacentFluidMaterial)) {

            ItemStack stack = OreDictUnifier.get(wrapper.getOutputPrefix(), adjacentFluidMaterial);

            exportItems.insertItem(0, stack, false);
            adjacentFluidHandler.drain(neededFluidAmount, true);
        }
    }

    protected void setOnFireAndDestroy() {
        this.setOnFire(10000);
        BlockPos MTEBlockPos = this.getPos();
        getWorld().destroyBlock(MTEBlockPos, false);
    }
    
    protected void setOnFireAndDestroy(CoverMold coverMold) {
        coverMold.destroyCover();
        setOnFireAndDestroy();
    }

    protected boolean setMoldCover() {
        CoverBehavior coverBehavior = getCoverAtSide(EnumFacing.UP);
        if ((coverBehavior instanceof CoverMold)) {
            coverMold = (CoverMold) coverBehavior;
            return true;
        }
        coverMold = null;
        return false;
    }
    
    protected boolean setAdjacentFluidTemp() {
        FluidStack fluidStack = adjacentFluidHandler.drain(1, false);
        if (fluidStack == null) {
            adjacentFluidTemp = 293;
            adjacentFluidMaterial = null;
            return false;
        }
        adjacentFluidMaterial = getMaterialFromFluid(fluidStack.getFluid());

        if (adjacentFluidMaterial == null) {
            adjacentFluidTemp = 293;
            return false;
        }
        adjacentFluidTemp = adjacentFluidMaterial.getFluid().getTemperature();
        return true;
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

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return TKCYATextures.BRICKS[brickType.getTextureId()];
    }

    protected void setAdjacentFluidHandler() {
        inputTe = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
        if (inputTe == null) {
            adjacentFluidHandler = null;
            return;
        }
        adjacentFluidHandler = inputTe.getCapability(FLUID_HANDLER, getFrontFacing().getOpposite());
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {

        if (capability.equals(CAPABILITY_MOLD_COVERABLE) && side.equals(UP)) {
            return CAPABILITY_MOLD_COVERABLE.cast(moldCoverable);
        }

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(exportItems);
        }
        return super.getCapability(capability, side);
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.PIPE_IN_OVERLAY.renderOriented(renderState, translation, pipeline, getFrontFacing());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCast(metaTileEntityId, brickTier);
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
