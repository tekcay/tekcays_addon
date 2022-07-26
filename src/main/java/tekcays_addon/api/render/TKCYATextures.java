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

    // From MC
    public static SimpleOverlayRenderer MC_BRICK;

    //Bricks
    public static SimpleOverlayRenderer BRICK_GRAY;

    // Casings
    public static SimpleOverlayRenderer MACERATOR_CASING;
    public static SimpleOverlayRenderer BLAST_CASING;
    public static SimpleOverlayRenderer ASSEMBLING_CASING;
    public static SimpleOverlayRenderer STRESS_PROOF_CASING;
    public static SimpleOverlayRenderer CORROSION_PROOF_CASING;
    public static SimpleOverlayRenderer VIBRATION_SAFE_CASING;
    public static SimpleOverlayRenderer WATERTIGHT_CASING;
    public static SimpleOverlayRenderer CUTTER_CASING;
    public static SimpleOverlayRenderer NONCONDUCTING_CASING;
    public static SimpleOverlayRenderer MIXER_CASING;
    public static SimpleOverlayRenderer ENGRAVER_CASING;
    public static SimpleOverlayRenderer ATOMIC_CASING;
    public static SimpleOverlayRenderer STEAM_CASING;

    //GT6 style
    public static SimpleOverlayRenderer STEEL_GT;
    public static SimpleOverlayRenderer WHITE_GT;
    public static SimpleOverlayRenderer STAINLESS_STEEL_GT;


    public static void preInit() {
        // Simple Machines


        //From MC
        MC_BRICK = new SimpleOverlayRenderer("mc_brick");

        //Bricks
        BRICK_GRAY = new SimpleOverlayRenderer("brick_gray");

        // Casings
        MACERATOR_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/macerator_casing");
        BLAST_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/blast_casing");
        ASSEMBLING_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/assembler_casing");
        STRESS_PROOF_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/stress_proof_casing");
        CORROSION_PROOF_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/corrosion_proof_casing");
        VIBRATION_SAFE_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/vibration_safe_casing");
        WATERTIGHT_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/watertight_casing");
        CUTTER_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/cutter_casing");
        NONCONDUCTING_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/nonconducting_casing");
        MIXER_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/mixer_casing");
        ENGRAVER_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/engraver_casing");
        ATOMIC_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/atomic_casing");
        STEAM_CASING = new SimpleOverlayRenderer("casings/large_multiblock_casing/steam_casing");

        //GT6 Style
        STEEL_GT = new SimpleOverlayRenderer("steel_gt");
        WHITE_GT = new SimpleOverlayRenderer("white_gt");
        STAINLESS_STEEL_GT = new SimpleOverlayRenderer("stainless_steel_gt");
    }
}
