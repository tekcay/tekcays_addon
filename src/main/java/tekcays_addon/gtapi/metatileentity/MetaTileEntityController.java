package tekcays_addon.gtapi.metatileentity;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.consts.DetectorModes;
import tekcays_addon.api.detectors.ControllerDetectorWrapper;
import tekcays_addon.api.detectors.DetectorControllerHelper;
import tekcays_addon.api.gui.CoverGuiHandler;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.impl.ContainerDetector;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static tekcays_addon.api.consts.DetectorModes.changeDetectModeAndSendMessage;
import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_PRESSURE;

public class MetaTileEntityController extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IContainerDetector>, CoverGuiHandler, IDataInfoProvider, DetectorControllerHelper {

    @Getter
    @Setter
    private int threshold;
    private IContainerDetector containerControl;
    private int currentValue;
    private final ControllerDetectorWrapper wrapper;

    public MetaTileEntityController(@Nonnull ResourceLocation metaTileEntityId, ControllerDetectorWrapper wrapper) {
        super(metaTileEntityId, 1);
        this.wrapper = wrapper;
        this.containerControl = new ContainerDetector(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityController(metaTileEntityId, wrapper);
    }

    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
            updateRedstoneBehavior(getCurrentDetectorMode(), currentValue, threshold, this::setOutputRedstoneSignal);
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        wrapper.getTextures().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!getWorld().isRemote) {
            containerControl.setDetectorMode(changeDetectModeAndSendMessage(containerControl.getDetectorMode(), playerIn));
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.pressure_controller.tooltip.1"));
    }

    @Override
    public MultiblockAbility<IContainerDetector> getAbility() {
        return TKCYAMultiblockAbility.CONTAINER_CONTROL;
    }

    @Override
    public void registerAbilities(List<IContainerDetector> abilityList) {
        abilityList.add(this.containerControl);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(wrapper.getControllerCapability())) {
            return wrapper.getControllerCapability().cast(containerControl);
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUIHelper(entityPlayer);
    }

    @Override
    public String getUITitle() {
        return wrapper.getUiTitle();
    }

    @Override
    public void adjustThreshold(int amount) {
        setThreshold(MathHelper.clamp(threshold + amount, 0, MAX_PRESSURE));
    }

    @Override
    public String getCurrentMeasureText() {
        return wrapper.getCurrentMeasureText();
    }

    @Override
    public int getMaxMeasure() {
        return MAX_PRESSURE;
    }

    @Override
    public int getContainerMeasure() {
        return this.currentValue;
    }

    @Override
    public Supplier<String> convertThresholdToString() {
        return () -> Integer.toString(getThreshold());
    }

    @Override
    public Consumer<String> setThresholdFromString() {
        return val -> setThreshold(Integer.parseInt(val));
    }

    @Override
    public ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player) {
        return builder.build(this.getHolder(), player);
    }

    @Override
    public String getUnitSymbol() {
        return wrapper.getUnit();
    }

    @Override
    public DetectorModes getCurrentDetectorMode() {
        return this.containerControl.getDetectorMode();
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_pressure", currentValue));
        list.add(new TextComponentTranslation("behavior.tricorder.pressureThreshold", threshold));
        return list;
    }
}
