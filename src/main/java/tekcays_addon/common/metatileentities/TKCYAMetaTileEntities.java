package tekcays_addon.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

import static gregtech.common.metatileentities.MetaTileEntities.*;
import static tekcays_addon.api.utils.BlastFurnaceUtils.BRICKS;
import static tekcays_addon.api.utils.FuelHeaterTiers.BRICK;
import static tekcays_addon.api.utils.FuelHeaterTiers.FUEL_HEATERS;
import static tekcays_addon.api.utils.TKCYAValues.DRUM_MATERIALS;

public class TKCYAMetaTileEntities {


    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ELECTRIC_CASTING_TABLE = new SimpleMachineMetaTileEntity[5];
    public static MetaTileEntityElectricHeater[] ELECTRIC_HEATER = new MetaTileEntityElectricHeater[5];
    public static MetaTileEntityElectricCooler[] ELECTRIC_COOLER = new MetaTileEntityElectricCooler[5];
    public static MetaTileEntityHeatAcceptor[] HEAT_ACCEPTOR = new MetaTileEntityHeatAcceptor[4];
    public static MetaTileEntityPressureHatch[] PRESSURE_HATCH = new MetaTileEntityPressureHatch[GTValues.LuV * 2 + 2];
    public static MetaTileEntityElectricPressureCompressor[] PRESSURE_COMPRESSOR = new MetaTileEntityElectricPressureCompressor[GTValues.LuV * 2 + 2];

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

    public static MetaTileEntitySolidFuelHeater[] SOLID_FUEL_HEATER = new MetaTileEntitySolidFuelHeater[FUEL_HEATERS.size()];
    public static MetaTileEntityLiquidFuelHeater[] LIQUID_FUEL_HEATER = new MetaTileEntityLiquidFuelHeater[FUEL_HEATERS.size()];
    public static MetaTileEntityFluidizedHeater[] FLUIDIZED_FUEL_HEATER = new MetaTileEntityFluidizedHeater[FUEL_HEATERS.size()];
    public static MetaTileEntityGasHeater[] GAS_FUEL_HEATER = new MetaTileEntityGasHeater[FUEL_HEATERS.size()];

    public static MetaTileEntityRoastingOven ROASTING_OVEN;
    public static MetaTileEntitySpiralSeparator SPIRAL_SEPARATOR;


    //Brick MTES
    public static String[] BRICK_MTES = {"_blast_furnace", "_import_fluid_hatch", "_export_fluid_hatch", "_export_item_bus", "_import_item_bus", "_muffler", "_single_crucible"};
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

        startId += 5;

        //id 11005-11009
        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            registerSimpleMetaTileEntity(ADVANCED_POLARIZER, startId, "advanced_polarizer", TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES,
                    TKCYATextures.ADVANCED_POLARIZER_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        startId += 5;

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            PRIMITIVE_MELTER = registerMetaTileEntity(startId, new MetaTileEntityPrimitiveMelter(tkcyaId("primitive_melter")));
            PRIMITIVE_FERMENTER = registerMetaTileEntity(startId++, new MetaTileEntityPrimitiveFermenter(tkcyaId("primitive_fermenter")));
        }

