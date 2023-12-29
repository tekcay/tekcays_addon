package tekcays_addon.gtapi.capability.containers;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

public interface IHinderedContainer {

    void changeWasteAmount(int amount);

    void changeFuelAmount(int amount);

    /**
     * Returns the {@link ItemStack} of the total amount of waste.
     * 
     * @return return the total waste
     */
    ItemStack getWasteStack();

    ItemStack getFuelStack();

    int getFuelAmount();

    int getWasteAmount();

    void initWasteStack(@NotNull OrePrefix orePrefix, @NotNull Material wasteMaterial, int amount);

    void initWasteStack(ItemStack itemStack);

    void initFuelStack(@NotNull OrePrefix orePrefix, @NotNull Material wasteMaterial, int amount);

    void initFuelStack(ItemStack itemStack);

    void clearWaste();

    void clearFuel();

    float getEfficiency();

    void setEfficiency(float efficiency);

    void changeEfficiency(float amount);

    void resetEfficiency();
}
