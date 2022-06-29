package tekcays_addon.common;

import tekcays_addon.TekCaysAddon;
import net.minecraftforge.common.config.Config;

@Config(modid = TekCaysAddon.MODID)
public class TKCYAConfigHolder {


    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Miscellaneous")
    public static MiscOverhaul miscOverhaul = new MiscOverhaul();

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Melting overhaul")
    public static MeltingOverhaul meltingOverhaul = new MeltingOverhaul();

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Melting overhaul")
    public static EnergyOverhaul energyOverhaul = new EnergyOverhaul();

    public static class MiscOverhaul {

        @Config.Comment({"Foils are made in the Cluster Mill.", "Default: true"})
        public boolean enableFoilOverhaul = true;

        @Config.Comment({"Foils are made in the Cluster Mill.", "Default: true"})
        public boolean enableCoilOverhaul = true;

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableMagneticOverhaul = true;

        @Config.Comment({"Disable IV+ tier machines", "Default: true"})
        public boolean disableHighTierMachines = true;

        @Config.Comment({"Replace normal steel with galvanized steel", "Default: true"})
        public boolean enableGalvanizedSteel = true;

        @Config.Comment({"Components can not be made in shaped recipe", "Default: true"})
        public boolean disableComponentsShapesRecipes = true;

        @Config.Comment({"Multiblock tanks capacity depends of their height", "Default: true"})
        public boolean enableModulableTanks = true;

    }


    public static class MeltingOverhaul {

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableMeltingOverhaul = true;

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableAlloyingOverhaul = true;

        @Config.Comment({"Overhaul for steel making", "Default: true"})
        public boolean enableBlastingOverhaul = true;

        @Config.Comment({"Magnetic parts need magnetite", "Default: true"})
        public boolean enableCastingOverhaul = true;
    }

    public static class EnergyOverhaul {

        @Config.Comment({"Gas are burned in the large boilers", "Default: true"})
        public boolean disableGasTurbinesOverhaul = true;

    }
}