        startId = Math.max(startId, startId + 2);

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            ALLOYING_CRUCIBLE = registerMetaTileEntity(startId, new MetaTileEntityAlloyingCrucible(tkcyaId("alloying_crucible")));
        }
        startId = Math.max(startId, startId + 1);

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            CASTING_TABLE = registerMetaTileEntity(startId++, new MetaTileEntityCastingTable(tkcyaId("casting_table")));

            registerSimpleMetaTileEntity(ELECTRIC_CASTING_TABLE, startId++, "electric_casting_table", TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES,
                    TKCYATextures.CASTING_TABLE_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);

            startId += 5;

            STEAM_COOLER_BRONZE = registerMetaTileEntity(startId, new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_bronze"), false));
            STEAM_COOLER_STEEL = registerMetaTileEntity(startId++, new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_steel"), true));

        }

        startId = Math.max(startId, startId + 7);

        if (TKCYAConfigHolder.meltingOverhaul.enableBlastingOverhaul) {
            CONVERTER = registerMetaTileEntity(startId, new MetaTileEntityElectricConverter(tkcyaId("converter")));
            PRIMITIVE_CONVERTER = registerMetaTileEntity(startId++, new MetaTileEntityPrimitiveConverter(tkcyaId("primitive_converter")));
            COKE_OVEN = registerMetaTileEntity(startId++, new MetaTileEntityTKCYACokeOven(tkcyaId("coke_oven")));

            for (int i = 0; i < BRICKS.size(); i++) {
                int j = 0;
                BlockBrick.BrickType brick = BRICKS.get(i);
                String name = brick.getName() + BRICK_MTES[j];
                int finalI = i;
                int finalStartId = startId;
                IntFunction<Integer> setId = (id) -> finalStartId + finalI * BRICKS.size() * id;

                BLAST_FURNACE[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityTKCYABlastFurnace(tkcyaId(name), brick));
                ADVANCED_BLAST_FURNACE[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityAdvancedBlastFurnace(tkcyaId(name), brick));
                BRICK_IMPORT_FLUID_HATCH[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityBrickFluidHatch(tkcyaId(name), false, brick));
                BRICK_EXPORT_FLUID_HATCH[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityBrickFluidHatch(tkcyaId(name), true, brick));
                BRICK_EXPORT_ITEM_BUS[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityBrickItemBus(tkcyaId(name), true, brick));
                BRICK_IMPORT_ITEM_BUS[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityBrickItemBus(tkcyaId(name), false, brick));
                PRIMITIVE_MUFFLER[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntityPrimitiveMufflerHatch(tkcyaId(name), brick));
                SINGLE_CRUCIBLE[i] = registerMetaTileEntity(setId.apply(j++), new MetaTileEntitySingleCrucible(tkcyaId(name), brick));

                /*
                BLAST_FURNACE[i] = registerMetaTileEntity(j, new MetaTileEntityTKCYABlastFurnace(tkcyaId(name), brick));
                ADVANCED_BLAST_FURNACE[i] = registerMetaTileEntity(setId.apply(j), new MetaTileEntityAdvancedBlastFurnace(tkcyaId(name + "_advanced_blast_furnace"), brick));
                BRICK_IMPORT_FLUID_HATCH[i] = registerMetaTileEntity(startId + i * BRICKS.size() * 2, new MetaTileEntityBrickFluidHatch(tkcyaId(name + "_import_fluid_hatch"), false, brick));
                BRICK_EXPORT_FLUID_HATCH[i] = registerMetaTileEntity(startId + i * BRICKS.size() * 3, new MetaTileEntityBrickFluidHatch(tkcyaId(name + "_export_fluid_hatch"), true, brick));
                BRICK_EXPORT_ITEM_BUS[i] = registerMetaTileEntity(startId + i * BRICKS.size() * 4, new MetaTileEntityBrickItemBus(tkcyaId(name + "_export_item_bus"), true, brick));
                BRICK_IMPORT_ITEM_BUS[i] = registerMetaTileEntity(startId + i * BRICKS.size() * 5, new MetaTileEntityBrickItemBus(tkcyaId(name + "_import_item_bus"), false, brick));
                PRIMITIVE_MUFFLER[i] = registerMetaTileEntity(startId + i * BRICKS.size() * 6, new MetaTileEntityPrimitiveMufflerHatch(tkcyaId(name + "_muffler"), brick));
                SINGLE_CRUCIBLE[i] = registerMetaTileEntity(startId + i * BRICKS.size() * 7, new MetaTileEntitySingleCrucible(tkcyaId(name + "_single_crucible"), brick));

                 */
            }
        }

        startId = Math.max(startId, startId + 36);

        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            PRIMITIVE_BATH = registerMetaTileEntity(11085, new MetaTileEntityPrimitiveBath(tkcyaId("primitive_bath")));
        }

        if (TKCYAConfigHolder.miscOverhaul.enableElectrolysisOverhaul) {
            ADVANCED_ELECTROLYZER = registerMetaTileEntity(11025, new MetaTileEntityAdvancedElectrolyzer(tkcyaId("advanced_electrolyzer")));
        }

        //id 11026-11036
        if (TKCYAConfigHolder.storageOverhaul.enableDrumsOverhaul) {
            for (int i = 0; i < DRUM_MATERIALS.size(); i++) {
                Material m = DRUM_MATERIALS.get(i);
                DRUMS[i] = registerMetaTileEntity(11026 + i, new MetaTileEntityDrum(tkcyaId("drum." + m.getUnlocalizedName()), m, 16000));
            }
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

        FILTER = registerMetaTileEntity(13075, new MetaTileEntityFilter(tkcyaId("filter")));

        if (TKCYAConfigHolder.crackingOverhaul.enableCrackingOverhaul) {
            PRESSURIZED_CRACKING_UNIT = registerMetaTileEntity(13076, new MetaTileEntityPressurizedCrackingUnit(tkcyaId("pressurized_cracking_unit")));
        }


        ROASTING_OVEN = registerMetaTileEntity(13077, new MetaTileEntityRoastingOven(tkcyaId("roasting_oven")));
        SPIRAL_SEPARATOR = registerMetaTileEntity(13078, new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));
        STEAM_AIR_COMPRESSOR = registerMetaTileEntity(13079, new MetaTileEntitySteamAirCompressor(tkcyaId("steam_air_compressor")));

        
        ROASTING_OVEN = registerMetaTileEntity(13080, new MetaTileEntityRoastingOven(tkcyaId("roasting_oven")));
        SPIRAL_SEPARATOR = registerMetaTileEntity(13081, new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));
        


        //id 11177 to 11194
        SOLID_FUEL_HEATER[0] = registerMetaTileEntity(11177, new MetaTileEntitySolidFuelHeater(tkcyaId( "brick_solid_fuel_heater"), BRICK));

        for (int i = 1; i < FUEL_HEATERS.size(); i++) {
            int idToStart = 11178;
            FuelHeaterTiers fuelHeater = FUEL_HEATERS.get(i);
            String materialName = fuelHeater.getMaterial().getUnlocalizedName();
            SOLID_FUEL_HEATER[i] = registerMetaTileEntity(idToStart + i, new MetaTileEntitySolidFuelHeater(tkcyaId( materialName + "_solid_fuel_heater"), fuelHeater));
            LIQUID_FUEL_HEATER[i] = registerMetaTileEntity(idToStart + i + FUEL_HEATERS.size(), new MetaTileEntityLiquidFuelHeater(tkcyaId(materialName + "_liquid_fuel_heater"), fuelHeater));
            FLUIDIZED_FUEL_HEATER[i] = registerMetaTileEntity(idToStart + i + FUEL_HEATERS.size() * 2, new MetaTileEntityFluidizedHeater(tkcyaId(materialName + "_fluidized_fuel_heater"), fuelHeater));
            GAS_FUEL_HEATER[i] = registerMetaTileEntity(idToStart + i + FUEL_HEATERS.size() * 3, new MetaTileEntityGasHeater(tkcyaId(materialName + "_gas_fuel_heater"), fuelHeater));
        }

        STEAM_AUTOCLAVE = registerMetaTileEntity(12020, new MetaTileEntitySteamAutoclave(tkcyaId("steam_autoclave"), true));

        int increment = GTValues.LuV + 1;

        for (int i = 0; i < GTValues.LuV; i++) {
            int startId = 12034;
            PRESSURE_HATCH[i] = registerMetaTileEntity(startId + i, new MetaTileEntityPressureHatch(tkcyaId("vacuum_hatch." + GTValues.VN[i].toLowerCase()), true, i));
            PRESSURE_HATCH[i + increment] = registerMetaTileEntity(startId + i + increment, new MetaTileEntityPressureHatch(tkcyaId("pressure_hatch." + GTValues.VN[i].toLowerCase()), false, i));
            PRESSURE_COMPRESSOR[i] = registerMetaTileEntity(startId + i + increment * 2, new MetaTileEntityElectricPressureCompressor(tkcyaId("electric_vacuum_pump." + GTValues.VN[i].toLowerCase()), true, i));
            PRESSURE_COMPRESSOR[i + increment] = registerMetaTileEntity(startId + i + increment * 3, new MetaTileEntityElectricPressureCompressor(tkcyaId("electric_compressor." + GTValues.VN[i].toLowerCase()), false, i));
        }

        increment = GTValues.IV+ 1;

        for (int i = 0; i < GTValues.LuV; i++) {
            int startId = 12100;
            ELECTRIC_HEATER[i] = registerMetaTileEntity(startId + i, new MetaTileEntityElectricHeater(tkcyaId("electric_heater." + GTValues.VN[i]), i));
            ELECTRIC_COOLER[i] = registerMetaTileEntity(startId + increment, new MetaTileEntityElectricCooler(tkcyaId("electric_cooler." + GTValues.VN[i]), i));
            HEAT_ACCEPTOR[i] = registerMetaTileEntity(startId + increment * 2, new MetaTileEntityHeatAcceptor(tkcyaId("heat_acceptor." + GTValues.VN[i]), i));
        }


    }

    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }


}
