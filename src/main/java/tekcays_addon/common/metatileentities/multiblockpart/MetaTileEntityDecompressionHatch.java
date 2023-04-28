package tekcays_addon.common.metatileentities.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import tekcays_addon.common.metatileentities.multi.MetaTileEntityPressurizedMultiblockTank;
import tekcays_addon.gtapi.capability.containers.IDecompression;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_DECOMPRESSION_CONTAINER;

public class MetaTileEntityDecompressionHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IDecompression> {

    private final int transferRate;
    private IDecompression decompression;
    private final int tierMultiplier = getTier() * getTier() + 1;

    public MetaTileEntityDecompressionHatch(@Nonnull ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.transferRate = 10 * tierMultiplier;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityDecompressionHatch(metaTileEntityId, this.getTier());
    }

    @Nullable
    private IPressureContainer getPressureContainer() {
        MultiblockControllerBase controller = super.getController();
        if (controller instanceof MetaTileEntityPressurizedMultiblockTank) {
            return ((MetaTileEntityPressurizedMultiblockTank) controller).getPressureContainer();
        }
        return null;
    }

    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
            if (!getWorld().isRemote && getOffsetTimer() % 5 == 0L && isAttachedToMultiBlock()) {

                TileEntity tileEntity = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
                if (tileEntity == null) {
                    return;
                }

                if (!tileEntity.hasCapability(CAPABILITY_DECOMPRESSION_CONTAINER, getFrontFacing().getOpposite())) return;

                IPressureContainer pressureContainer = getPressureContainer();
                if (pressureContainer == null || pressureContainer.getPressurizedFluidStackAmount() == 0) return;

                FluidStack fluidStack = new FluidStack(pressureContainer.getPressurizedFluidStack().getFluid(), transferRate);

                IDecompression decompression = tileEntity.getCapability(CAPABILITY_DECOMPRESSION_CONTAINER, getFrontFacing().getOpposite());
                if (decompression == null || !decompression.isAbleToDecompress()) return;

                IFluidHandler fluidHandler = decompression.getFluidHandler();
                if (fluidHandler == null) return;

                int filledAmount = fluidHandler.fill(fluidStack, true);
                if (filledAmount == 0) {
                    decompression.setActivity(false);
                    return;
                }
                pressureContainer.changePressurizedFluidStack(fluidStack, -filledAmount);
                decompression.setActivity(true);
            }
        }
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.decompression_hatch.tooltip.1"));
        tooltip.add(I18n.format("tkcya.general.transfer_rate", transferRate));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public MultiblockAbility<IDecompression> getAbility() {
        return TKCYAMultiblockAbility.DECOMPRESSOR_CONTAINER;
    }

    @Override
    public void registerAbilities(@Nonnull List<IDecompression> list) {
        list.add(this.decompression);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(CAPABILITY_DECOMPRESSION_CONTAINER) && side.equals(getFrontFacing())) {
            return CAPABILITY_DECOMPRESSION_CONTAINER.cast(decompression);
        }
        return null;
    }
}
