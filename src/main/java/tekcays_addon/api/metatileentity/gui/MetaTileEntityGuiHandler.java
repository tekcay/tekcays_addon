package tekcays_addon.api.metatileentity.gui;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MetaTileEntityGuiHandler {

    String getUITitle();
    int getThreshold();
    void setThreshold(int threshold);
    void adjustThreshold(int amount);

    /**
     * Must finish by {@code < %d %s >}.
     * @return
     */
    String getCurrentMeasureText();
    int getMaxMeasure();
    int getContainerMeasure();
    Supplier<String> convertThresholdToString();
    Consumer<String> setThresholdFromString();
    ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player);
    String getUnit();

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

        //Prints Kelvin symbol
        primaryGroup.addWidget(new LabelWidget(115, 26, getUnit(), TextFormatting.BLACK));

        //Prints the current temperature of the HeatContainer
        primaryGroup.addWidget(new LabelWidget(15, 46, String.format(getCurrentMeasureText(), getContainerMeasure(), getUnit()), TextFormatting.BLACK));

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 184 + 82)
                .widget(primaryGroup)
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 7, 184);
        return buildUI(builder, player);
    }

}
