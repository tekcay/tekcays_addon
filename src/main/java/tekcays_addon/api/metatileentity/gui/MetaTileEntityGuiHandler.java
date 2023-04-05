package tekcays_addon.api.metatileentity.gui;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import tekcays_addon.api.consts.DetectorModes;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MetaTileEntityGuiHandler {

    String getUITitle();
    int getThreshold();
    void setThreshold(int threshold);
    void adjustThreshold(int amount);

    /**
     * Description of the current measure. Must finish by {@code < %d %s >}.
     * @return the description as a {@link String}
     */
    String getCurrentMeasureText();

    /**
     * The max value the threshold can reach.
     * @return this max value as an {@code int}.
     */
    int getMaxMeasure();

    /**
     * The current value of the container.
     * @return this value as an {@code int}.
     */
    int getContainerMeasure();

    Supplier<String> convertThresholdToString();
    Consumer<String> setThresholdFromString();
    ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player);
    String getUnitSymbol();
    DetectorModes getCurrentDetectorMode();

    default ModularUI createUIHelper(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, getUITitle()));

        primaryGroup.addWidget(new ImageWidget(44, 20, 62, 20, GuiTextures.DISPLAY));

        primaryGroup.addWidget(new IncrementButtonWidget(136, 20, 30, 20, 1, 10, 100, 1000, this::adjustThreshold)
                .setDefaultTooltip()
                .setShouldClientCallback(false));
        primaryGroup.addWidget(new IncrementButtonWidget(10, 20, 34, 20, -1, -10, -100, -1000, this::adjustThreshold)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        TextFieldWidget2 textFieldWidget = new TextFieldWidget2(45, 26, 60, 20, convertThresholdToString(), setThresholdFromString())
                .setCentered(true)
                .setNumbersOnly(1, getMaxMeasure())
                .setMaxLength(5);
        primaryGroup.addWidget(textFieldWidget);

        //Prints the unit symbol
        primaryGroup.addWidget(new LabelWidget(115, 26, getUnitSymbol(), TextFormatting.BLACK));

        //Prints the current measure of the container
        primaryGroup.addWidget(new LabelWidget(15, 46, String.format(getCurrentMeasureText(), getContainerMeasure(), getUnitSymbol()), TextFormatting.BLACK));

        //Prints the current detector mode
        primaryGroup.addWidget(new LabelWidget(15, 66, DetectorModes.showDetectorModeText(getCurrentDetectorMode()), TextFormatting.BLACK));

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 104 + 82)
                .widget(primaryGroup)
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 7, 104);
        return buildUI(builder, player);
    }

}
