package tekcays_addon.common.metatileentities;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static gregtech.common.metatileentities.MetaTileEntities.registerSimpleMetaTileEntity;
import static tekcays_addon.api.consts.ContainerControllerWrappers.*;
import static tekcays_addon.api.metatileentity.predicates.BrickHatchesPredicates.BRICKS;
import static tekcays_addon.gtapi.consts.TKCYAValues.*;
import static tekcays_addon.gtapi.render.TKCYATextures.STEAM_CASING;
import static tekcays_addon.gtapi.utils.FuelHeaterTiers.BRICK;
import static tekcays_addon.gtapi.utils.FuelHeaterTiers.FUEL_HEATERS;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

import net.minecraft.util.ResourceLocation;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.common.metatileentities.storage.MetaTileEntityDrum;
import tekcays_addon.api.utils.TriFunction;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.metatileentities.multi.*;
import tekcays_addon.common.metatileentities.multi.electric.MetaTileEntityAdvancedElectrolyzer;
import tekcays_addon.common.metatileentities.multi.electric.MetaTileEntityMultiAmperageTest;
import tekcays_addon.common.metatileentities.multi.primitive.*;
import tekcays_addon.common.metatileentities.multi.storage.MetaTileEntityModulableMultiblockCrate;
import tekcays_addon.common.metatileentities.multi.storage.MetaTileEntityModulableMultiblockTank;
import tekcays_addon.common.metatileentities.multi.storage.MetaTileEntityModulablePressurizedMultiblockTank;
import tekcays_addon.common.metatileentities.multiblockpart.brick.*;
import tekcays_addon.common.metatileentities.multiblockpart.capabilities.MetaTileEntityDecompressionHatch;
import tekcays_addon.common.metatileentities.multiblockpart.capabilities.MetaTileEntityHeatAcceptor;
import tekcays_addon.common.metatileentities.multiblockpart.capabilities.MetaTileEntityPressureHatch;
import tekcays_addon.common.metatileentities.multiblockpart.capabilities.MetaTileEntityVacuumHatch;
import tekcays_addon.common.metatileentities.multiblockpart.controllers.MetaTileEntityController;
import tekcays_addon.common.metatileentities.multiblockpart.storage.MetaTileEntityCrateValve;
import tekcays_addon.common.metatileentities.multiblockpart.storage.TKCYAMetaTileEntityTankValve;
import tekcays_addon.common.metatileentities.primitive.MetaTileEntityAxeSupport;
import tekcays_addon.common.metatileentities.single.*;
import tekcays_addon.common.metatileentities.single.electric.*;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntityFluidizedHeater;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntityGasHeater;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntityLiquidFuelHeater;
import tekcays_addon.common.metatileentities.single.heaters.MetaTileEntitySolidFuelHeater;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamAutoclave;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamCooler;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamCrusher;
import tekcays_addon.gtapi.consts.TKCYAValues;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.gtapi.utils.FuelHeaterTiers;

public class TKCYAMetaTileEntities {

    public static int MAX_TIER = GTValues.IV;
    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[MAX_TIER];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[MAX_TIER];
    public static SimpleMachineMetaTileEntity[] ROLLING_MACHINE = new SimpleMachineMetaTileEntity[MAX_TIER];
    public static SimpleMachineMetaTileEntity[] ELECTRIC_CASTING_TABLE = new SimpleMachineMetaTileEntity[MAX_TIER];

    // HEAT STUFF
    public static String[] HEAT_MTES = { "electric_heater.", "electric_cooler.", "heat_acceptor." };
    public static MetaTileEntityElectricHeater[] ELECTRIC_HEATER = new MetaTileEntityElectricHeater[MAX_TIER];
    public static MetaTileEntityElectricCooler[] ELECTRIC_COOLER = new MetaTileEntityElectricCooler[MAX_TIER];
    public static MetaTileEntityHeatAcceptor[] HEAT_ACCEPTOR = new MetaTileEntityHeatAcceptor[MAX_TIER];

