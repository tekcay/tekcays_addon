package tekcays_addon.api.capability.impl;

import gregtech.api.pipenet.WorldPipeNet;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WorldLaserNet extends WorldPipeNet<LaserPipeData, LaserPipeNet> {

    private static final String DATA_ID = "gregtech.pressure_pipe_net";

    public WorldLaserNet(@Nonnull String name) {
        super(name);
    }

    @Nonnull
    public static WorldLaserNet getWorldPipeNet(@Nonnull World world) {
        WorldLaserNet netWorldData = (WorldLaserNet) world.loadData(WorldLaserNet.class, DATA_ID);
        if (netWorldData == null) {
            netWorldData = new WorldLaserNet(DATA_ID);
            world.setData(DATA_ID, netWorldData);
        }
        netWorldData.setWorldAndInit(world);
        return netWorldData;
    }

    @Override
    protected LaserPipeNet createNetInstance() {
        return new LaserPipeNet(this);
    }
}
