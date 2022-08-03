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

    //Multiblocks
    public static OrientedOverlayRenderer PRIMITIVE_MELTER_OVERLAY = new OrientedOverlayRenderer("multiblocks/primitive", FRONT);
    public static OrientedOverlayRenderer ELECTRIC_MELTER_OVERLAY = new OrientedOverlayRenderer("multiblocks/electric_melter", FRONT);
    public static OrientedOverlayRenderer ALLOYING_CRUCIBLE_OVERLAY = new OrientedOverlayRenderer("multiblocks/primitive", FRONT);
    public static OrientedOverlayRenderer BATCH_DISTILLATION_TOWER_OVERLAY = new OrientedOverlayRenderer("multiblocks/batch_distillation_tower", FRONT);


    // Bricks
    public static SimpleOverlayRenderer BRICK;
    public static SimpleOverlayRenderer REINFORCED_BRICK;
    public static SimpleOverlayRenderer FIRECLAY_BRICK;
    public static SimpleOverlayRenderer STRONG_BRICK;


    //GT6 style
    public static SimpleOverlayRenderer STEEL_GT;
    public static SimpleOverlayRenderer STAINLESS_STEEL_GT;
    public static SimpleOverlayRenderer WHITE_GT;
    public static SimpleOverlayRenderer WHITE_GT_STRIPE;


    public static void preInit() {
        // Simple Machines


        //BRICKS
        BRICK = new SimpleOverlayRenderer("casings/block_brick/brick");
        REINFORCED_BRICK = new SimpleOverlayRenderer("casings/block_brick/reinforced_brick");
        FIRECLAY_BRICK = new SimpleOverlayRenderer("casings/block_brick/fireclay_brick");
        STRONG_BRICK = new SimpleOverlayRenderer("casings/block_brick/strong_brick");



        //GT6 Style
        STEEL_GT = new SimpleOverlayRenderer("steel_gt");
        STAINLESS_STEEL_GT = new SimpleOverlayRenderer("stainless_steel_gt");
        WHITE_GT = new SimpleOverlayRenderer("white_gt");
        WHITE_GT_STRIPE = new SimpleOverlayRenderer("white_gt_stripe");

    }
}
