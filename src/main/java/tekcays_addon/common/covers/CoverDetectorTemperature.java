package tekcays_addon.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.render.TKCYATextures;


public class CoverDetectorTemperature extends CoverBehavior implements ITickable, CoverWithUI {

    private boolean isInverted;
    private int temperatureThreshold;
    private final int MAX_TEMPERATURE = 10_000;

    public CoverDetectorTemperature(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
        this.isInverted = false;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, null) != null;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        TKCYATextures.DETECTOR_TEMPERATURE.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }


    public void onScrewdriverSneakClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {

        if (this.isInverted) {
            this.setInverted();
            playerIn.sendMessage(new TextComponentTranslation("tkcya.cover.temperature_detector.message_temperature_storage_normal"));
        } else {
            this.setInverted();
            playerIn.sendMessage(new TextComponentTranslation("tkcya.cover.temperature_detector.message_temperature_storage_inverted"));
        }
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {

        if (!coverHolder.getWorld().isRemote) {
            if (playerIn.isSneaking()) {
                onScrewdriverSneakClick(playerIn, hand, hitResult);
                return EnumActionResult.SUCCESS;
            } else openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    private void adjustTemperatureThreshold(int amount) {
        setTemperatureThreshold(MathHelper.clamp(temperatureThreshold + amount, 1, MAX_TEMPERATURE));
    }

    private void setTemperatureThreshold(int temperatureThreshold) {
        this.temperatureThreshold = temperatureThreshold;
        coverHolder.markDirty();
    }

    protected String getUITitle() {
        return "Temperature Detector";
    }

    protected ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player) {
        return builder.build(this, player);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, getUITitle()));

        primaryGroup.addWidget(new ImageWidget(44, 20, 62, 20, GuiTextures.DISPLAY));

        primaryGroup.addWidget(new IncrementButtonWidget(136, 20, 30, 20, 1, 10, 100, 1000, this::adjustTemperatureThreshold)
                .setDefaultTooltip()
                .setShouldClientCallback(false));
        primaryGroup.addWidget(new IncrementButtonWidget(10, 20, 34, 20, -1, -10, -100, -1000, this::adjustTemperatureThreshold)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        TextFieldWidget2 textFieldWidget = new TextFieldWidget2(45, 26, 60, 20, () -> Integer.toString(temperatureThreshold), val -> setTemperatureThreshold(Integer.parseInt(val)))
                .setCentered(true)
                .setNumbersOnly(1, MAX_TEMPERATURE)
                .setMaxLength(5);
        primaryGroup.addWidget(textFieldWidget);

        //Prints Kelvin symbol
        primaryGroup.addWidget(new LabelWidget(115, 26, "K", TextFormatting.BLACK));

        //Prints the current temperature of the HeatContainer
        primaryGroup.addWidget(new LabelWidget(15, 46, String.format("Current temperature: %d K", getHeatContainerTemperature()), TextFormatting.BLACK));

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 184 + 82)
                .widget(primaryGroup)
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 7, 184);
        return buildUI(builder, player);
    }

    private void setInverted() {
        this.isInverted = !this.isInverted;
        if (!this.coverHolder.getWorld().isRemote) {
            this.coverHolder.writeCoverData(this, 100, b -> b.writeBoolean(this.isInverted));
            this.coverHolder.notifyBlockUpdate();
            this.coverHolder.markDirty();
        }
    }

    private int getHeatContainerTemperature() {
        return coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, EnumFacing.DOWN).getTemperature();
    }

    @Override
    public void update() {

        int currentTemp = getHeatContainerTemperature();
        if (currentTemp == 0) return;

        if (!isInverted) {
            if (currentTemp > temperatureThreshold) setRedstoneSignalOutput(15);
            else setRedstoneSignalOutput(0);
        } else {
            if (currentTemp > temperatureThreshold) setRedstoneSignalOutput(0);
            else setRedstoneSignalOutput(15);
        }
    }



    @Override
    public boolean canConnectRedstone() {
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("isInverted", this.isInverted);
        tagCompound.setInteger("temperatureThreshold", this.temperatureThreshold);
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.isInverted = tagCompound.getBoolean("isInverted");
        this.temperatureThreshold = tagCompound.getInteger("temperatureThreshold");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(this.isInverted);
        packetBuffer.writeInt(this.temperatureThreshold);
    }

    @Override
    public void readInitialSyncData(PacketBuffer packetBuffer) {
        this.isInverted = packetBuffer.readBoolean();
        this.temperatureThreshold = packetBuffer.readInt();
    }
}
