package tekcays_addon.common.metatileentities;

import org.junit.Assert;
import org.junit.Test;
import tekcays_addon.common.metatileentities.single.MetaTileEntitySteamTurbine;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.STEAM_TURBINE;
import static tekcays_addon.gtapi.consts.TKCYAValues.tkcyaId;

public class TKCYAMetaTileEntitiesTest {

    @Test
    void test() {
        AtomicReference<String> str = new AtomicReference<>("");
        IntStream.range(1, STEAM_TURBINE.length)
                .forEach(i -> {
                    System.out.println(i);
                    str.updateAndGet(v -> v + i);
                    STEAM_TURBINE[i] = registerMetaTileEntity(12000 + i, new MetaTileEntitySteamTurbine(tkcyaId("steam_turbine." + i), i));
                });
        Assert.assertEquals(str.get(), "123456");
    }

}
