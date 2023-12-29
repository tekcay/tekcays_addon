package tekcays_addon.common.metatileentities.single;

import static gregtech.api.capability.GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER;
import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static tekcays_addon.api.consts.NBTKeys.IS_RUNNING;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.gtapi.render.TKCYATextures.*;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import tekcays_addon.api.capability.AdjacentCapabilityHelper;
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.gtapi.capability.impl.RotationContainer;

public class MetaTileEntityDynamo extends MetaTileEntity
                                  implements IDataInfoProvider, AdjacentCapabilityHelper<IEnergyContainer> {

    private IRotationContainer rotationContainer;
    private IEnergyContainer energyContainer;
    private int baseTransferRate;
    private final int tier;
    private int transferRate;
    private boolean isRunning;
    private final long maxEnergyOutput;

    public MetaTileEntityDynamo(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier + 1;
        this.maxEnergyOutput = GTValues.V[tier] * 15 / 16;
        this.rotationContainer = new RotationContainer(this, 20 * this.tier, 100 * this.tier,
                2000 * this.tier * this.tier);
        super.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityDynamo(metaTileEntityId, tier - 1);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.VOLTAGE_CASINGS[tier];
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        DYNAMO_OVERLAY.renderOrientedState(renderState, translation, colouredPipeline, getFrontFacing(), isRunning,
                true);
    }

    private EnumFacing getRotationSide() {
        return getFrontFacing().getOpposite();
    }

    @Override
    public void update() {
        super.update();
        // Redstone stops fluid transfer
        if (this.isBlockRedstonePowered()) return;
        if (rotationContainer.getSpeed() == 0) return;

        if (!getWorld().isRemote) {
            energyContainer = getAdjacentCapabilityContainer(this);
            if (energyContainer != null) {
                transferRate = rotationContainer.getSpeed();
            } else {
                transferRate = 0;
                return;
            }
            energyContainer.addEnergy(transferRate);
        }
    }

    public void setRunningState(boolean isRunning) {
        this.isRunning = isRunning;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(IS_WORKING, buf -> buf.writeBoolean(isRunning));
        }
    }

    @Override
    public boolean isActive() {
        return this.isRunning;
    }

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing side) {
        if (capability == CAPABILITY_ROTATIONAL_CONTAINER && side == getRotationSide()) {
            return CAPABILITY_ROTATIONAL_CONTAINER.cast(rotationContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.dynamo.tooltip.1"));
        rotationContainer.addTooltip(tooltip);
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_out", maxEnergyOutput, GTValues.VNF[tier]));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.speed", rotationContainer.getSpeed()));
        return list;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean(IS_RUNNING, isRunning);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.isRunning = data.getBoolean(IS_RUNNING);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.isRunning);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isRunning = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == IS_WORKING) {
            this.isRunning = buf.readBoolean();
            scheduleRenderUpdate();
        }
    }

    // Implementations

    @Override
    public EnumFacing getOutputSide() {
        return getFrontFacing();
    }

    @Override
    public Capability<IEnergyContainer> getCapability() {
        return CAPABILITY_ENERGY_CONTAINER;
    }
}
