package tekcays_addon.gtapi.capability.containers;

import net.minecraft.client.resources.I18n;

import java.util.List;

public interface ISteamConsumer {

    int getSteamConsumption();
    int getWaterOutputRate();
    void setSteamConsumption(int steamConsumption);
    void setWaterOutputRate(int waterOutputRate);
    void changeSteamConsumption(int amount);
    void changeWaterOutputRate(int amount);
    default void addTooltip(List<String> tooltip) {
        tooltip.add(I18n.format("tkcya.general.steam_consumer.tooltip.steam_input", getSteamConsumption()));
        tooltip.add(I18n.format("tkcya.general.steam_consumer.tooltip.water_output", getWaterOutputRate()));
    }
}
