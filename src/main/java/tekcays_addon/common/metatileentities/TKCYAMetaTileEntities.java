package tekcays_addon.common.metatileentities;

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
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.metatileentities.multi.*;

import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityHeatAcceptor;
import tekcays_addon.common.metatileentities.single.MetaTileEntityElectricHeater;

import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityBrickFluidHatch;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityBrickItemBus;
import tekcays_addon.common.metatileentities.multiblockpart.MetaTileEntityPrimitiveMufflerHatch;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamAirCompressor;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamAutoclave;
import tekcays_addon.common.metatileentities.steam.MetaTileEntitySteamCooler;

import static gregtech.common.metatileentities.MetaTileEntities.*;
import static tekcays_addon.api.utils.BlastFurnaceUtils.BRICKS;
import static tekcays_addon.api.utils.TKCYAValues.DRUM_MATERIALS;

public class TKCYAMetaTileEntities {


    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ELECTRIC_CASTING_TABLE = new SimpleMachineMetaTileEntity[5];
    public static MetaTileEntityElectricHeater[] ELECTRIC_HEATER = new MetaTileEntityElectricHeater[5];
    public static MetaTileEntityHeatAcceptor[] HEAT_ACCEPTOR = new MetaTileEntityHeatAcceptor[1];

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

    public static MetaTileEntityAdvancedMelter ADVANCED_MELTER;

    public static MetaTileEntityRoastingOven ROASTING_OVEN;
    public static MetaTileEntitySpiralSeparator SPIRAL_SEPARATOR;


    //Blast Furnaces
    public static MetaTileEntityTKCYABlastFurnace[] BLAST_FURNACE = new MetaTileEntityTKCYABlastFurnace[BRICKS.size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_IMPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS.size()];
    public static MetaTileEntityBrickFluidHatch[] BRICK_EXPORT_FLUID_HATCH = new MetaTileEntityBrickFluidHatch[BRICKS.size()];
    public static MetaTileEntityBrickItemBus[] BRICK_EXPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityBrickItemBus[] BRICK_IMPORT_ITEM_BUS = new MetaTileEntityBrickItemBus[BRICKS.size()];
    public static MetaTileEntityPrimitiveMufflerHatch[] PRIMITIVE_MUFFLER = new MetaTileEntityPrimitiveMufflerHatch[BRICKS.size()];

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

