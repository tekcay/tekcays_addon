package tekcays_addon.common;

import tekcays_addon.TekCaysAddon;
import net.minecraftforge.common.config.Config;

@Config(modid = TekCaysAddon.MODID)
public class TKCYAConfigHolder {

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Foil overhaul")
    public static FoilOverhaul foilOverhaul = new FoilOverhaul();

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Magnetic overhaul")
    public static MagneticOverhaul magneticOverhaul = new MagneticOverhaul();

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Melting overhaul")
    public static MeltingOverhaul meltingOverhaul = new MeltingOverhaul();

    public static class FoilOverhaul {
        @Config.Comment({"Foils are made in the Cluster Mill.", "Default: true"})
        public boolean enableFoilOverhaul = true;
    }
    public static class MagneticOverhaul {

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableMagneticOverhaul = true;
    }

    public static class MeltingOverhaul {

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableMeltingOverhaul = true;

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableAlloyingOverhaul = true;

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableCastingOverhaul = true;
    }
}
