package tekcays_addon.common.metatileentities;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.storage.MetaTileEntityDrum;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.recipes.TKCYARecipeMaps;


import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.metatileentities.multi.*;
import tekcays_addon.common.metatileentities.steam.SteamCooler;

import static gregtech.common.metatileentities.MetaTileEntities.*;

public class TKCYAMetaTileEntities {


    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ELECTRIC_CASTING_TABLE = new SimpleMachineMetaTileEntity[5];

    public static MetaTileEntityPrimitiveMelter PRIMITIVE_MELTER;
    public static MetaTileEntityElectricMelter ELECTRIC_MELTER;
    public static MetaTileEntityFuelMelter FUEL_MELTER;
    public static MetaTileEntityPrimitiveFermenter PRIMITIVE_FERMENTER;
    public static MetaTileEntityAlloyingCrucible ALLOYING_CRUCIBLE;
    public static MetaTileEntityCastingTable CASTING_TABLE;
    public static SteamCooler STEAM_COOLER_BRONZE;
    public static SteamCooler STEAM_COOLER_STEEL;
    public static MetaTileEntityConverter CONVERTER;
    public static MetaTileEntityPrimitiveBath PRIMITIVE_BATH;
    public static MetaTileEntityCrystallizer CRYSTALLIZER;
    public static MetaTileEntityAdvancedElectrolyzer ADVANCED_ELECTROLYZER;
    public static MetaTileEntityFilter FILTER;
    public static MetaTileEntityPressurizedCrackingUnit PRESSURIZED_CRACKING_UNIT;

    //Blast Furnaces
    public static MetaTileEntityTKCYABlastFurnace BRICK_BLAST_FURNACE;
    public static MetaTileEntityTKCYABlastFurnace FIRECLAY_BRICK_BLAST_FURNACE;
    public static MetaTileEntityTKCYABlastFurnace REINFORCED_BRICK_BLAST_FURNACE;
    public static MetaTileEntityTKCYABlastFurnace STRONG_BRICK_BLAST_FURNACE;



    // Drums
    public static MetaTileEntityDrum WOODEN_DRUM;
    public static MetaTileEntityDrum BRONZE_DRUM;
    public static MetaTileEntityDrum STEEL_DRUM;
    public static MetaTileEntityDrum ALUMINIUM_DRUM;

    public static MetaTileEntityDrum GALVANIZED_STEEL_DRUM;
    public static MetaTileEntityDrum STAINLESS_STEEL_DRUM;
    public static MetaTileEntityDrum POLYTETRAFLUOROETHYLENE_DRUM;
    public static MetaTileEntityDrum POLYPROPYLENE_DRUM;
    public static MetaTileEntityDrum HIGH_DENSITY_POLYETHYLENE_DRUM;

    //Tanks
    public static TKCYAMetaTileEntityMultiblockTank WOODEN_TANK;
    public static TKCYAMetaTileEntityMultiblockTank STEEL_TANK;
    public static TKCYAMetaTileEntityMultiblockTank GALVANIZED_STEEL_TANK;
    public static TKCYAMetaTileEntityMultiblockTank STAINLESS_STEEL_TANK;
    public static TKCYAMetaTileEntityTankValve STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve GALVANIZED_STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve STAINLESS_STEEL_TANK_VALVE;

