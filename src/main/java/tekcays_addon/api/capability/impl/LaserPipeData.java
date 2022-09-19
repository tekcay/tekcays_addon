package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;

public class LaserPipeData {

    public static final LaserPipeData EMPTY = new LaserPipeData(GCYSValues.EARTH_PRESSURE, GCYSValues.EARTH_PRESSURE, 1.0);

    private final double minLaser;
    private final double maxLaser;
    private final double volume;

    public LaserPipeData(double minLaser, double maxLaser, double volume) {
        this.minLaser = minLaser;
        this.maxLaser = maxLaser;
        this.volume = volume;
    }

    public double getMinLaser() {
        return minLaser;
    }

    public double getMaxLaser() {
        return maxLaser;
    }

    public double getVolume() {
        return volume;
    }
}
