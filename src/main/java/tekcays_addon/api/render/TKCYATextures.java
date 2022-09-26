package tekcays_addon.api.render;

import tekcays_addon.TekCaysAddon;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import static gregtech.client.renderer.texture.cube.OrientedOverlayRenderer.OverlayFace.*;

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

    // Bricks
    public static SimpleOverlayRenderer BRICK;
    public static SimpleOverlayRenderer REINFORCED_BRICK;
    public static SimpleOverlayRenderer FIRECLAY_BRICK;
    public static SimpleOverlayRenderer STRONG_BRICK;


    //GT6 style
    //Casing
    public static SimpleOverlayRenderer STEEL_GT;
    public static SimpleOverlayRenderer WHITE_GT;
    public static SimpleOverlayRenderer STAINLESS_STEEL_GT;
    //SteamCasing
    public static SimpleOverlayRenderer BRONZE_STEAM_CASING;
    public static SimpleOverlayRenderer STEEL_STEAM_CASING;
    public static SimpleOverlayRenderer INVAR_STEAM_CASING;
    public static SimpleOverlayRenderer TITANIUM_STEAM_CASING;
    public static SimpleOverlayRenderer TUNGSTEN_STEEL_STEAM_CASING;

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

        //GT6 Style
        //Casing
        STEEL_GT = new SimpleOverlayRenderer("steel_gt");
        WHITE_GT = new SimpleOverlayRenderer("white_gt");
        STAINLESS_STEEL_GT = new SimpleOverlayRenderer("stainless_steel_gt");
        //SteamCasing
        BRONZE_STEAM_CASING = new SimpleOverlayRenderer("casings/steam/bronze/top");
        STEEL_STEAM_CASING = new SimpleOverlayRenderer("casings/steam/steel/top");
        INVAR_STEAM_CASING = new SimpleOverlayRenderer("casings/steam/invar/top");
        TITANIUM_STEAM_CASING = new SimpleOverlayRenderer("casings/steam/titanium/top");
        TUNGSTEN_STEEL_STEAM_CASING = new SimpleOverlayRenderer("casings/steam/tungsten_steel/top");


        //Acceptors
        HEAT_ACCEPTOR_HORIZONTALS_OVERLAY = new SimpleOverlayRenderer("multiblockpart/heat_acceptor_horizontals");
        HEAT_ACCEPTOR_VERTICALS_OVERLAY = new SimpleOverlayRenderer("multiblockpart/heat_acceptor_verticals");
    }
}