    // PRESSURE HATCHES
    public static int PRESSURE_HATCH_MAX_TIER = GTValues.LuV;
    public static String[] PRESSURE_HATCHES_TYPE = { "vacuum_hatch.", "pressure_hatch." };
    public static MetaTileEntityPressureHatch[] PRESSURE_HATCH = new MetaTileEntityPressureHatch[PRESSURE_HATCH_MAX_TIER +
            1];
    public static MetaTileEntityVacuumHatch[] VACUUM_HATCH = new MetaTileEntityVacuumHatch[PRESSURE_HATCH_MAX_TIER + 1];

    // DECOMPRESSORS
    public static MetaTileEntityDecompressor[] DECOMPRESSOR = new MetaTileEntityDecompressor[MAX_TIER];
    public static MetaTileEntityDecompressionHatch[] DECOMPRESSION_HATCH = new MetaTileEntityDecompressionHatch[MAX_TIER];

    // ELECTRIC PRESSURE COMPRESSOR
    public static int PRESSURE_COMPRESSOR_SINGLE_MAX_TIER = GTValues.IV;
    public static String[] ELECTRIC_PRESSURE_COMPRESSORS_TYPE = { "electric_vacuum_pump.", "electric_compressor." };
    public static MetaTileEntityElectricPressureCompressor[] PRESSURE_COMPRESSOR = new MetaTileEntityElectricPressureCompressor[PRESSURE_COMPRESSOR_SINGLE_MAX_TIER +
            1];
    public static MetaTileEntityElectricVacuumPump[] VACUUM_PUMP = new MetaTileEntityElectricVacuumPump[PRESSURE_COMPRESSOR_SINGLE_MAX_TIER +
            1];

    // ELECTRIC FORMER COVERS
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
    public static MetaTileEntitySteamCrusher STEAM_CRUSHER;
    public static MetaTileEntitySolidFuelHeater BRICK_FUEL_HEATER;
    public static MetaTileEntitySolidFuelHeater[] SOLID_FUEL_HEATER = new MetaTileEntitySolidFuelHeater[FUEL_HEATERS
            .size()];
    public static MetaTileEntityLiquidFuelHeater[] LIQUID_FUEL_HEATER = new MetaTileEntityLiquidFuelHeater[FUEL_HEATERS
            .size()];
    public static MetaTileEntityFluidizedHeater[] FLUIDIZED_FUEL_HEATER = new MetaTileEntityFluidizedHeater[FUEL_HEATERS
            .size()];
    public static MetaTileEntityGasHeater[] GAS_FUEL_HEATER = new MetaTileEntityGasHeater[FUEL_HEATERS.size()];

    public static MetaTileEntitySteamTurbine[] STEAM_TURBINE = new MetaTileEntitySteamTurbine[STEAM_CASING.length];
    public static MetaTileEntityRotationCompressor[] ROTATION_COMPRESSOR = new MetaTileEntityRotationCompressor[STEAM_CASING.length];
    public static MetaTileEntityDynamo[] DYNAMOS = new MetaTileEntityDynamo[MAX_TIER];
    public static MetaTileEntityDieselGenerator[] DIESEL_GENERATOR = new MetaTileEntityDieselGenerator[MAX_TIER];
    public static MetaTileEntitySpiralSeparator SPIRAL_SEPARATOR;
    public static MetaTileEntityPrimitiveFurnace PRIMITIVE_FURNACE;
    public static MetaTileEntityMultiAmperageTest MULTI_AMPERAGE_TEST;

    // Brick MTES
    public static String[] BRICK_MTES = { "_blast_furnace", "_advanced_blast_furnace", "_import_fluid_hatch",
            "_export_fluid_hatch", "_export_item_bus", "_import_item_bus", "_casting_bus", "_casting_fluid_input",
            "_muffler", "_single_crucible", "_casting_table" };
    public static MetaTileEntityTKCYABlastFurnace[] BLAST_FURNACE = new MetaTileEntityTKCYABlastFurnace[BRICKS.size()];
    public static MetaTileEntityAdvancedBlastFurnace[] ADVANCED_BLAST_FURNACE = new MetaTileEntityAdvancedBlastFurnace[BRICKS
            .size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_IMPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS
            .size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_EXPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS
            .size()];
    public static MetaTileEntityBrickItemBus[] BRICK_EXPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityBrickItemBus[] BRICK_IMPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityBrickCastingFluidInput[] BRICK_CASTING_FLUID_INPUT = new MetaTileEntityBrickCastingFluidInput[BRICKS
            .size()];
    public static MetaTileEntityPrimitiveMufflerHatch[] PRIMITIVE_MUFFLER = new MetaTileEntityPrimitiveMufflerHatch[BRICKS
            .size()];
    public static MetaTileEntitySingleCrucible[] SINGLE_CRUCIBLE = new MetaTileEntitySingleCrucible[BRICKS.size()];
    public static MetaTileEntityPrimitiveRoastOven[] BRICK_ROASTING_OVEN = new MetaTileEntityPrimitiveRoastOven[BRICKS
            .size()];