        //id 11000-11004
        if (TKCYAConfigHolder.miscOverhaul.enableFoilOverhaul) {
            registerSimpleMetaTileEntity(CLUSTER_MILL, 11000, "cluster_mill", TKCYARecipeMaps.CLUSTER_MILL_RECIPES,
                    TKCYATextures.CLUSTER_MILL_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        //id 11005-11009
        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            registerSimpleMetaTileEntity(ADVANCED_POLARIZER, 11005, "advanced_polarizer", TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES,
                    TKCYATextures.ADVANCED_POLARIZER_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            PRIMITIVE_MELTER = registerMetaTileEntity(11010, new MetaTileEntityPrimitiveMelter(tkcyaId("primitive_melter")));
            PRIMITIVE_FERMENTER = registerMetaTileEntity(11011, new MetaTileEntityPrimitiveFermenter(tkcyaId("primitive_fermenter")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            ALLOYING_CRUCIBLE = registerMetaTileEntity(11013, new MetaTileEntityAlloyingCrucible(tkcyaId("alloying_crucible")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            CASTING_TABLE = registerMetaTileEntity(11014, new MetaTileEntityCastingTable(tkcyaId("casting_table")));

            registerSimpleMetaTileEntity(ELECTRIC_CASTING_TABLE, 11015, "electric_casting_table", TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES,
                    TKCYATextures.CASTING_TABLE_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);


            STEAM_COOLER_BRONZE = registerMetaTileEntity(11020, new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_bronze"), false));
            STEAM_COOLER_STEEL = registerMetaTileEntity(11021, new MetaTileEntitySteamCooler(tkcyaId("steam_cooler_steel"), true));

        }


        if (TKCYAConfigHolder.meltingOverhaul.enableBlastingOverhaul) {
            CONVERTER = registerMetaTileEntity(11022, new MetaTileEntityElectricConverter(tkcyaId("converter")));
            PRIMITIVE_CONVERTER = registerMetaTileEntity(11023, new MetaTileEntityPrimitiveConverter(tkcyaId("primitive_converter")));
            COKE_OVEN = registerMetaTileEntity(11024, new MetaTileEntityTKCYACokeOven(tkcyaId("coke_oven")));

            //id 11051-11074
            for (int i = 0; i < BRICKS.size(); i++) {
                BlockBrick.BrickType brick = BRICKS.get(i);
                BLAST_FURNACE[i] = registerMetaTileEntity(11051 + i, new MetaTileEntityTKCYABlastFurnace(tkcyaId(brick.getName() + "_blast_furnace"), brick));
                BRICK_IMPORT_FLUID_HATCH[i] = registerMetaTileEntity(11055 + i, new MetaTileEntityBrickFluidHatch(tkcyaId(brick.getName() + "_import_fluid_hatch"), false, brick));
                BRICK_EXPORT_FLUID_HATCH[i] = registerMetaTileEntity(11059 + i, new MetaTileEntityBrickFluidHatch(tkcyaId(brick.getName() + "_export_fluid_hatch"), true, brick));
                BRICK_EXPORT_ITEM_BUS[i] = registerMetaTileEntity(11063 + i, new MetaTileEntityBrickItemBus(tkcyaId(brick.getName() + "_export_item_bus"), true, brick));
                BRICK_IMPORT_ITEM_BUS[i] = registerMetaTileEntity(11067 + i, new MetaTileEntityBrickItemBus(tkcyaId(brick.getName() + "_import_item_bus"), false, brick));
                PRIMITIVE_MUFFLER[i] = registerMetaTileEntity(11071 + i, new MetaTileEntityPrimitiveMufflerHatch(tkcyaId(brick.getName() + "_muffler"), brick));
            }

        }

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

        FILTER = registerMetaTileEntity(11075, new MetaTileEntityFilter(tkcyaId("filter")));

        if (TKCYAConfigHolder.crackingOverhaul.enableCrackingOverhaul) {
            PRESSURIZED_CRACKING_UNIT = registerMetaTileEntity(11076, new MetaTileEntityPressurizedCrackingUnit(tkcyaId("pressurized_cracking_unit")));
        }


        ROASTING_OVEN = registerMetaTileEntity(11077, new MetaTileEntityRoastingOven(tkcyaId("roasting_oven")));
        SPIRAL_SEPARATOR = registerMetaTileEntity(11078, new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));
        STEAM_AIR_COMPRESSOR = registerMetaTileEntity(11079, new MetaTileEntitySteamAirCompressor(tkcyaId("steam_air_compressor")));

        
        ROASTING_OVEN = registerMetaTileEntity(11080, new MetaTileEntityRoastingOven(tkcyaId("roasting_oven")));
        SPIRAL_SEPARATOR = registerMetaTileEntity(10081, new MetaTileEntitySpiralSeparator(tkcyaId("spiral_separator")));
        
        ELECTRIC_HEATER[0] = registerMetaTileEntity(11170, new MetaTileEntityElectricHeater(tkcyaId("electric_heater.lv"), 1));
        ELECTRIC_HEATER[1] = registerMetaTileEntity(11171, new MetaTileEntityElectricHeater(tkcyaId("electric_heater.mv"), 2));
        ELECTRIC_HEATER[2] = registerMetaTileEntity(11172, new MetaTileEntityElectricHeater(tkcyaId("electric_heater.hv"), 3));
        ELECTRIC_HEATER[3] = registerMetaTileEntity(11173, new MetaTileEntityElectricHeater(tkcyaId("electric_heater.ev"), 4));
        ELECTRIC_HEATER[4] = registerMetaTileEntity(11174, new MetaTileEntityElectricHeater(tkcyaId("electric_heater.iv"), 5));

        HEAT_ACCEPTOR[0] = registerMetaTileEntity(11175, new MetaTileEntityHeatAcceptor(tkcyaId("heat_acceptor.lv"), 1));
        ADVANCED_MELTER = registerMetaTileEntity(1116, new MetaTileEntityAdvancedMelter(tkcyaId("avcanced_melter")));

        STEAM_AUTOCLAVE = registerMetaTileEntity(12010, new MetaTileEntitySteamAutoclave(tkcyaId("steam_autoclave"), true));

    }

    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }


}
