package tekcays_addon.common.metatileentities.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.PressureContainer;
import tekcays_addon.api.consts.DataIds;
import tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.consts.DataIds.AIR_FLUID_STACK;
import static tekcays_addon.api.consts.DataIds.PRESSURIZED_FLUID_STACK;
import static tekcays_addon.api.utils.TKCYAValues.*;

public class MetaTileEntityPressureHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IPressureContainer>, IDataInfoProvider {

    private final IPressureContainer pressureContainer;
    private final int leakingRate;
    private final boolean canHandleVacuum;
    private final int minPressure;
    private final int maxPressure;
    private final int tierMultiplier = getTier() * getTier() + 1;

    public MetaTileEntityPressureHatch(@Nonnull ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.canHandleVacuum = false;
        this.minPressure = ATMOSPHERIC_PRESSURE;
        this.maxPressure = ATMOSPHERIC_PRESSURE * 10 * tierMultiplier;
        this.leakingRate = - 10 * tierMultiplier;
        this.pressureContainer = new PressureContainer(this, minPressure, maxPressure);
        //this.volume = 1;
        //this.pressureContainer.initializeAirFluidStack();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureHatch(metaTileEntityId, this.getTier());
    }

    //Each second, it leaks
    @Override
    public void update() {
        super.update();
        //pressureContainer.setPressure();

            writeData(PRESSURIZED_FLUID_STACK);
            writeData(AIR_FLUID_STACK);
        if (getOffsetTimer() % 20 == 0) {
            TKCYALog.logger.info("air amount in MTEPressureHatch" + this.pressureContainer.getAirAmount());

            /*
            getPressureContainer().leaksContainer(leakingRate);
            */
            //getPressureContainer().setPressure();
        }

    }

    private void writeData(DataIds dataId) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag(dataId.getName(), getPressureContainer().setFluidStackNBT());
        writeCustomData(dataId.getId(), packetBuffer -> packetBuffer.writeCompoundTag(nbt));
        TKCYALog.logger.info("WroteData");
    }

    public IPressureContainer getPressureContainer() {
        return this.pressureContainer;
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
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.pressure_hatch.tooltip.pressure", pressureContainer.convertPressureToBar(maxPressure)));
        tooltip.add(I18n.format("tkcya.machine.pressure_hatch.tooltip.pressure.leak", Math.abs(leakingRate)));
    }

    @Override
    public MultiblockAbility<IPressureContainer> getAbility() {
        return TKCYAMultiblockAbility.PRESSURE_CONTAINER;
    }

    @Override
    public void registerAbilities(@Nonnull List<IPressureContainer> list) {
        list.add(this.pressureContainer);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER) && side.equals(getFrontFacing())) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(pressureContainer);
        }
        return null;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.fluid.amount", pressureContainer.getFluidAmount()));
        list.add(new TextComponentTranslation("behavior.tricorder.air.amount", pressureContainer.getAirAmount()));
        list.add(new TextComponentTranslation("behavior.tricorder.pressure.pressure", pressureContainer.convertPressureToBar(pressureContainer.getPressure())));
        //list.add(new TextComponentTranslation("behavior.tricorder.min_pressure.pressure", pressureContainer.convertPressureToBar(minPressure)));
        //list.add(new TextComponentTranslation("behavior.tricorder.max_pressure.pressure", pressureContainer.convertPressureToBar(maxPressure)));
        return list;
    }

}
