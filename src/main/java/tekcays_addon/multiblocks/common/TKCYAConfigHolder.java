package tekcays_addon.multiblocks.common;

import tekcays_addon.multiblocks.TekCaysAddon;
import net.minecraftforge.common.config.Config;

@Config(modid = TekCaysAddon.MODID)
public class TKCYAConfigHolder {

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Global Multiblock Options")
    public static GlobalMultiblocks globalMultiblocks = new GlobalMultiblocks();

    public static class GlobalMultiblocks {

        @Config.Comment({"Makes nearly every TKCYA Multiblock require blocks which set their maximum voltages.", "Default: false"})
        public boolean enableTieredCasings = false;
    }
}
