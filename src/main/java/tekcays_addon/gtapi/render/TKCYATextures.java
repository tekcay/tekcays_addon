package tekcays_addon.gtapi.render;

import tekcays_addon.TekCaysAddon;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import static tekcays_addon.gtapi.utils.HeatersMethods.*;

@Mod.EventBusSubscriber(modid = TekCaysAddon.MODID, value = Side.CLIENT)
public class TKCYATextures {


    // SimpleMachines
    public static OrientedOverlayRenderer CLUSTER_MILL_OVERLAY = new OrientedOverlayRenderer("machines/cluster_mill");
    public static OrientedOverlayRenderer ADVANCED_POLARIZER_OVERLAY = new OrientedOverlayRenderer("machines/advanced_polarizer");



    public static OrientedOverlayRenderer CASTING_TABLE_OVERLAY = new OrientedOverlayRenderer("machines/casting_table");

    //Casings
    public static SimpleOverlayRenderer MONEL;

    //MeltingOverhaul
    public static OrientedOverlayRenderer PRIMITIVE_MELTER_OVERLAY = new OrientedOverlayRenderer("multiblocks/primitive");
    public static OrientedOverlayRenderer ELECTRIC_MELTER_OVERLAY = new OrientedOverlayRenderer("multiblocks/electric_melter");
    public static OrientedOverlayRenderer ALLOYING_CRUCIBLE_OVERLAY = new OrientedOverlayRenderer("multiblocks/primitive");
    public static OrientedOverlayRenderer LIQUID_FUEL_HEATER = new OrientedOverlayRenderer("generators/liquid_heater");
    public static OrientedOverlayRenderer FLUIDIZED_FUEL_HEATER = new OrientedOverlayRenderer("generators/fluidized_heater");
    public static OrientedOverlayRenderer SOLID_FUEL_HEATER = new OrientedOverlayRenderer("generators/solid_heater");
    public static OrientedOverlayRenderer GAS_FUEL_HEATER = new OrientedOverlayRenderer("generators/gas_heater");

    //Rotation
    public static OrientedOverlayRenderer ROTATION_TURBINE_OVERLAY = new OrientedOverlayRenderer("rotation/turbine/rotation");
    public static OrientedOverlayRenderer DYNAMO_OVERLAY = new OrientedOverlayRenderer("dynamo");

    // Bricks
    public static SimpleOverlayRenderer[] BRICKS = new SimpleOverlayRenderer[4];
    public static SimpleOverlayRenderer HALF_BRICK;


    //GT6 style
    //Casing
    public static SimpleOverlayRenderer STEEL_GT;
    public static SimpleOverlayRenderer WHITE_GT;
    public static SimpleOverlayRenderer STAINLESS_STEEL_GT;

    //SteamCasing
    public static SimpleOverlayRenderer[] STEAM_CASING = new SimpleOverlayRenderer[5];

    //Acceptors
    public static SimpleOverlayRenderer HEAT_ACCEPTOR_HORIZONTALS_OVERLAY;
    public static SimpleOverlayRenderer HEAT_ACCEPTOR_VERTICALS_OVERLAY;

    //Rotation
    public static SimpleOverlayRenderer ROTATION_WATER_OUTPUT_OVERLAY;
    public static SimpleOverlayRenderer ROTATION_STATIC;

    //Cooler
    public static SimpleOverlayRenderer COOLER_COOL_SIDE_OVERLAY;
    public static SimpleOverlayRenderer COOLER_HOT_SIDE_OVERLAY;

    //Covers
    
    ////Detectors
    public static SimpleOverlayRenderer DETECTOR_TEMPERATURE;
    public static SimpleOverlayRenderer DETECTOR_PRESSURE;
    public static SimpleOverlayRenderer DETECTOR_VACUUM;
    public static SimpleOverlayRenderer DETECTOR_SPEED;
    public static SimpleOverlayRenderer DETECTOR_TORQUE;
    public static SimpleOverlayRenderer DETECTOR_ROTATION_POWER;
    
    ////Molds
    public static SimpleOverlayRenderer COVER_MOLD_INGOT_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_PLATE_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_LONG_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_SMALL_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BOLT_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_RING_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BLOCK_CERAMIC_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_INGOT_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_PLATE_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_LONG_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_SMALL_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BOLT_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_RING_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BLOCK_STEEL_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_RING_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_INGOT_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_PLATE_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_STICK_LONG_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_GEAR_SMALL_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BOLT_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_RING_CARBON_TEXTURE;
    public static SimpleOverlayRenderer COVER_MOLD_BLOCK_CARBON_TEXTURE;

    //Misc
    public static SimpleOverlayRenderer DIRT;


