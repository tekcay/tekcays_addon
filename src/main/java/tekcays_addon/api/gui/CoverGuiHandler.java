package tekcays_addon.api.gui;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import tekcays_addon.api.consts.DetectorModes;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static tekcays_addon.api.consts.DetectorModes.showDetectorModeText;

public interface CoverGuiHandler {

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

    default void displayDetectorMode(List<ITextComponent> textList) {
        textList.add(new TextComponentString(showDetectorModeText(getCurrentDetectorMode())));
    }

    default void displayCurrentValue(List<ITextComponent> textList) {
        textList.add(new TextComponentString(String.format(getCurrentMeasureText(), getContainerMeasure(), getUnitSymbol())));
    }

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
        primaryGroup.addWidget(new AdvancedTextWidget(15, 46, this::displayCurrentValue, 0));

        //Prints the current detector mode
        primaryGroup.addWidget(new AdvancedTextWidget(15, 66, this::displayDetectorMode, 0));

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 104 + 82)
                .widget(primaryGroup)
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 7, 104);
        return buildUI(builder, player);
    }

}
