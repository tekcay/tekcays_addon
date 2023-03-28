package tekcays_addon.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.storage.MetaTileEntityDrum;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.api.utils.FuelHeaterTiers;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.metatileentities.multi.*;

import tekcays_addon.common.metatileentities.multiblockpart.*;
import tekcays_addon.common.metatileentities.single.*;

import tekcays_addon.common.metatileentities.single.MetaTileEntitySingleCrucible;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamAirCompressor;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamAutoclave;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamCooler;

import java.util.function.IntFunction;

import static gregtech.common.metatileentities.MetaTileEntities.*;
import static tekcays_addon.api.utils.BlastFurnaceUtils.BRICKS;
import static tekcays_addon.api.utils.FuelHeaterTiers.BRICK;
import static tekcays_addon.api.utils.FuelHeaterTiers.FUEL_HEATERS;
import static tekcays_addon.api.utils.TKCYAValues.DRUM_MATERIALS;

public class TKCYAMetaTileEntities {


    public static int MAX_TIER = GTValues.IV;
    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[MAX_TIER];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[MAX_TIER];
    public static SimpleMachineMetaTileEntity[] ELECTRIC_CASTING_TABLE = new SimpleMachineMetaTileEntity[MAX_TIER];


    //HEAT STUFF
    public static String[] HEAT_MTES = {"electric_heater.", "electric_cooler.", "heat_acceptor."};
    public static MetaTileEntityElectricHeater[] ELECTRIC_HEATER = new MetaTileEntityElectricHeater[MAX_TIER];
    public static MetaTileEntityElectricCooler[] ELECTRIC_COOLER = new MetaTileEntityElectricCooler[MAX_TIER];
    public static MetaTileEntityHeatAcceptor[] HEAT_ACCEPTOR = new MetaTileEntityHeatAcceptor[MAX_TIER];

    //PRESSURE HATCHES
    public static int PRESSURE_HATCH_MAX_TIER = GTValues.LuV;
    public static String[] PRESSURE_HATCHES_TYPE = {"vacuum_hatch.", "pressure_hatch."};
    public static MetaTileEntityPressureHatch[] PRESSURE_HATCH = new MetaTileEntityPressureHatch[PRESSURE_HATCH_MAX_TIER + 1];
    public static MetaTileEntityVacuumHatch[] VACUUM_HATCH = new MetaTileEntityVacuumHatch[PRESSURE_HATCH_MAX_TIER + 1];

    //ELECTRIC PRESSURE COMPRESSOR
    public static int PRESSURE_COMPRESSOR_SINGLE_MAX_TIER = GTValues.IV;
    public static String[] ELECTRIC_PRESSURE_COMPRESSORS_TYPE = {"electric_vacuum_pump.", "electric_compressor."};
    public static MetaTileEntityElectricPressureCompressor[] PRESSURE_COMPRESSOR = new MetaTileEntityElectricPressureCompressor[PRESSURE_COMPRESSOR_SINGLE_MAX_TIER + 1];
    public static MetaTileEntityElectricVacuumPump[] VACUUM_PUMP = new MetaTileEntityElectricVacuumPump[PRESSURE_COMPRESSOR_SINGLE_MAX_TIER + 1];


    public static MetaTileEntityPrimitiveMelter PRIMITIVE_MELTER;
    public static MetaTileEntityPrimitiveFermenter PRIMITIVE_FERMENTER;
    public static MetaTileEntityPrimitiveConverter PRIMITIVE_CONVERTER;
    public static MetaTileEntityAlloyingCrucible ALLOYING_CRUCIBLE;
    public static MetaTileEntityCastingTable CASTING_TABLE;
    public static MetaTileEntitySteamCooler STEAM_COOLER_BRONZE;
    public static MetaTileEntitySteamCooler STEAM_COOLER_STEEL;
    public static MetaTileEntitySteamAirCompressor STEAM_AIR_COMPRESSOR;
    public static MetaTileEntityElectricConverter CONVERTER;
    public static MetaTileEntityTKCYACokeOven COKE_OVEN;
    public static MetaTileEntityPrimitiveBath PRIMITIVE_BATH;
    public static MetaTileEntityCrystallizer CRYSTALLIZER;
    public static MetaTileEntityAdvancedElectrolyzer ADVANCED_ELECTROLYZER;
    public static MetaTileEntityFilter FILTER;
    public static MetaTileEntityPressurizedCrackingUnit PRESSURIZED_CRACKING_UNIT;
    public static MetaTileEntitySteamAutoclave STEAM_AUTOCLAVE;

