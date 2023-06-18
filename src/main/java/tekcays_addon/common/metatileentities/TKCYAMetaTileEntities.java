package tekcays_addon.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.common.metatileentities.storage.MetaTileEntityDrum;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityController;
import tekcays_addon.common.metatileentities.primitive.MetaTileEntityAxeSupport;
import tekcays_addon.common.metatileentities.single.electric.*;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.gtapi.utils.FuelHeaterTiers;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.metatileentities.multi.*;

import tekcays_addon.common.metatileentities.multiblockpart.*;
import tekcays_addon.common.metatileentities.single.*;

import tekcays_addon.common.metatileentities.single.MetaTileEntitySingleCrucible;
import tekcays_addon.common.metatileentities.single.electric.MetaTileEntityElectricCooler;
import tekcays_addon.common.metatileentities.single.electric.MetaTileEntityElectricHeater;
import tekcays_addon.common.metatileentities.single.electric.MetaTileEntityElectricPressureCompressor;
import tekcays_addon.common.metatileentities.single.electric.MetaTileEntityElectricVacuumPump;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntityFluidizedHeater;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntityGasHeater;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntityLiquidFuelHeater;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntitySolidFuelHeater;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamAutoclave;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamCooler;
import tekcays_addon.gtapi.utils.TKCYALog;