    public static void init() {

        if (TKCYAConfigHolder.miscOverhaul.enableFoilOverhaul) {
            registerSimpleMetaTileEntity(CLUSTER_MILL, 11000, "cluster_mill", TKCYARecipeMaps.CLUSTER_MILL_RECIPES,
                    TKCYATextures.CLUSTER_MILL_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            registerSimpleMetaTileEntity(ADVANCED_POLARIZER, 11005, "advanced_polarizer", TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES,
                    TKCYATextures.ADVANCED_POLARIZER_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            PRIMITIVE_MELTER = registerMetaTileEntity(11010, new MetaTileEntityPrimitiveMelter(tkcyaId("primitive_melter")));
            ELECTRIC_MELTER = registerMetaTileEntity(11011, new MetaTileEntityElectricMelter(tkcyaId("electric_melter")));
            FUEL_MELTER = registerMetaTileEntity(11012, new MetaTileEntityFuelMelter(tkcyaId("fuel_melter")));
            PRIMITIVE_FERMENTER = registerMetaTileEntity(11056, new MetaTileEntityPrimitiveFermenter(tkcyaId("primitive_fermenter")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            ALLOYING_CRUCIBLE = registerMetaTileEntity(11013, new MetaTileEntityAlloyingCrucible(tkcyaId("alloying_crucible")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            CASTING_TABLE = registerMetaTileEntity(11014, new MetaTileEntityCastingTable(tkcyaId("casting_table")));

            registerSimpleMetaTileEntity(ELECTRIC_CASTING_TABLE, 11015, "electric_casting_table", TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES,
                    TKCYATextures.CASTING_TABLE_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);


            STEAM_COOLER_BRONZE = registerMetaTileEntity(11020, new SteamCooler(tkcyaId("steam_cooler_bronze"), false));
            STEAM_COOLER_STEEL = registerMetaTileEntity(11021, new SteamCooler(tkcyaId("steam_cooler_steel"), true));

        }


        if (TKCYAConfigHolder.meltingOverhaul.enableBlastingOverhaul) {
            CONVERTER = registerMetaTileEntity(11022, new MetaTileEntityConverter(tkcyaId("converter")));



            BRICK_BLAST_FURNACE = registerMetaTileEntity(11051, new MetaTileEntityTKCYABlastFurnace(tkcyaId("brick_blast_furnace"), BlockBrick.BrickType.BRICK));
            FIRECLAY_BRICK_BLAST_FURNACE = registerMetaTileEntity(11052, new MetaTileEntityTKCYABlastFurnace(tkcyaId("fireclay_brick_blast_furnace"), BlockBrick.BrickType.FIRECLAY_BRICK));
            REINFORCED_BRICK_BLAST_FURNACE = registerMetaTileEntity(11053, new MetaTileEntityTKCYABlastFurnace(tkcyaId("reinforced_brick_blast_furnace"), BlockBrick.BrickType.REINFORCED_BRICK));
            STRONG_BRICK_BLAST_FURNACE = registerMetaTileEntity(11054, new MetaTileEntityTKCYABlastFurnace(tkcyaId("strong_brick_blast_furnace"), BlockBrick.BrickType.STRONG_BRICK));
        }

        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            PRIMITIVE_BATH = registerMetaTileEntity(11024, new MetaTileEntityPrimitiveBath(tkcyaId("primitive_bath")));
        }

        if (TKCYAConfigHolder.miscOverhaul.enableElectrolysisOverhaul) {
            ADVANCED_ELECTROLYZER = registerMetaTileEntity(11025, new MetaTileEntityAdvancedElectrolyzer(tkcyaId("advanced_electrolyzer")));
        }

        if (TKCYAConfigHolder.storageOverhaul.enableDrumsOverhaul) {
            WOODEN_DRUM = registerMetaTileEntity(11026, new MetaTileEntityDrum(tkcyaId("drum.wood"), Materials.Wood, 4000));
            BRONZE_DRUM = registerMetaTileEntity(11027, new MetaTileEntityDrum(tkcyaId("drum.bronze"), Materials.Bronze, 8000));
            STEEL_DRUM = registerMetaTileEntity(11028, new MetaTileEntityDrum(tkcyaId("drum.steel"), Materials.Steel, 16000));
            ALUMINIUM_DRUM = registerMetaTileEntity(11029, new MetaTileEntityDrum(tkcyaId("drum.aluminium"), Materials.Aluminium, 16000));
            GALVANIZED_STEEL_DRUM = registerMetaTileEntity(11030, new MetaTileEntityDrum(tkcyaId("drum.galvanized_steel"), TKCYAMaterials.GalvanizedSteel, 16000));
            STAINLESS_STEEL_DRUM = registerMetaTileEntity(11031, new MetaTileEntityDrum(tkcyaId("drum.stainless_steel"), Materials.StainlessSteel, 16000));
            POLYTETRAFLUOROETHYLENE_DRUM = registerMetaTileEntity(11032, new MetaTileEntityDrum(tkcyaId("drum.polytetrafluoroethylene"), Materials.Polytetrafluoroethylene, 16000));
            POLYPROPYLENE_DRUM = registerMetaTileEntity(11033, new MetaTileEntityDrum(tkcyaId("drum.polypropylene"), TKCYAMaterials.Polypropylene, 16000));
            HIGH_DENSITY_POLYETHYLENE_DRUM = registerMetaTileEntity(11034, new MetaTileEntityDrum(tkcyaId("drum.high_density_polyethylene"), TKCYAMaterials.HighDensityPolyethylene, 16000));
        }

        if (TKCYAConfigHolder.storageOverhaul.enableMultiblockTanksOverhaul) {

            WOODEN_TANK = registerMetaTileEntity(11040, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.wood"),
                            Materials.TreatedWood,
                            MetaBlocks.STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.WOOD_WALL),
                            250000));

            STEEL_TANK = registerMetaTileEntity(11041, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.steel"),
                    Materials.Steel,
                    TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STEEL_WALL),
                    250000));


            GALVANIZED_STEEL_TANK = registerMetaTileEntity(11042, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.galvanized_steel"),
                    TKCYAMaterials.GalvanizedSteel,
                    TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL),
                    250000));

            STAINLESS_STEEL_TANK = registerMetaTileEntity(11043, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.stainless_steel"),
                    Materials.StainlessSteel,
                    TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL),
                    250000));


            STEEL_TANK_VALVE = registerMetaTileEntity(11045, new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.steel"),
                    Materials.Steel));

            GALVANIZED_STEEL_TANK_VALVE = registerMetaTileEntity(11046, new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.galvanized_steel"),
                    TKCYAMaterials.GalvanizedSteel));

            STAINLESS_STEEL_TANK_VALVE = registerMetaTileEntity(11047, new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.stainless_steel"),
                    Materials.StainlessSteel));


        }

        if (TKCYAConfigHolder.meltingOverhaul.enableBouleCrystallization) {
            CRYSTALLIZER = registerMetaTileEntity(11050, new MetaTileEntityCrystallizer(tkcyaId("crystallizer")));
        }

        FILTER = registerMetaTileEntity(11055, new MetaTileEntityFilter(tkcyaId("filter")));

        if (TKCYAConfigHolder.crackingOverhaul.enableCrackingOverhaul) {
            PRESSURIZED_CRACKING_UNIT = registerMetaTileEntity(11057, new MetaTileEntityPressurizedCrackingUnit(tkcyaId("pressurized_cracking_unit")));
        }

    }

    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }


}
