package tekcays_addon.common;

import net.minecraftforge.common.config.Config;

import tekcays_addon.TekCaysAddon;

@Config(modid = TekCaysAddon.MODID)
public class TKCYAConfigHolder {

    @Config.Comment("Miscellaneaous config options")
    @Config.Name("Miscellaneous")
    public static MiscOverhaul miscOverhaul = new MiscOverhaul();

    @Config.Comment("Miscellaneaous config options")
    @Config.Name("Harder")
    public static HarderStuff harderStuff = new HarderStuff();

    @Config.Comment("Machines options")
    @Config.Name("MachinesOptions")
    public static MachinesOptions machinesOptions = new MachinesOptions();

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

        @Config.Comment({
                "Enables TKCYA custom oregen. You MUST delete the json files in your config/gregtech/worldgen/vein folder." })
        public boolean enableTKCYACustomOreGen = true;

        @Config.Comment({ "Coils now requires Mica foils", "Default: true" })
        public boolean enableCoilOverhaul = true;

        @Config.Comment({ "Magnetic parts need Magnetite", "Default: true" })
        public boolean enableMagneticOverhaul = true;

        @Config.Comment({ "Disable IV+ tier machines", "Default: true" })
        public boolean disableHighTierMachines = true;

        @Config.Comment({ "Replace normal steel with galvanized steel in LV components", "Default: true" })
        public boolean enableGalvanizedSteel = true;

        @Config.Comment({ "Removes most electrolysis recipes and replaces the GTCEu Electrolyzer with a new one",
                "Default: true" })
        public boolean enableElectrolysisOverhaul = true;

        @Config.Comment({ "Replaces the GTCEu CokeOven with a new one", "Default: true" })
        public boolean enableCokeOvenOverhaul = true;

        @Config.Comment({ "Multiblock tanks capacity depends of their height", "Default: true" })
        public boolean enableModulableTanks = true;
    }

    public static class HarderStuff {

        @Config.Comment({ "Components can only be made in the assembler", "Default: true" })
        public boolean disableComponentsShapesRecipes = true;

        @Config.Comment({
                "Rotors can not be made in extruder anymore, they also require curved plates adds a recipe in the assembler",
                "Default: true" })
        public boolean enableHarderRotors = true;

        @Config.Comment({
                "Rotors can not be made in extruder anymore, they also require curved plates adds a recipe in the assembler",
                "Default: true" })
        public boolean enableHarderPipes = true;

        @Config.Comment({ "Remove circuit recipes using tin. Only soldering alloy is allowed", "Default: true" })
        public boolean disableTinCircuitRecipes = true;

        @Config.Comment({ "Remove all furnace recipes", "Default: true" })
        public boolean disableFurnacesRecipes = true;

        @Config.Comment({ "bla", "Default: true" })
        public boolean enableRoastingOverhaul = true;

        @Config.Comment({ "bla", "Default: 10. DO NOT TOUCH FOR NOW!" })
        public final int maxOutputPerOre = 10;

        @Config.Comment({ "Foils are made in the Cluster Mill.", "Default: true" })
        public boolean enableHarderFoil = true;
    }

    public static class MachinesOptions {

        @Config.Comment({ "Blast furnace can take fire and explode", "Default: true" })
        public boolean enableBlastFurnaceFireExplosion = true;
    }

    public static class CrackingOverhaul {

        @Config.Comment({ "Cracking now requires pressure", "Default: true" })
        public boolean enableCrackingOverhaul = true;
    }

    public static class MeltingOverhaul {

        @Config.Comment({ "Magnetic parts need magnetite", "Default: true" })
        public boolean enableMeltingOverhaul = true;

        @Config.Comment({ "Magnetic parts need magnetite", "Default: true" })
        public boolean enableAlloyingOverhaul = true;

        @Config.Comment({ "Overhaul for steel making", "Default: true" })
        public boolean enableBlastingOverhaul = true;

        @Config.Comment({ "Magnetic parts need magnetite", "Default: true" })
        public boolean enableCastingOverhaul = true;

        @Config.Comment({ "Boule must be crystallized", "Default : true" })
        public boolean enableBouleCrystallization = true;
    }

    public static class EnergyOverhaul {

        @Config.Comment({ "Gas are burned in the large boilers", "Default: true" })
        public boolean disableGasTurbinesOverhaul = true;
    }

    public static class StorageOverhaul {

        @Config.Comment({ "Drums can handle smaller volume, but can me made from more materials." })
        public boolean enableDrumsOverhaul = true;

        @Config.Comment({ "Multiblock Tanks can handle smaller volume, but can me made from more materials." })
        public boolean enableMultiblockTanksOverhaul = true;

        @Config.Comment({ "Remove SuperTanks and Quantum Tanks" })
        public boolean removeOPTanks = true;
    }
}