    // Drums
    public static MetaTileEntityDrum[] DRUMS = new MetaTileEntityDrum[DRUM_MATERIALS.size()];

    // Controls
    public static MetaTileEntityController MULTIBLOCK_PRESSURE_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_VACUUM_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_TEMPERATURE_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_SPEED_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_TORQUE_DETECTOR;
    public static MetaTileEntityController MULTIBLOCK_ROTATION_POWER_DETECTOR;

    // Tanks
    public static TKCYAMetaTileEntityTankValve STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve GALVANIZED_STEEL_TANK_VALVE;
    public static TKCYAMetaTileEntityTankValve STAINLESS_STEEL_TANK_VALVE;

    public static MetaTileEntityCrateValve TREATED_WOOD_CRATE_VALVE;
    public static MetaTileEntityCrateValve STEEL_CRATE_VALVE;
    public static MetaTileEntityCrateValve GALVANIZED_STEEL_CRATE_VALVE;
    public static MetaTileEntityCrateValve STAINLESS_STEEL_CRATE_VALVE;

    public static MetaTileEntityModulableMultiblockTank[] MODULABLE_TANK = new MetaTileEntityModulableMultiblockTank[4];
    public static MetaTileEntityModulablePressurizedMultiblockTank[] PRESSURIZED_TANK = new MetaTileEntityModulablePressurizedMultiblockTank[3];
    public static MetaTileEntityModulableMultiblockCrate[] MODULABLE_CRATE = new MetaTileEntityModulableMultiblockCrate[4];

    // Primitive
    public static MetaTileEntityAxeSupport AXE_SUPPORT;

    // LOGISTIC
    public static MetaTileEntityLogisticMachine LOGISTIC_MACHINE;

