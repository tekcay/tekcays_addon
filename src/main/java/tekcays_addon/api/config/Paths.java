package tekcays_addon.api.config;

import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Loader;
import tekcays_addon.TekCaysAddon;

import java.nio.file.Path;

import static tekcays_addon.gtapi.consts.DepositValues.*;

public class Paths {

    public static final Path CONFIG_PATH = Loader.instance().getConfigDir().toPath();
    public static final Path GT_CONFIG_PATH = CONFIG_PATH.resolve(GTValues.MODID);
    /**
     * The path of the worldgen folder in the gt config folder
     */
    public static final Path GT_WORLD_GEN_ROOT_PATH = GT_CONFIG_PATH.resolve(WORLDGEN);
    public static final Path TKCYA_CONFIG_PATH = CONFIG_PATH.resolve(TekCaysAddon.MODID);
    public static final Path TKCYA_CONFIG_WORLDGEN_PATH = TKCYA_CONFIG_PATH.resolve(WORLDGEN);
    public static final Path TKCYA_CONFIG_FLUID_DEPOSIT_PATH = TKCYA_CONFIG_WORLDGEN_PATH.resolve(FLUID_DEPOSITS);
    public static final Path TKCYA_CONFIG_ORE_DEPOSIT_PATH = TKCYA_CONFIG_WORLDGEN_PATH.resolve(ORE_DEPOSITS);
    public static final String TKCYA_JAR_ORE_DEPOSIT_PATH = "/assets/tkcya/worldgen/ore_deposits/overworld";
    public static final String TKCYA_JAR_FLUID_DEPOSIT_PATH = "/assets/tkcya/worldgen/fluid_deposits/overworld";
}
