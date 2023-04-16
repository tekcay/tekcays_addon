package tekcays_addon.gtapi.capability.containers;

public interface ISteamConsumer {

    int getSteamConsumption();
    int getWaterOutputRate();
    void setSteamConsumption(int steamConsumption);
    void setWaterOutputRate(int waterOutputRate);
    void changeSteamConsumption(int amount);
    void changeWaterOutputRate(int amount);
}