    public static void init() {
        if (TKCYAConfigHolder.meltingOverhaul.enableBlastingOverhaul) {
            CONVERTER = registerMetaTileEntity(11000, new MetaTileEntityElectricConverter(tkcyaId("converter")));
            PRIMITIVE_CONVERTER = registerMetaTileEntity(11001,
                    new MetaTileEntityPrimitiveConverter(tkcyaId("primitive_converter")));
            COKE_OVEN = registerMetaTileEntity(11002, new MetaTileEntityTKCYACokeOven(tkcyaId("coke_oven")));

            registerBrickMTE("_blast_furnace", BLAST_FURNACE, 11003, MetaTileEntityTKCYABlastFurnace::new);
            registerBrickMTE("_advanced_blast_furnace", ADVANCED_BLAST_FURNACE, 11007,
                    MetaTileEntityAdvancedBlastFurnace::new);
            registerBrickMTE("_muffler", PRIMITIVE_MUFFLER, 11011, MetaTileEntityPrimitiveMufflerHatch::new);
            registerBrickMTE("_single_crucible", SINGLE_CRUCIBLE, 11015, MetaTileEntitySingleCrucible::new);
            registerBrickMTE("_casting_fluid_input", BRICK_CASTING_FLUID_INPUT, 11027,
                    MetaTileEntityBrickCastingFluidInput::new, false);
            registerBrickMTE("_import_fluid_hatch", BRICK_IMPORT_FLUID_HATCH, 11031, MetaTileEntityBrickFluidHatch::new,
                    false);
            registerBrickMTE("_export_fluid_hatch", BRICK_EXPORT_FLUID_HATCH, 11035, MetaTileEntityBrickFluidHatch::new,
                    true);
            registerBrickMTE("_import_item_bus", BRICK_IMPORT_ITEM_BUS, 11039, MetaTileEntityBrickItemBus::new, false);
            registerBrickMTE("_export_item_bus", BRICK_EXPORT_ITEM_BUS, 11043, MetaTileEntityBrickItemBus::new, true);
        }

        if (TKCYAConfigHolder.harderStuff.enableHarderFoil) {
            registerSimpleMetaTileEntity(CLUSTER_MILL, 11052, "cluster_mill", TKCYARecipeMaps.CLUSTER_MILL_RECIPES,
                    TKCYATextures.CLUSTER_MILL_OVERLAY, true, TKCYAValues::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        // id 11005-11009
        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            registerSimpleMetaTileEntity(ADVANCED_POLARIZER, 11057, "advanced_polarizer",
                    TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES,
                    TKCYATextures.ADVANCED_POLARIZER_OVERLAY, true, TKCYAValues::tkcyaId,
                    GTUtility.hvCappedTankSizeFunction);
        }

        registerSimpleMetaTileEntity(ROLLING_MACHINE, 11500, "rolling_machine", TKCYARecipeMaps.ROLLING_RECIPES,
                TKCYATextures.ROLLING_MACHINE_OVERLAY, true, TKCYAValues::tkcyaId, null);

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            PRIMITIVE_MELTER = registerMetaTileEntity(11062,
                    new MetaTileEntityPrimitiveMelter(tkcyaId("primitive_melter")));
            PRIMITIVE_FERMENTER = registerMetaTileEntity(11063,
                    new MetaTileEntityPrimitiveFermenter(tkcyaId("primitive_fermenter")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            ALLOYING_CRUCIBLE = registerMetaTileEntity(11064,
                    new MetaTileEntityAlloyingCrucible(tkcyaId("alloying_crucible")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {

            registerSimpleMetaTileEntity(ELECTRIC_CASTING_TABLE, 11065, "electric_casting_table",
                    TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES,
                    TKCYATextures.CASTING_TABLE_OVERLAY, true, TKCYAValues::tkcyaId,
                    GTUtility.hvCappedTankSizeFunction);

            STEAM_COOLER_BRONZE = registerMetaTileEntity(11070,
                    new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_bronze"), false));
            STEAM_COOLER_STEEL = registerMetaTileEntity(11075,
                    new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_steel"), true));

        }

        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            PRIMITIVE_BATH = registerMetaTileEntity(11080, new MetaTileEntityPrimitiveBath(tkcyaId("primitive_bath")));
        }

        if (TKCYAConfigHolder.miscOverhaul.enableElectrolysisOverhaul) {
            ADVANCED_ELECTROLYZER = registerMetaTileEntity(11081,
                    new MetaTileEntityAdvancedElectrolyzer(tkcyaId("advanced_electrolyzer")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableBouleCrystallization) {
            CRYSTALLIZER = registerMetaTileEntity(11090, new MetaTileEntityCrystallizer(tkcyaId("crystallizer")));
        }

        FILTER = registerMetaTileEntity(11091, new MetaTileEntityFilter(tkcyaId("filter")));

        if (TKCYAConfigHolder.crackingOverhaul.enableCrackingOverhaul) {
            PRESSURIZED_CRACKING_UNIT = registerMetaTileEntity(11092,
                    new MetaTileEntityPressurizedCrackingUnit(tkcyaId("pressurized_cracking_unit")));
        }

        SPIRAL_SEPARATOR = registerMetaTileEntity(11094,
                new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));
        PRIMITIVE_FURNACE = registerMetaTileEntity(11095,
                new MetaTileEntityPrimitiveFurnace(tkcyaId("primitive_furnace")));

        BRICK_FUEL_HEATER = registerMetaTileEntity(11096,
                new MetaTileEntitySolidFuelHeater(tkcyaId("brick_solid_fuel_heater"), BRICK));

        registerFuelTier("_solid_fuel_heater", SOLID_FUEL_HEATER, 11097, MetaTileEntitySolidFuelHeater::new);
        registerFuelTier("_liquid_fuel_heater", LIQUID_FUEL_HEATER, 11102, MetaTileEntityLiquidFuelHeater::new);
        registerFuelTier("_fluidized_fuel_heater", FLUIDIZED_FUEL_HEATER, 11107, MetaTileEntityFluidizedHeater::new);
        registerFuelTier("_gas_fuel_heater", GAS_FUEL_HEATER, 11112, MetaTileEntityGasHeater::new);

        STEAM_AUTOCLAVE = registerMetaTileEntity(11240,
                new MetaTileEntitySteamAutoclave(tkcyaId("steam_autoclave"), true));

        if (TKCYAConfigHolder.miscOverhaul.enableModulableTanks) {
            MODULABLE_TANK[0] = registerMetaTileEntity(11330, new MetaTileEntityModulableMultiblockTank(
                    tkcyaId("modulable_wood_tank"), TreatedWood, 1000 * 1000));
            MODULABLE_TANK[1] = registerMetaTileEntity(11331,
                    new MetaTileEntityModulableMultiblockTank(tkcyaId("modulable_steel_tank"), Steel, 1000 * 1000));
            MODULABLE_TANK[2] = registerMetaTileEntity(11332, new MetaTileEntityModulableMultiblockTank(
                    tkcyaId("modulable_galvanized_steel_tank"), TKCYAMaterials.GalvanizedSteel, 1000 * 1000));
            MODULABLE_TANK[3] = registerMetaTileEntity(11333, new MetaTileEntityModulableMultiblockTank(
                    tkcyaId("modulable_stainless_steel_tank"), StainlessSteel, 1000 * 1000));

            PRESSURIZED_TANK[0] = registerMetaTileEntity(11334, new MetaTileEntityModulablePressurizedMultiblockTank(
                    tkcyaId("pressurized_steel_tank"), Steel, 10 * ATMOSPHERIC_PRESSURE));
            PRESSURIZED_TANK[1] = registerMetaTileEntity(11335,
                    new MetaTileEntityModulablePressurizedMultiblockTank(tkcyaId("pressurized_galvanized_steel_tank"),
                            TKCYAMaterials.GalvanizedSteel, 50 * ATMOSPHERIC_PRESSURE));
            PRESSURIZED_TANK[2] = registerMetaTileEntity(11336, new MetaTileEntityModulablePressurizedMultiblockTank(
                    tkcyaId("pressurized_stainless_steel_tank"), StainlessSteel, 100 * ATMOSPHERIC_PRESSURE));
        }

        IntStream.range(0, MAX_TIER)
                .forEach(i -> DECOMPRESSOR[i] = registerMetaTileEntity(11337 + i,
                        new MetaTileEntityDecompressor(tkcyaId("decompressor." + i), i)));

        IntStream.range(0, MAX_TIER)
                .forEach(i -> DECOMPRESSION_HATCH[i] = registerMetaTileEntity(11345 + i,
                        new MetaTileEntityDecompressionHatch(tkcyaId("decompression_hatch." + i), i)));

        IntStream.range(0, MAX_TIER)
                .forEach(i -> ELECTRIC_PUMPS[i] = registerMetaTileEntity(11353 + i,
                        new MetaTileEntityElectricPump(tkcyaId("electric_pump." + i), i)));

        IntStream.range(0, MAX_TIER)
                .forEach(i -> ELECTRIC_CONVEYORS[i] = registerMetaTileEntity(11361 + i,
                        new MetaTileEntityElectricConveyor(tkcyaId("electric_conveyor." + i), i)));

        AXE_SUPPORT = registerMetaTileEntity(11369, new MetaTileEntityAxeSupport(tkcyaId("axe_support")));

        IntStream.range(0, STEAM_TURBINE.length)
                .forEach(i -> STEAM_TURBINE[i] = registerMetaTileEntity(11375 + i,
                        new MetaTileEntitySteamTurbine(tkcyaId("steam_turbine." + i), i)));

        IntStream.range(0, ROTATION_COMPRESSOR.length)
                .forEach(i -> ROTATION_COMPRESSOR[i] = registerMetaTileEntity(11382 + i,
                        new MetaTileEntityRotationCompressor(tkcyaId("rotation_compressor." + i), i)));

        IntStream.range(0, DYNAMOS.length)
                .forEach(i -> DYNAMOS[i] = registerMetaTileEntity(11389 + i,
                        new MetaTileEntityDynamo(tkcyaId("dynamo." + i), i)));

        IntStream.range(0, DIESEL_GENERATOR.length)
                .forEach(i -> DIESEL_GENERATOR[i] = registerMetaTileEntity(11395 + i,
                        new MetaTileEntityDieselGenerator(tkcyaId("diesel_generator." + i), i)));

        MULTIBLOCK_PRESSURE_DETECTOR = registerMetaTileEntity(11400,
                new MetaTileEntityController(tkcyaId("pressure_controller"), CONTROL_PRESSURE_DETECTOR));
        MULTIBLOCK_VACUUM_DETECTOR = registerMetaTileEntity(11401,
                new MetaTileEntityController(tkcyaId("vacuum_controller"), CONTROL_VACUUM_DETECTOR));
        MULTIBLOCK_TEMPERATURE_DETECTOR = registerMetaTileEntity(11402,
                new MetaTileEntityController(tkcyaId("temperature_controller"), CONTROL_TEMPERATURE_DETECTOR));
        MULTIBLOCK_SPEED_DETECTOR = registerMetaTileEntity(11403,
                new MetaTileEntityController(tkcyaId("speed_controller"), CONTROL_SPEED_DETECTOR));
        MULTIBLOCK_TORQUE_DETECTOR = registerMetaTileEntity(11404,
                new MetaTileEntityController(tkcyaId("torque_controller"), CONTROL_TORQUE_DETECTOR));
        MULTIBLOCK_ROTATION_POWER_DETECTOR = registerMetaTileEntity(11405,
                new MetaTileEntityController(tkcyaId("rotation_power_controller"), CONTROL_ROTATION_POWER_DETECTOR));

        if (TKCYAConfigHolder.storageOverhaul.enableMultiblockTanksOverhaul) {

            STEEL_TANK_VALVE = registerMetaTileEntity(11406,
                    new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.steel"),
                            Materials.Steel));
            GALVANIZED_STEEL_TANK_VALVE = registerMetaTileEntity(11407,
                    new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.galvanized_steel"),
                            TKCYAMaterials.GalvanizedSteel));
            STAINLESS_STEEL_TANK_VALVE = registerMetaTileEntity(11408,
                    new TKCYAMetaTileEntityTankValve(tkcyaId("multiblock.valve.stainless_steel"),
                            Materials.StainlessSteel));
        }

        MODULABLE_CRATE[0] = registerMetaTileEntity(12105,
                new MetaTileEntityModulableMultiblockCrate(tkcyaId("modulable_wood_crate"), TreatedWood, 5000));
        MODULABLE_CRATE[1] = registerMetaTileEntity(12106,
                new MetaTileEntityModulableMultiblockCrate(tkcyaId("modulable_steel_crate"), Steel, 10000));
        MODULABLE_CRATE[2] = registerMetaTileEntity(12107, new MetaTileEntityModulableMultiblockCrate(
                tkcyaId("modulable_galvanized_steel_crate"), TKCYAMaterials.GalvanizedSteel, 10000));
        MODULABLE_CRATE[3] = registerMetaTileEntity(12108, new MetaTileEntityModulableMultiblockCrate(
                tkcyaId("modulable_stainless_steel_crate"), StainlessSteel, 10000));

        TREATED_WOOD_CRATE_VALVE = registerMetaTileEntity(12109,
                new MetaTileEntityCrateValve(tkcyaId("wood_crate_valve"), TreatedWood));
        STEEL_CRATE_VALVE = registerMetaTileEntity(12110,
                new MetaTileEntityCrateValve(tkcyaId("steel_crate_valve"), Steel));
        GALVANIZED_STEEL_CRATE_VALVE = registerMetaTileEntity(12111,
                new MetaTileEntityCrateValve(tkcyaId("galvanized_steel_crate_valve"), TKCYAMaterials.GalvanizedSteel));
        STAINLESS_STEEL_CRATE_VALVE = registerMetaTileEntity(12112,
                new MetaTileEntityCrateValve(tkcyaId("stainless_steel_crate_valve"), StainlessSteel));

        MULTI_AMPERAGE_TEST = registerMetaTileEntity(12130, new MetaTileEntityMultiAmperageTest(tkcyaId("test")));

        if (TKCYAConfigHolder.storageOverhaul.enableDrumsOverhaul) {
            for (int i = 0; i < DRUM_MATERIALS.size(); i++) {
                Material m = DRUM_MATERIALS.get(i);
                DRUMS[i] = registerMetaTileEntity(12140 + i,
                        new MetaTileEntityDrum(tkcyaId("drum." + m.getUnlocalizedName()), m, 16000));
            }
        }

        registerOther("electric_heater.", ELECTRIC_HEATER, 11260, MetaTileEntityElectricHeater::new);
        registerOther("electric_cooler.", ELECTRIC_COOLER, 11270, MetaTileEntityElectricCooler::new);
        registerOther("electric_vacuum_pump.", VACUUM_PUMP, 11280, MetaTileEntityElectricVacuumPump::new);
        registerOther("electric_pressure_compressor.", PRESSURE_COMPRESSOR, 11290,
                MetaTileEntityElectricPressureCompressor::new);
        registerOther("heat_acceptor.", HEAT_ACCEPTOR, 11300, MetaTileEntityHeatAcceptor::new);

        registerOther("vacuum_hatch.", VACUUM_HATCH, 11241, MetaTileEntityVacuumHatch::new);
        registerOther("pressure_hatch.", PRESSURE_HATCH, 11251, MetaTileEntityPressureHatch::new);

        if (TKCYAConfigHolder.harderStuff.enableRoastingOverhaul) {
            registerBrickMTE("_roasting_oven", BRICK_ROASTING_OVEN, 11310, MetaTileEntityPrimitiveRoastOven::new);
        }

        LOGISTIC_MACHINE = registerMetaTileEntity(11320,
                new MetaTileEntityLogisticMachine(tkcyaId("logistic_machine.1"), 1));
        LOGISTIC_MACHINE = registerMetaTileEntity(11321,
                new MetaTileEntityLogisticMachine(tkcyaId("logistic_machine.2"), 2));
        LOGISTIC_MACHINE = registerMetaTileEntity(11322,
                new MetaTileEntityLogisticMachine(tkcyaId("logistic_machine.3"), 3));
        LOGISTIC_MACHINE = registerMetaTileEntity(11323,
                new MetaTileEntityLogisticMachine(tkcyaId("logistic_machine.4"), 4));

        STEAM_CRUSHER = registerMetaTileEntity(11440, new MetaTileEntitySteamCrusher(tkcyaId("crusher")));
    }

    private static void registerBrickMTE(String lang, MetaTileEntity[] mtes, int id,
                                         BiFunction<ResourceLocation, BlockBrick.BrickType, MetaTileEntity> function) {
        for (int i = 0; i < BRICKS.size(); i++) {
            BlockBrick.BrickType brickType = BRICKS.get(i);
            mtes[i] = registerMetaTileEntity(id + i, function.apply(tkcyaId(brickType.getName() + lang), brickType));
        }
    }

    private static void registerBrickMTE(String lang, MetaTileEntity[] mtes, int id,
                                         TriFunction<ResourceLocation, BlockBrick.BrickType, Boolean, MetaTileEntity> function,
                                         boolean isExport) {
        for (int i = 0; i < BRICKS.size(); i++) {
            BlockBrick.BrickType brickType = BRICKS.get(i);
            mtes[i] = registerMetaTileEntity(id + i,
                    function.apply(tkcyaId(brickType.getName() + lang), brickType, isExport));
        }
    }

    private static void registerOther(String lang, MetaTileEntity[] mtes, int id,
                                      BiFunction<ResourceLocation, Integer, MetaTileEntity> function) {
        for (int i = 0; i < MAX_TIER; i++) {
            mtes[i] = registerMetaTileEntity(id + i, function.apply(tkcyaId(lang + GTValues.VN[i].toLowerCase()), i));
        }
    }

    private static void registerFuelTier(String lang, MetaTileEntity[] mtes, int id,
                                         BiFunction<ResourceLocation, FuelHeaterTiers, MetaTileEntity> function) {
        for (int i = 0; i < FUEL_HEATERS.size(); i++) {
            FuelHeaterTiers fuelHeater = FUEL_HEATERS.get(i);
            mtes[i] = registerMetaTileEntity(id + i,
                    function.apply(tkcyaId(fuelHeater.getMaterialName() + lang), fuelHeater));
        }
    }
}
