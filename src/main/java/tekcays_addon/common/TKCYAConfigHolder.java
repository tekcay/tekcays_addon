package tekcays_addon.common;

import tekcays_addon.TekCaysAddon;
import net.minecraftforge.common.config.Config;

@Config(modid = TekCaysAddon.MODID)
public class TKCYAConfigHolder {

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Foil overhaul")
    public static FoilOverhaul foilOverhaul = new FoilOverhaul();

    public static class FoilOverhaul {

        @Config.Comment({"Foils are made in the Cluster Mill.", "Default: true"})
        public boolean enableFoilOverhaul = false;
    }
}
