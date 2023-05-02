package tekcays_addon.integration;

import gregtech.common.items.MetaItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.gtapi.utils.TKCYALog;
import tekcays_addon.gtapi.worldgen.FluidDepositDefinition;
import tekcays_addon.gtapi.worldgen.TKCYAWorldGenRegistry;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@JEIPlugin
public class JEI implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {

        // Fluid Veins
        List<FluidDepositDefinition> fluidVeins = TKCYAWorldGenRegistry.getFluidDeposits();
        List<FluidDepositInfo> fluidVeinInfos = new CopyOnWriteArrayList<>();
        for (FluidDepositDefinition fluidVein : fluidVeins) {
            TKCYALog.logger.info("HERE");
            TKCYALog.logger.info(fluidVein);
            fluidVeinInfos.add(new FluidDepositInfo(fluidVein));
        }

        String fluidVeinSpawnID = TekCaysAddon.MODID + ":" + "fluid_spawn_location";
        registry.addRecipes(fluidVeinInfos, fluidVeinSpawnID);
        registry.addRecipeCatalyst(MetaItems.PROSPECTOR_HV.getStackForm(), fluidVeinSpawnID);
        registry.addRecipeCatalyst(MetaItems.PROSPECTOR_LUV.getStackForm(), fluidVeinSpawnID);
        // Fluid Veins End
    }
}