    //FUEL HEATERS
    public static String[] FUEL_HEATERS_TYPES = {"_solid_fuel_heater", "_liquid_fuel_heater", "_fluidized_fuel_heater", "_gas_fuel_heater"};
    public static MetaTileEntitySolidFuelHeater[] SOLID_FUEL_HEATER = new MetaTileEntitySolidFuelHeater[FUEL_HEATERS.size()];
    public static MetaTileEntityLiquidFuelHeater[] LIQUID_FUEL_HEATER = new MetaTileEntityLiquidFuelHeater[FUEL_HEATERS.size()];
    public static MetaTileEntityFluidizedHeater[] FLUIDIZED_FUEL_HEATER = new MetaTileEntityFluidizedHeater[FUEL_HEATERS.size()];
    public static MetaTileEntityGasHeater[] GAS_FUEL_HEATER = new MetaTileEntityGasHeater[FUEL_HEATERS.size()];

    public static MetaTileEntityRoastingOven ROASTING_OVEN;
    public static MetaTileEntitySpiralSeparator SPIRAL_SEPARATOR;


    //Brick MTES
    public static String[] BRICK_MTES = {"_blast_furnace", "_advanced_blast_furnace", "_import_fluid_hatch", "_export_fluid_hatch", "_export_item_bus", "_import_item_bus", "_muffler", "_single_crucible"};
    public static MetaTileEntityTKCYABlastFurnace[] BLAST_FURNACE = new MetaTileEntityTKCYABlastFurnace[BRICKS.size()];
    public static MetaTileEntityAdvancedBlastFurnace[] ADVANCED_BLAST_FURNACE = new MetaTileEntityAdvancedBlastFurnace[BRICKS.size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_IMPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS.size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_EXPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS.size()];
    public static MetaTileEntityBrickItemBus[] BRICK_EXPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityBrickItemBus[] BRICK_IMPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityPrimitiveMufflerHatch[] PRIMITIVE_MUFFLER = new MetaTileEntityPrimitiveMufflerHatch[BRICKS.size()];
    public static MetaTileEntitySingleCrucible[] SINGLE_CRUCIBLE = new MetaTileEntitySingleCrucible[BRICKS.size()];

    // Drums
    public static MetaTileEntityDrum[] DRUMS = new MetaTileEntityDrum[DRUM_MATERIALS.size()];



    //Tanks
    public static TKCYAMetaTileEntityMultiblockTank WOODEN_TANK;
    public static TKCYAMetaTileEntityMultiblockTank STEEL_TANK;
    public static TKCYAMetaTileEntityMultiblockTank GALVANIZED_STEEL_TANK;
    public static TKCYAMetaTileEntityMultiblockTank STAINLESS_STEEL_TANK;
    public static TKCYAMetaTileEntityTankValve STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve GALVANIZED_STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve STAINLESS_STEEL_TANK_VALVE;


