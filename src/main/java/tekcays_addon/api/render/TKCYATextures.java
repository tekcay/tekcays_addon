package tekcays_addon.api.render;

import tekcays_addon.TekCaysAddon;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import static gregtech.client.renderer.texture.cube.OrientedOverlayRenderer.OverlayFace.*;
import static tekcays_addon.api.utils.HeatersMethods.*;

@Mod.EventBusSubscriber(modid = TekCaysAddon.MODID, value = Side.CLIENT)
public class TKCYATextures {


    // SimpleMachines
    public static OrientedOverlayRenderer CLUSTER_MILL_OVERLAY = new OrientedOverlayRenderer("machines/cluster_mill", FRONT);
    public static OrientedOverlayRenderer ADVANCED_POLARIZER_OVERLAY = new OrientedOverlayRenderer("machines/advanced_polarizer", FRONT, TOP);



    public static OrientedOverlayRenderer CASTING_TABLE_OVERLAY = new OrientedOverlayRenderer("machines/casting_table", FRONT);

    //MeltingOverhaul
    public static OrientedOverlayRenderer PRIMITIVE_MELTER_OVERLAY = new OrientedOverlayRenderer("multiblocks/primitive", FRONT);
    public static OrientedOverlayRenderer ELECTRIC_MELTER_OVERLAY = new OrientedOverlayRenderer("multiblocks/electric_melter", FRONT);
    public static OrientedOverlayRenderer ALLOYING_CRUCIBLE_OVERLAY = new OrientedOverlayRenderer("multiblocks/primitive", FRONT);
    public static OrientedOverlayRenderer LIQUID_FUEL_HEATER = new OrientedOverlayRenderer("generators/liquid_heater", FRONT);
    public static OrientedOverlayRenderer FLUIDIZED_FUEL_HEATER = new OrientedOverlayRenderer("generators/fluidized_heater", FRONT);
    public static OrientedOverlayRenderer SOLID_FUEL_HEATER = new OrientedOverlayRenderer("generators/solid_heater", FRONT);

    // Bricks
    public static SimpleOverlayRenderer BRICK;
    public static SimpleOverlayRenderer REINFORCED_BRICK;
    public static SimpleOverlayRenderer FIRECLAY_BRICK;
    public static SimpleOverlayRenderer STRONG_BRICK;

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


    public static void preInit() {
        // Simple Machines


        //BRICKS
        BRICK = new SimpleOverlayRenderer("casings/block_brick/brick");
        REINFORCED_BRICK = new SimpleOverlayRenderer("casings/block_brick/reinforced_brick");
        FIRECLAY_BRICK = new SimpleOverlayRenderer("casings/block_brick/fireclay_brick");
        STRONG_BRICK = new SimpleOverlayRenderer("casings/block_brick/strong_brick");

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
    }
}
