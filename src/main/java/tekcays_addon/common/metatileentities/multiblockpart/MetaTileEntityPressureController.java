package tekcays_addon.common.metatileentities.multiblockpart;

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
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
import tekcays_addon.api.capability.containers.IPressureControl;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.PressureControl;
import tekcays_addon.api.consts.DetectorModes;
import tekcays_addon.api.metatileentity.gui.MetaTileEntityGuiHandler;
import tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static tekcays_addon.api.consts.DetectorModes.*;
import static tekcays_addon.api.consts.UnitSymbol.BAR;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.api.utils.TKCYAValues.MAX_PRESSURE;

public class MetaTileEntityPressureController extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IPressureControl>, MetaTileEntityGuiHandler, IDataInfoProvider {

    private IPressureControl pressureControl;
    private long pressureThreshold;
    private long currentPressure;

    public MetaTileEntityPressureController(@Nonnull ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 1);
        this.pressureControl = new PressureControl(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureController(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
            this.currentPressure = pressureControl.getPressure();
            switch (getCurrentDetectorMode()) {
                case EQUAL:
                    if (currentPressure == getPressureThresholdInBar()) turnRedstoneSignalOn();
                    else turnRedstoneSignalOff();
                    break;
                case LOWER:
                    if (currentPressure < getPressureThresholdInBar()) turnRedstoneSignalOn();
                    else turnRedstoneSignalOff();
                    break;
                case HIGHER:
                    if (currentPressure > getPressureThresholdInBar()) turnRedstoneSignalOn();
                    else turnRedstoneSignalOff();
                    break;
                default:
            }
        }
    }

    private int getPressureThresholdInBar() {
        return (int) (this.pressureThreshold * ATMOSPHERIC_PRESSURE);
    }

    private void turnRedstoneSignalOn() {
        this.setOutputRedstoneSignal(getFrontFacing(), 12);
    }

    private void turnRedstoneSignalOff() {
        this.setOutputRedstoneSignal(getFrontFacing(), 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.CONVERTER_FE_IN.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    private boolean setDetectorModeAndSendMessage(DetectorModes detectorModes, EntityPlayer player) {
        this.pressureControl.setDetectorMode(detectorModes);
        player.sendMessage(new TextComponentTranslation("tkcya.machine.pressure_controller.message", detectorModes));
        return true;
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        switch (getCurrentDetectorMode()) {
            case EQUAL:
                return setDetectorModeAndSendMessage(LOWER, playerIn);
            case LOWER:
                return setDetectorModeAndSendMessage(HIGHER, playerIn);
            case HIGHER:
                return setDetectorModeAndSendMessage(EQUAL, playerIn);
            default:
                return false;
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.pressure_controller.tooltip.1"));
    }

    @Override
    public MultiblockAbility<IPressureControl> getAbility() {
        return TKCYAMultiblockAbility.PRESSURE_CONTROL;
    }

    @Override
    public void registerAbilities(List<IPressureControl> abilityList) {
        abilityList.add(pressureControl);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTROL)) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTROL.cast(pressureControl);
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUIHelper(entityPlayer);
    }

    @Override
    public String getUITitle() {
        return "Pressure Controller";
    }

    @Override
    public int getThreshold() {
        return (int) this.pressureThreshold;
    }

    @Override
    public void setThreshold(int threshold) {
        this.pressureThreshold = threshold;
    }

    @Override
    public void adjustThreshold(int amount) {
        setThreshold((int) MathHelper.clamp(pressureThreshold + amount, 0, MAX_PRESSURE));
    }

    @Override
    public String getCurrentMeasureText() {
        return "Current pressure: %d %s";
    }

    @Override
    public int getMaxMeasure() {
        return (int) MAX_PRESSURE;
    }

    @Override
    public int getContainerMeasure() {
        return (int) this.currentPressure;
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
        return BAR;
    }

    @Override
    public DetectorModes getCurrentDetectorMode() {
        return this.pressureControl.getDetectorMode();
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_pressure", currentPressure));
        list.add(new TextComponentTranslation("behavior.tricorder.pressureThreshold", pressureThreshold));
        return list;
    }
}
