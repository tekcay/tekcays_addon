package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import org.apache.commons.lang3.Range;
import tekcays_addon.api.item.ItemStackUtils;
import tekcays_addon.api.utils.Numbers;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IHinderedContainer;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.consts.NBTKeys.*;
import static tekcays_addon.api.utils.Numbers.changeByKeepingPositive;

@Getter
public class HinderedContainer extends MTETrait implements IHinderedContainer {

    private ItemStack wasteStack, fuelStack;
    private int wasteAmount, fuelAmount;
    private float efficiency;

    /**
     * Create a new MTE trait.
     * @param metaTileEntity the MTE to reference, and add the trait to
     */
    public HinderedContainer(@Nonnull MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
        this.efficiency = 1.0f;
        this.wasteAmount = 0;
        this.fuelAmount = 0;
    }

    @Override
    public void initWasteStack(@Nonnull OrePrefix orePrefix, @Nonnull Material wasteMaterial, int amount) {
        this.wasteStack = OreDictUnifier.get(orePrefix, wasteMaterial, amount);
        this.metaTileEntity.markDirty();
    }

    @Override
    public void initWasteStack(ItemStack itemStack) {
        this.wasteStack = itemStack;
        this.metaTileEntity.markDirty();;
    }

    @Override
    public void initFuelStack(@Nonnull OrePrefix orePrefix, @Nonnull Material wasteMaterial, int amount) {
        this.wasteStack = OreDictUnifier.get(orePrefix, wasteMaterial, amount);
        this.metaTileEntity.markDirty();
    }

    @Override
    public void initFuelStack(ItemStack itemStack) {
        this.fuelStack = itemStack;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void changeWasteAmount(int amount) {
        this.wasteAmount = changeByKeepingPositive(this.wasteAmount, amount);
        this.metaTileEntity.markDirty();
    }

    @Override
    public void changeFuelAmount(int amount) {
        this.fuelAmount = changeByKeepingPositive(this.fuelAmount, amount);
        this.metaTileEntity.markDirty();
    }

    @Override
    public void clearWaste() {
        this.wasteAmount = 0;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void clearFuel() {
        this.fuelAmount = 0;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void resetEfficiency() {
        this.efficiency = 0.0f;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void changeEfficiency(float amount) {
        this.efficiency = Numbers.getPercentageInFloat(this.efficiency + amount);
        this.metaTileEntity.markDirty();
    }

    @Nonnull
    @Override
    public String getName() {
        return "hindered_container";
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_HINDERED_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_HINDERED_CONTAINER.cast(this);
        }
        return null;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setFloat(EFFICIENCY_KEY, this.efficiency);
        compound.setInteger(WASTE_AMOUNT_KEY, this.wasteAmount);
        compound.setInteger(FUEL_AMOUNT_KEY, this.fuelAmount);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.efficiency = compound.getFloat(EFFICIENCY_KEY);
        this.wasteAmount = compound.getInteger(WASTE_AMOUNT_KEY);
        this.fuelAmount = compound.getInteger(FUEL_AMOUNT_KEY);
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeFloat(this.efficiency);
        buffer.writeInt(this.wasteAmount);
        buffer.writeInt(this.fuelAmount);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.efficiency = buffer.readFloat();
        this.wasteAmount = buffer.readInt();
        this.fuelAmount = buffer.readInt();

    }


}