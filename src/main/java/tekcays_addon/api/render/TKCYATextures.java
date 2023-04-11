package tekcays_addon.api.render;

import tekcays_addon.TekCaysAddon;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import static tekcays_addon.api.utils.HeatersMethods.*;

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

    //Cooler
    public static SimpleOverlayRenderer COOLER_COOL_SIDE_OVERLAY;
    public static SimpleOverlayRenderer COOLER_HOT_SIDE_OVERLAY;

    //Covers
    public static SimpleOverlayRenderer DETECTOR_TEMPERATURE;


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


        //Covers
        DETECTOR_TEMPERATURE = new SimpleOverlayRenderer("cover/overlay_temperature_detector");
    }
}