    public static void preInit() {
        // Simple Machines

        //CASINGS
        MONEL = new SimpleOverlayRenderer("casings/large_multiblock_casing/monel_casing");

        //BRICKS
        BRICKS[BlockBrick.BRICK] = new SimpleOverlayRenderer("casings/block_brick/brick");
        BRICKS[BlockBrick.REINFORCED_BRICK] = new SimpleOverlayRenderer("casings/block_brick/reinforced_brick");
        BRICKS[BlockBrick.FIRECLAY_BRICK] = new SimpleOverlayRenderer("casings/block_brick/fireclay_brick");
        BRICKS[BlockBrick.STRONG_BRICK] = new SimpleOverlayRenderer("casings/block_brick/strong_brick");

        HALF_BRICK = new SimpleOverlayRenderer("half_brick");

        //GT6 Style
        //Casing
        STEEL_GT = new SimpleOverlayRenderer("steel_gt");
        WHITE_GT = new SimpleOverlayRenderer("white_gt");
        STAINLESS_STEEL_GT = new SimpleOverlayRenderer("stainless_steel_gt");
        //SteamCasing
        STEAM_CASING[BRONZE] = new SimpleOverlayRenderer("casings/steam/bronze/top");
        STEAM_CASING[STEEL] = new SimpleOverlayRenderer("casings/steam/steel/top");
        STEAM_CASING[INVAR] = new SimpleOverlayRenderer("casings/steam/invar/top");
        STEAM_CASING[TITANIUM] = new SimpleOverlayRenderer("casings/steam/titanium/top");
        STEAM_CASING[TUNGSTEN_STEEL] = new SimpleOverlayRenderer("casings/steam/tungsten_steel/top");

        //Acceptors
        HEAT_ACCEPTOR_HORIZONTALS_OVERLAY = new SimpleOverlayRenderer("multiblockpart/heat_acceptor_horizontals");
        HEAT_ACCEPTOR_VERTICALS_OVERLAY = new SimpleOverlayRenderer("multiblockpart/heat_acceptor_verticals");

        COOLER_COOL_SIDE_OVERLAY = new SimpleOverlayRenderer("machines/cooler/cool");
        COOLER_HOT_SIDE_OVERLAY = new SimpleOverlayRenderer("machines/cooler/hot");

        //Rotation
        ROTATION_WATER_OUTPUT_OVERLAY = new SimpleOverlayRenderer("rotation/water_output");
        ROTATION_STATIC = new SimpleOverlayRenderer("rotation/rotation_static");


        //Covers
        ////Detectors
        DETECTOR_TEMPERATURE = new SimpleOverlayRenderer("cover/detector/overlay_temperature_detector");
        DETECTOR_PRESSURE = new SimpleOverlayRenderer("cover/detector/overlay_pressure_detector");
        DETECTOR_VACUUM = new SimpleOverlayRenderer("cover/detector/overlay_vacuum_detector");
        DETECTOR_SPEED = new SimpleOverlayRenderer("cover/detector/overlay_speed_detector");
        DETECTOR_TORQUE = new SimpleOverlayRenderer("cover/detector/overlay_torque_detector");
        DETECTOR_ROTATION_POWER = new SimpleOverlayRenderer("cover/detector/overlay_rotation_power_detector");

        ////Molds
        COVER_MOLD_INGOT_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ingot_ceramic");
        COVER_MOLD_PLATE_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_plate_ceramic");
        COVER_MOLD_STICK_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_ceramic");
        COVER_MOLD_STICK_LONG_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_long_ceramic");
        COVER_MOLD_GEAR_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_ceramic");
        COVER_MOLD_GEAR_SMALL_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_small_ceramic");
        COVER_MOLD_BOLT_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_bolt_ceramic");
        COVER_MOLD_RING_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ring_ceramic");
        COVER_MOLD_BLOCK_CERAMIC_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_block_ceramic");
        COVER_MOLD_INGOT_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ingot_steel");
        COVER_MOLD_PLATE_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_plate_steel");
        COVER_MOLD_STICK_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_steel");
        COVER_MOLD_STICK_LONG_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_long_steel");
        COVER_MOLD_GEAR_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_steel");
        COVER_MOLD_GEAR_SMALL_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_small_steel");
        COVER_MOLD_BOLT_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_bolt_steel");
        COVER_MOLD_RING_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ring_steel");
        COVER_MOLD_BLOCK_STEEL_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_block_steel");
        COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ingot_tungsten_carbide");
        COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_plate_tungsten_carbide");
        COVER_MOLD_STICK_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_tungsten_carbide");
        COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_long_tungsten_carbide");
        COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_tungsten_carbide");
        COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_small_tungsten_carbide");
        COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_bolt_tungsten_carbide");
        COVER_MOLD_RING_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ring_tungsten_carbide");
        COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_block_tungsten_carbide");
        COVER_MOLD_INGOT_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ingot_carbon");
        COVER_MOLD_PLATE_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_plate_carbon");
        COVER_MOLD_STICK_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_carbon");
        COVER_MOLD_STICK_LONG_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_stick_long_carbon");
        COVER_MOLD_GEAR_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_carbon");
        COVER_MOLD_GEAR_SMALL_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_gear_small_carbon");
        COVER_MOLD_BOLT_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_bolt_carbon");
        COVER_MOLD_RING_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_ring_carbon");
        COVER_MOLD_BLOCK_CARBON_TEXTURE = new SimpleOverlayRenderer("cover/mold/overlay_block_carbon");

        //Misc
        DIRT = new SimpleOverlayRenderer("dirt");

    }
}
