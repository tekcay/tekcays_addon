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
    @Config.Name("Cracking overhaul")
    public static CrackingOverhaul crackingOverhaul = new CrackingOverhaul();

    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Melting overhaul")
    public static EnergyOverhaul energyOverhaul = new EnergyOverhaul();
    @Config.Comment("Config options applying to all TKCYA Multiblocks")
    @Config.Name("Storage overhaul")
    public static StorageOverhaul storageOverhaul = new StorageOverhaul();


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

        @Config.Comment({"Removes most electrolysis recipes and replaces the GTCEu Electrolyzer with a new one", "Default: true"})
        public boolean enableElectrolysisOverhaul = true;

        @Config.Comment({"Replaces the GTCEu CokeOven with a new one", "Default: true"})
        public boolean enableCokeOvenOverhaul = true;

        @Config.Comment({"Rotors can not be made in extruder anymore, adds a recipe in the assembler", "Default: true"})
        public boolean enableHarderRotors = true;

    }

    public static class CrackingOverhaul {

        @Config.Comment({"Cracking now requires pressure", "Default: true"})
        public boolean enableCrackingOverhaul = true;

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

        @Config.Comment({"Boule must be crystallized", "Default : true"})
        public boolean enableBouleCrystallization = true;
    }

    public static class EnergyOverhaul {

        @Config.Comment({"Gas are burned in the large boilers", "Default: true"})
        public boolean disableGasTurbinesOverhaul = true;

    }

    public static class StorageOverhaul {
        @Config.Comment({"Drums can handle smaller volume, but can me made from more materials."})
        public boolean enableDrumsOverhaul = true;

        @Config.Comment({"Multiblock Tanks can handle smaller volume, but can me made from more materials."})
        public boolean enableMultiblockTanksOverhaul = true;

        @Config.Comment({"Remove SuperTanks and Quantum Tanks"})
        public boolean removeOPTanks = true;

    }
}