import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static tekcays_addon.api.consts.ContainerControllerWrappers.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.gtapi.render.TKCYATextures.STEAM_CASING;
import static tekcays_addon.api.metatileentity.predicates.BrickHatchesPredicates.BRICKS;
import static tekcays_addon.gtapi.utils.FuelHeaterTiers.BRICK;
import static tekcays_addon.gtapi.utils.FuelHeaterTiers.FUEL_HEATERS;
import static tekcays_addon.gtapi.consts.TKCYAValues.DRUM_MATERIALS;

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

    //DECOMPRESSORS
    public static MetaTileEntityDecompressor[] DECOMPRESSOR = new MetaTileEntityDecompressor[MAX_TIER];
    public static MetaTileEntityDecompressionHatch[] DECOMPRESSION_HATCH = new MetaTileEntityDecompressionHatch[MAX_TIER];

    //ELECTRIC PRESSURE COMPRESSOR
    public static int PRESSURE_COMPRESSOR_SINGLE_MAX_TIER = GTValues.IV;
    public static String[] ELECTRIC_PRESSURE_COMPRESSORS_TYPE = {"electric_vacuum_pump.", "electric_compressor."};
    public static MetaTileEntityElectricPressureCompressor[] PRESSURE_COMPRESSOR = new MetaTileEntityElectricPressureCompressor[PRESSURE_COMPRESSOR_SINGLE_MAX_TIER + 1];
    public static MetaTileEntityElectricVacuumPump[] VACUUM_PUMP = new MetaTileEntityElectricVacuumPump[PRESSURE_COMPRESSOR_SINGLE_MAX_TIER + 1];


    //ELECTRIC FORMER COVERS
    public static MetaTileEntityElectricPump[] ELECTRIC_PUMPS = new MetaTileEntityElectricPump[MAX_TIER];
    public static MetaTileEntityElectricConveyor[] ELECTRIC_CONVEYORS = new MetaTileEntityElectricConveyor[MAX_TIER];

    public static MetaTileEntityPrimitiveMelter PRIMITIVE_MELTER;
    public static MetaTileEntityPrimitiveFermenter PRIMITIVE_FERMENTER;
    public static MetaTileEntityPrimitiveConverter PRIMITIVE_CONVERTER;
    public static MetaTileEntityAlloyingCrucible ALLOYING_CRUCIBLE;
    public static MetaTileEntitySteamCooler STEAM_COOLER_BRONZE;
    public static MetaTileEntitySteamCooler STEAM_COOLER_STEEL;
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

    public static MetaTileEntitySteamTurbine[] STEAM_TURBINE = new MetaTileEntitySteamTurbine[STEAM_CASING.length];
    public static MetaTileEntityRotationCompressor[] ROTATION_COMPRESSOR = new MetaTileEntityRotationCompressor[STEAM_CASING.length];
    public static MetaTileEntityDynamo[] DYNAMOS = new MetaTileEntityDynamo[MAX_TIER];
    public static MetaTileEntityDieselGenerator[] DIESEL_GENERATOR = new MetaTileEntityDieselGenerator[MAX_TIER];

    public static MetaTileEntityRoastingOven ROASTING_OVEN;
    public static MetaTileEntitySpiralSeparator SPIRAL_SEPARATOR;
    public static MetaTileEntityPrimitiveFurnace PRIMITIVE_FURNACE;


    //Brick MTES
    public static String[] BRICK_MTES = {"_blast_furnace", "_advanced_blast_furnace", "_import_fluid_hatch", "_export_fluid_hatch", "_export_item_bus", "_import_item_bus", "_casting_bus", "_casting_fluid_input", "_muffler", "_single_crucible", "_casting_table"};
    public static MetaTileEntityTKCYABlastFurnace[] BLAST_FURNACE = new MetaTileEntityTKCYABlastFurnace[BRICKS.size()];
    public static MetaTileEntityAdvancedBlastFurnace[] ADVANCED_BLAST_FURNACE = new MetaTileEntityAdvancedBlastFurnace[BRICKS.size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_IMPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS.size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_EXPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS.size()];
    public static MetaTileEntityBrickItemBus[] BRICK_EXPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityBrickItemBus[] BRICK_IMPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityBrickCastingBus[] BRICK_CASTING_BUS = new MetaTileEntityBrickCastingBus[BRICKS.size()];
    public static MetaTileEntityBrickCastingFluidInput[] BRICK_CASTING_FLUID_INPUT = new MetaTileEntityBrickCastingFluidInput[BRICKS.size()];
    public static MetaTileEntityPrimitiveMufflerHatch[] PRIMITIVE_MUFFLER = new MetaTileEntityPrimitiveMufflerHatch[BRICKS.size()];
    public static MetaTileEntitySingleCrucible[] SINGLE_CRUCIBLE = new MetaTileEntitySingleCrucible[BRICKS.size()];
    public static MetaTileEntityCastingTable[] CASTING_TABLE = new MetaTileEntityCastingTable[BRICKS.size()];

    // Drums
    public static MetaTileEntityDrum[] DRUMS = new MetaTileEntityDrum[DRUM_MATERIALS.size()];

    //Controls
    public static MetaTileEntityController MULTIBLOCK_PRESSURE_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_VACUUM_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_TEMPERATURE_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_SPEED_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_TORQUE_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_ROTATION_POWER_DETECTOR;



    //Tanks
    public static TKCYAMetaTileEntityTankValve STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve GALVANIZED_STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve STAINLESS_STEEL_TANK_VALVE;

    public static MetaTileEntityModulableMultiblockTank[] MODULABLE_TANK = new MetaTileEntityModulableMultiblockTank[4];
    public static MetaTileEntityPressurizedMultiblockTank[] PRESSURIZED_TANK = new MetaTileEntityPressurizedMultiblockTank[3];

    //Primitive
    public static MetaTileEntityAxeSupport AXE_SUPPORT;


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
                BRICK_CASTING_BUS[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityBrickCastingBus(tkcyaId(setName.apply(j++)), brick));
                BRICK_CASTING_FLUID_INPUT[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityBrickCastingFluidInput(tkcyaId(setName.apply(j++)), false, brick));
                PRIMITIVE_MUFFLER[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityPrimitiveMufflerHatch(tkcyaId(setName.apply(j++)), brick));
                SINGLE_CRUCIBLE[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntitySingleCrucible(tkcyaId(setName.apply(j++)), brick));
                CASTING_TABLE[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityCastingTable(tkcyaId(setName.apply(j)), brick));

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

        ROASTING_OVEN = registerMetaTileEntity(startId++, new MetaTileEntityRoastingOven(tkcyaId("roasting_oven")));
        SPIRAL_SEPARATOR = registerMetaTileEntity(startId++, new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));
        PRIMITIVE_FURNACE = registerMetaTileEntity(startId++, new MetaTileEntityPrimitiveFurnace(tkcyaId("primitive_furnace")));


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

        MULTIBLOCK_PRESSURE_DETECTOR = registerMetaTileEntity(startId++, new MetaTileEntityController(tkcyaId("pressure_controller"), CONTROL_PRESSURE_DETECTOR));
        MULTIBLOCK_VACUUM_DETECTOR = registerMetaTileEntity(startId++, new MetaTileEntityController(tkcyaId("vacuum_controller"), CONTROL_VACUUM_DETECTOR));
        MULTIBLOCK_TEMPERATURE_DETECTOR = registerMetaTileEntity(startId++, new MetaTileEntityController(tkcyaId("temperature_controller"), CONTROL_TEMPERATURE_DETECTOR));
        MULTIBLOCK_SPEED_DETECTOR = registerMetaTileEntity(startId++, new MetaTileEntityController(tkcyaId("speed_controller"), CONTROL_SPEED_DETECTOR));
        MULTIBLOCK_TORQUE_DETECTOR = registerMetaTileEntity(startId++, new MetaTileEntityController(tkcyaId("torque_controller"), CONTROL_TORQUE_DETECTOR));
        MULTIBLOCK_ROTATION_POWER_DETECTOR = registerMetaTileEntity(startId++, new MetaTileEntityController(tkcyaId("rotation_power_controller"), CONTROL_ROTATION_POWER_DETECTOR));

        IntStream.range(0, STEAM_TURBINE.length)
                .forEach(i -> STEAM_TURBINE[i] = registerMetaTileEntity(12000 + i, new MetaTileEntitySteamTurbine(tkcyaId("steam_turbine." + i), i)));

        IntStream.range(0, ROTATION_COMPRESSOR.length )
                .forEach(i -> ROTATION_COMPRESSOR[i] = registerMetaTileEntity(12010 + i, new MetaTileEntityRotationCompressor(tkcyaId("rotation_compressor." + i), i)));

        IntStream.range(0, DYNAMOS.length )
                .forEach(i -> DYNAMOS[i] = registerMetaTileEntity(12020 + i, new MetaTileEntityDynamo(tkcyaId("dynamo." + i), i)));

        IntStream.range(0, DIESEL_GENERATOR.length )
                .forEach(i -> DIESEL_GENERATOR[i] = registerMetaTileEntity(12030 + i, new MetaTileEntityDieselGenerator(tkcyaId("diesel_generator." + i), i)));

        TKCYALog.logger.info("id : {}", startId);

        if (TKCYAConfigHolder.miscOverhaul.enableModulableTanks) {
            MODULABLE_TANK[0] = registerMetaTileEntity(12025, new MetaTileEntityModulableMultiblockTank(tkcyaId("modulable_wood_tank"), TreatedWood, 1000 * 1000));
            MODULABLE_TANK[1] = registerMetaTileEntity(12026, new MetaTileEntityModulableMultiblockTank(tkcyaId("modulable_steel_tank"), Steel, 1000 * 1000));
            MODULABLE_TANK[2] = registerMetaTileEntity(12027, new MetaTileEntityModulableMultiblockTank(tkcyaId("modulable_galvanized_steel_tank"), TKCYAMaterials.GalvanizedSteel, 1000 * 1000));
            MODULABLE_TANK[3] = registerMetaTileEntity(12028, new MetaTileEntityModulableMultiblockTank(tkcyaId("modulable_stainless_steel_tank"), StainlessSteel, 1000 * 1000));

            PRESSURIZED_TANK[0] = registerMetaTileEntity(12040, new MetaTileEntityPressurizedMultiblockTank(tkcyaId("pressurized_steel_tank"), Steel, 10 * ATMOSPHERIC_PRESSURE));
            PRESSURIZED_TANK[1] = registerMetaTileEntity(12041, new MetaTileEntityPressurizedMultiblockTank(tkcyaId("pressurized_galvanized_steel_tank"), TKCYAMaterials.GalvanizedSteel, 50 * ATMOSPHERIC_PRESSURE));
            PRESSURIZED_TANK[2] = registerMetaTileEntity(12042, new MetaTileEntityPressurizedMultiblockTank(tkcyaId("pressurized_stainless_steel_tank"), StainlessSteel, 100 * ATMOSPHERIC_PRESSURE));
        }

        IntStream.range(0, MAX_TIER)
                .forEach(i -> DECOMPRESSOR[i] = registerMetaTileEntity(12050 + i, new MetaTileEntityDecompressor(tkcyaId("decompressor." + i), i)));

        IntStream.range(0, MAX_TIER)
                .forEach(i -> DECOMPRESSION_HATCH[i] = registerMetaTileEntity(12060 + i, new MetaTileEntityDecompressionHatch(tkcyaId("decompression_hatch." + i), i)));

        IntStream.range(0, MAX_TIER)
                .forEach(i -> ELECTRIC_PUMPS[i] = registerMetaTileEntity(12070 + i, new MetaTileEntityElectricPump(tkcyaId("electric_pump." + i), i)));

        IntStream.range(0, MAX_TIER)
                .forEach(i -> ELECTRIC_CONVEYORS[i] = registerMetaTileEntity(12080 + i, new MetaTileEntityElectricConveyor(tkcyaId("electric_conveyor." + i), i)));

        AXE_SUPPORT = registerMetaTileEntity(12090, new MetaTileEntityAxeSupport(tkcyaId("axe_support")));
    }

    static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }


}