    public static void init() {

        int startId = 11000;

        //id 11000-11004
        if (TKCYAConfigHolder.miscOverhaul.enableFoilOverhaul) {
            registerSimpleMetaTileEntity(CLUSTER_MILL, startId, "cluster_mill", TKCYARecipeMaps.CLUSTER_MILL_RECIPES,
                    TKCYATextures.CLUSTER_MILL_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        startId += CLUSTER_MILL.length + 1;

        //id 11005-11009
        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            registerSimpleMetaTileEntity(ADVANCED_POLARIZER, startId, "advanced_polarizer", TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES,
                    TKCYATextures.ADVANCED_POLARIZER_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        startId += ADVANCED_POLARIZER.length + 1;

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            PRIMITIVE_MELTER = registerMetaTileEntity(startId++, new MetaTileEntityPrimitiveMelter(tkcyaId("primitive_melter")));
            PRIMITIVE_FERMENTER = registerMetaTileEntity(startId++, new MetaTileEntityPrimitiveFermenter(tkcyaId("primitive_fermenter")));
        }

        startId = Math.max(startId, startId + 3);

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            ALLOYING_CRUCIBLE = registerMetaTileEntity(startId++, new MetaTileEntityAlloyingCrucible(tkcyaId("alloying_crucible")));
        }

        startId = Math.max(startId, startId + 2);

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            CASTING_TABLE = registerMetaTileEntity(startId++, new MetaTileEntityCastingTable(tkcyaId("casting_table")));

            registerSimpleMetaTileEntity(ELECTRIC_CASTING_TABLE, startId++, "electric_casting_table", TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES,
                    TKCYATextures.CASTING_TABLE_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);

            startId += ELECTRIC_CASTING_TABLE.length + 1;

            STEAM_COOLER_BRONZE = registerMetaTileEntity(startId++, new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_bronze"), false));
            STEAM_COOLER_STEEL = registerMetaTileEntity(startId++, new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_steel"), true));

        }

        startId = Math.max(startId, startId + ELECTRIC_CASTING_TABLE.length + 3);

        if (TKCYAConfigHolder.meltingOverhaul.enableBlastingOverhaul) {
            CONVERTER = registerMetaTileEntity(startId++, new MetaTileEntityElectricConverter(tkcyaId("converter")));
            PRIMITIVE_CONVERTER = registerMetaTileEntity(startId++, new MetaTileEntityPrimitiveConverter(tkcyaId("primitive_converter")));
            COKE_OVEN = registerMetaTileEntity(startId++, new MetaTileEntityTKCYACokeOven(tkcyaId("coke_oven")));


            for (int i = 0; i < BRICKS.size(); i++) {
                int j = 0;
                int finalI = i;
                int finalStartId = startId;

                BlockBrick.BrickType brick = BRICKS.get(i);

                IntFunction<String> setName = (k) -> brick.getName() + BRICK_MTES[k];
                IntFunction<Integer> setId = (id) -> finalStartId + finalI + BRICKS.size() * id;

                BLAST_FURNACE[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityTKCYABlastFurnace(tkcyaId(setName.apply(j++)), brick));
                ADVANCED_BLAST_FURNACE[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityAdvancedBlastFurnace(tkcyaId(setName.apply(j++)), brick));
                BRICK_IMPORT_FLUID_HATCH[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityBrickFluidHatch(tkcyaId(setName.apply(j++)), false, brick));
                BRICK_EXPORT_FLUID_HATCH[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityBrickFluidHatch(tkcyaId(setName.apply(j++)), true, brick));
                BRICK_EXPORT_ITEM_BUS[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityBrickItemBus(tkcyaId(setName.apply(j++)), true, brick));
                BRICK_IMPORT_ITEM_BUS[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityBrickItemBus(tkcyaId(setName.apply(j++)), false, brick));
                PRIMITIVE_MUFFLER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityPrimitiveMufflerHatch(tkcyaId(setName.apply(j++)), brick));
                SINGLE_CRUCIBLE[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntitySingleCrucible(tkcyaId(setName.apply(j)), brick));

            }
        }

        startId = Math.max(startId, startId + 3 + BRICK_MTES.length * BRICKS.size());

        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            PRIMITIVE_BATH = registerMetaTileEntity(startId++, new MetaTileEntityPrimitiveBath(tkcyaId("primitive_bath")));
        }

        startId = Math.max(startId, startId + 1);

        if (TKCYAConfigHolder.miscOverhaul.enableElectrolysisOverhaul) {
            ADVANCED_ELECTROLYZER = registerMetaTileEntity(startId++, new MetaTileEntityAdvancedElectrolyzer(tkcyaId("advanced_electrolyzer")));
        }

        startId = Math.max(startId, startId + 1);

        //id 11026-11036
        if (TKCYAConfigHolder.storageOverhaul.enableDrumsOverhaul) {
            for (int i = 0; i < DRUM_MATERIALS.size(); i++) {
                Material m = DRUM_MATERIALS.get(i);
                DRUMS[i] = registerMetaTileEntity(startId + i, new MetaTileEntityDrum(tkcyaId("drum." + m.getUnlocalizedName()), m, 16000));
            }
        }

        startId = Math.max(startId, startId + DRUM_MATERIALS.size() + 1);

        if (TKCYAConfigHolder.storageOverhaul.enableMultiblockTanksOverhaul) {

            WOODEN_TANK = registerMetaTileEntity(startId++, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.wood"),
                    Materials.TreatedWood,
                    MetaBlocks.STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.WOOD_WALL),
                    250000));
            STEEL_TANK = registerMetaTileEntity(startId++, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.steel"),
                    Materials.Steel,
                    TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STEEL_WALL),
                    250000));
            GALVANIZED_STEEL_TANK = registerMetaTileEntity(startId++, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.galvanized_steel"),
                    TKCYAMaterials.GalvanizedSteel,
                    TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL),
                    250000));
            STAINLESS_STEEL_TANK = registerMetaTileEntity(startId++, new TKCYAMetaTileEntityMultiblockTank(tkcyaId("multiblock.tank.stainless_steel"),
                    Materials.StainlessSteel,
                    TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL),
                    250000));

            STEEL_TANK_VALVE = registerMetaTileEntity(startId++, new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.steel"),
                    Materials.Steel));
            GALVANIZED_STEEL_TANK_VALVE = registerMetaTileEntity(startId++, new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.galvanized_steel"),
                    TKCYAMaterials.GalvanizedSteel));
            STAINLESS_STEEL_TANK_VALVE = registerMetaTileEntity(startId++, new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.stainless_steel"),
                    Materials.StainlessSteel));
        }

        startId = Math.max(startId, startId + 7);

        if (TKCYAConfigHolder.meltingOverhaul.enableBouleCrystallization) {
            CRYSTALLIZER = registerMetaTileEntity(startId++, new MetaTileEntityCrystallizer(tkcyaId("crystallizer")));
        }

        startId = Math.max(startId, startId + 1);

        FILTER = registerMetaTileEntity(startId++, new MetaTileEntityFilter(tkcyaId("filter")));

        if (TKCYAConfigHolder.crackingOverhaul.enableCrackingOverhaul) {
            PRESSURIZED_CRACKING_UNIT = registerMetaTileEntity(startId++, new MetaTileEntityPressurizedCrackingUnit(tkcyaId("pressurized_cracking_unit")));
        }

        startId = Math.max(startId, startId + 1);

        ROASTING_OVEN = registerMetaTileEntity(startId++, new MetaTileEntityRoastingOven(tkcyaId("roasting_oven")));
        SPIRAL_SEPARATOR = registerMetaTileEntity(startId++, new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));
        STEAM_AIR_COMPRESSOR = registerMetaTileEntity(startId++, new MetaTileEntitySteamAirCompressor(tkcyaId("steam_air_compressor")));
        
        ROASTING_OVEN = registerMetaTileEntity(startId++, new MetaTileEntityRoastingOven(tkcyaId("roasting_oven")));
        SPIRAL_SEPARATOR = registerMetaTileEntity(startId++, new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));


        //id 11177 to 11194
        SOLID_FUEL_HEATER[0] = registerMetaTileEntity(startId++, new MetaTileEntitySolidFuelHeater(tkcyaId( "brick_solid_fuel_heater"), BRICK));

        for (int i = 1; i < FUEL_HEATERS.size(); i++) {
            int j = 0;
            int finalI = i;
            int finalStartId1 = startId;

            FuelHeaterTiers fuelHeater = FUEL_HEATERS.get(i);

            IntFunction<String> setName = (k) -> fuelHeater.getMaterial().getUnlocalizedName() + FUEL_HEATERS_TYPES[k];
            IntFunction<Integer> setId = (id) -> finalStartId1 + finalI + FUEL_HEATERS.size() * id;

            SOLID_FUEL_HEATER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntitySolidFuelHeater(tkcyaId(setName.apply(j++)), fuelHeater));
            LIQUID_FUEL_HEATER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityLiquidFuelHeater(tkcyaId(setName.apply(j++)), fuelHeater));
            FLUIDIZED_FUEL_HEATER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityFluidizedHeater(tkcyaId(setName.apply(j++)), fuelHeater));
            GAS_FUEL_HEATER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityGasHeater(tkcyaId(setName.apply(j)), fuelHeater));
        }

        startId = Math.max(startId, startId + FUEL_HEATERS.size() * FUEL_HEATERS_TYPES.length + 1);

        STEAM_AUTOCLAVE = registerMetaTileEntity(startId++, new MetaTileEntitySteamAutoclave(tkcyaId("steam_autoclave"), true));

        for (int i = 0; i < GTValues.LuV; i++) {
            int j = 0;
            int tier= i;
            int finalStartId2 = startId;

            IntFunction<String> setHatchName = (k) -> PRESSURE_HATCHES_TYPE[k] + GTValues.VN[tier].toLowerCase();
            IntFunction<Integer> setId = (id) -> finalStartId2 + tier + GTValues.LuV * id;

            VACUUM_HATCH[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityVacuumHatch(tkcyaId(setHatchName.apply(0)), i));
            PRESSURE_HATCH[i] = registerMetaTileEntity(setId.apply(++j), new MetaTileEntityPressureHatch(tkcyaId(setHatchName.apply(1)), i));
        }

        startId += PRESSURE_HATCH.length * 2 + 1;

        for (int i = 0; i < MAX_TIER; i++) {
            int j = 0;
            int tier = i;
            int finalStartId = startId;

            IntFunction<String> setName = (x) -> HEAT_MTES[x] + GTValues.VN[tier].toLowerCase();
            IntFunction<String> setCompressorName = (type) -> ELECTRIC_PRESSURE_COMPRESSORS_TYPE[type] + GTValues.VN[tier].toLowerCase();
            IntFunction<Integer> setId = (id) -> finalStartId + tier + MAX_TIER * id;

            ELECTRIC_HEATER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityElectricHeater(tkcyaId(setName.apply(j++)), i));
            ELECTRIC_COOLER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityElectricCooler(tkcyaId(setName.apply(j++)), i));
            HEAT_ACCEPTOR[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityHeatAcceptor(tkcyaId(setName.apply(j++)), i));
            VACUUM_PUMP[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityElectricVacuumPump(tkcyaId(setCompressorName.apply(0)), i));
            PRESSURE_COMPRESSOR[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityElectricPressureCompressor(tkcyaId(setCompressorName.apply(1)), i));
        }

        startId += MAX_TIER * 5 + 1;


    }

    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }


}
