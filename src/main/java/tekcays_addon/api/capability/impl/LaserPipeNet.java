package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import net.minecraft.nbt.NBTTagCompound;
import tekcays_addon.api.capability.ILaserContainer;

import javax.annotation.Nonnull;

public class LaserPipeNet extends PipeNet<LaserPipeData> implements ILaserContainer {

    private double netParticles = GCYSValues.EARTH_PRESSURE;
    private double volume = 1.0D;
    private double minNetLaser = Double.MAX_VALUE;
    private double maxNetLaser = Double.MIN_VALUE;

    public LaserPipeNet(WorldPipeNet<LaserPipeData, ? extends PipeNet> world) {
        super(world);
    }

    @Override
    protected void writeNodeData(@Nonnull LaserPipeData pressurePipeData, @Nonnull NBTTagCompound nbt) {
        nbt.setDouble("MinP", pressurePipeData.getMinLaser());
        nbt.setDouble("MaxP", pressurePipeData.getMaxLaser());
        nbt.setDouble("Volume", pressurePipeData.getVolume());
    }

    @Override
    protected LaserPipeData readNodeData(@Nonnull NBTTagCompound nbt) {
        return new LaserPipeData(nbt.getDouble("MinP"), nbt.getDouble("MaxP"), nbt.getDouble("Volume"));
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setDouble("minNetP", minNetLaser);
        compound.setDouble("maxNetP", maxNetLaser);
        compound.setDouble("Volume", volume);
        compound.setDouble("Particles", netParticles);
        return compound;
    }


    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        this.minNetLaser = nbt.getDouble("minNetP");
        this.maxNetLaser = nbt.getDouble("maxNetP");
        this.volume = nbt.getDouble("Volume");
        this.netParticles = nbt.getDouble("Particles");
    }

    @Override
    protected void onNodeConnectionsUpdate() {
        super.onNodeConnectionsUpdate();
        this.minNetLaser = getAllNodes().values().stream().mapToDouble(node -> node.data.getMinLaser()).max().orElse(Double.MAX_VALUE);
        this.maxNetLaser = getAllNodes().values().stream().mapToDouble(node -> node.data.getMaxLaser()).min().orElse(Double.MIN_VALUE);
        final double oldVolume = getVolume();
        this.volume = Math.max(1, getAllNodes().values().stream().mapToDouble(node -> node.data.getVolume()).sum());
        this.netParticles *= getVolume() / oldVolume;
    }

    @Override
    public void onPipeConnectionsUpdate() {
        super.onPipeConnectionsUpdate();
    }

    @Override
    public double getParticles() {
        return netParticles;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public void setParticles(double amount) {
        this.netParticles = amount;
    }

    @Override
    public boolean changeParticles(double amount, boolean simulate) {
        if (simulate) return isLaserSafe(getLaserForParticles(getParticles() + amount));
        setParticles(getParticles() + amount);
        LaserNetWalker.checkLaser(getWorldData(), getAllNodes().keySet().iterator().next(), getLaser());
        return isLaserSafe();
    }

    public void onLeak() {
        if (getLaser() < GCYSValues.EARTH_PRESSURE) changeParticles(getLeakRate(), false);
        else if (getLaser() > GCYSValues.EARTH_PRESSURE) changeParticles(-getLeakRate(), false);
    }

    public double getLeakRate() {
        return 1000.0D;
    }

    @Override
    public double getMinLaser() {
        return minNetLaser;
    }

    @Override
    public double getMaxLaser() {
        return maxNetLaser;
    }
}
