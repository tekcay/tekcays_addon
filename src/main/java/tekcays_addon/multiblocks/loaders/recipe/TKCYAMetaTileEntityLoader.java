package tekcays_addon.multiblocks.loaders.recipe;

import tekcays_addon.multiblocks.common.block.TKCYAMetaBlocks;
import tekcays_addon.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.multiblocks.common.metatileentities.TKCYAMetaTileEntities;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.CraftingComponent;
import gregtech.loaders.recipe.MetaTileEntityLoader;

import static tekcays_addon.multiblocks.api.unification.TKCYAMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class TKCYAMetaTileEntityLoader {

    public static void init() {
        ModHandler.addShapedRecipe(true, "large_macerator", TKCYAMetaTileEntities.LARGE_MACERATOR.getStackForm(),
                "TCT", "PSP", "MWM",
                'T', new UnificationEntry(plate, TungstenCarbide),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
                'S', MetaTileEntities.MACERATOR[IV].getStackForm(),
                'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "alloy_blast_smelter", TKCYAMetaTileEntities.ALLOY_BLAST_SMELTER.getStackForm(),
                "TCT", "WSW", "TCT",
                'T', new UnificationEntry(plate, TantalumCarbide),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'S', MetaTileEntities.ALLOY_SMELTER[EV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Aluminium));

        ModHandler.addShapedRecipe(true, "large_arc_furnace", TKCYAMetaTileEntities.LARGE_ARC_FURNACE.getStackForm(),
                "WGW", "CSC", "TTT",
                'T', new UnificationEntry(plate, TantalumCarbide),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'G', new UnificationEntry(dust, Graphite),
                'S', MetaTileEntities.ARC_FURNACE[IV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_assembler", TKCYAMetaTileEntities.LARGE_ASSEMBLER.getStackForm(),
                "RWR", "CSC", "PWP",
                'R', MetaItems.ROBOT_ARM_IV.getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', MetaItems.CONVEYOR_MODULE_IV.getStackForm(),
                'S', MetaTileEntities.ASSEMBLER[IV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_autoclave", TKCYAMetaTileEntities.LARGE_AUTOCLAVE.getStackForm(),
                "ACA", "ASA", "PWP",
                'A', new UnificationEntry(plateDouble, HSLASteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
                'S', MetaTileEntities.AUTOCLAVE[IV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));


        ModHandler.addShapedRecipe(true, "large_bender", TKCYAMetaTileEntities.LARGE_BENDER.getStackForm(),
                "PWP", "BCS", "FWH",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
                'B', MetaTileEntities.BENDER[IV].getStackForm(),
                'S', MetaTileEntities.COMPRESSOR[IV].getStackForm(),
                'F', MetaTileEntities.FORMING_PRESS[IV].getStackForm(),
                'H', MetaTileEntities.FORGE_HAMMER[IV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_brewer", TKCYAMetaTileEntities.LARGE_BREWERY.getStackForm(),
                "SCS", "BFH", "PWP",
                'S', new UnificationEntry(spring, MolybdenumDisilicide),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
                'B', MetaTileEntities.BREWERY[IV].getStackForm(),
                'F', MetaTileEntities.FERMENTER[IV].getStackForm(),
                'H', MetaTileEntities.FLUID_HEATER[IV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_centrifuge", TKCYAMetaTileEntities.LARGE_CENTRIFUGE.getStackForm(),
                "HPH", "RCT", "MWM",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'H', new UnificationEntry(spring, MolybdenumDisilicide),
                'P', new UnificationEntry(pipeHugeFluid, StainlessSteel),
                'R', MetaTileEntities.CENTRIFUGE[IV].getStackForm(),
                'T', MetaTileEntities.THERMAL_CENTRIFUGE[IV].getStackForm(),
                'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_chemical_bath", TKCYAMetaTileEntities.LARGE_CHEMICAL_BATH.getStackForm(),
                "PGP", "BCO", "MWM",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'B', MetaTileEntities.CHEMICAL_BATH[IV].getStackForm(),
                'O', MetaTileEntities.ORE_WASHER[IV].getStackForm(),
                'G', MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.TEMPERED_GLASS),
                'P', MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
                'M', MetaItems.CONVEYOR_MODULE_IV.getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_extractor", TKCYAMetaTileEntities.LARGE_EXTRACTOR.getStackForm(),
                "PGP", "BCO", "MWM",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'B', MetaTileEntities.EXTRACTOR[IV].getStackForm(),
                'O', MetaTileEntities.CANNER[IV].getStackForm(),
                'G', MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.TEMPERED_GLASS),
                'P', MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
                'M', MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_cutter", TKCYAMetaTileEntities.LARGE_CUTTER.getStackForm(),
                "SPS", "BCO", "MWM",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'B', MetaTileEntities.CUTTER[IV].getStackForm(),
                'O', MetaTileEntities.LATHE[IV].getStackForm(),
                'S', new UnificationEntry(toolHeadBuzzSaw, TungstenCarbide),
                'P', MetaItems.CONVEYOR_MODULE_IV.getStackForm(),
                'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_distillery", TKCYAMetaTileEntities.LARGE_DISTILLERY.getStackForm(),
                "LCL", "PSP", "LCL",
                'L', new UnificationEntry(pipeLargeFluid, Iridium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
                'S', MetaTileEntities.DISTILLATION_TOWER.getStackForm());

        ModHandler.addShapedRecipe(true, "large_electrolyzer", TKCYAMetaTileEntities.LARGE_ELECTROLYZER.getStackForm(),
                "PCP", "LSL", "PWP",
                'L', new UnificationEntry(wireGtQuadruple, Osmium),
                'P', new UnificationEntry(plate, BlackSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'S', MetaTileEntities.ELECTROLYZER[IV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_polarizer", TKCYAMetaTileEntities.LARGE_POLARIZER.getStackForm(),
                "PSP", "BCO", "WSW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'B', MetaTileEntities.POLARIZER[IV].getStackForm(),
                'O', MetaTileEntities.ELECTROMAGNETIC_SEPARATOR[IV].getStackForm(),
                'S', new UnificationEntry(wireGtQuadruple, Osmium),
                'P', new UnificationEntry(plate, BlackSteel),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_extruder", TKCYAMetaTileEntities.LARGE_EXTRUDER.getStackForm(),
                "LCL", "PSP", "OWO",
                'L', new UnificationEntry(pipeLargeItem, Ultimet),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'S', MetaTileEntities.EXTRUDER[IV].getStackForm(),
                'P', MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
                'O', new UnificationEntry(spring, MolybdenumDisilicide),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_solidifier", TKCYAMetaTileEntities.LARGE_SOLIDIFIER.getStackForm(),
                "LCL", "PSP", "LWL",
                'L', new UnificationEntry(pipeNormalFluid, Polyethylene),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'S', MetaTileEntities.FLUID_SOLIDIFIER[IV].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_mixer", TKCYAMetaTileEntities.LARGE_MIXER.getStackForm(),
                "LCL", "RSR", "MWM",
                'L', new UnificationEntry(pipeNormalFluid, Polybenzimidazole),
                'R', new UnificationEntry(rotor, Iridium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'S', MetaTileEntities.MIXER[IV].getStackForm(),
                'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm(),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_packager", TKCYAMetaTileEntities.LARGE_PACKAGER.getStackForm(),
                "RCR", "PSP", "MPM",
                'P', new UnificationEntry(plate, HSLASteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'S', MetaTileEntities.PACKER[HV].getStackForm(),
                'R', MetaItems.ROBOT_ARM_HV.getStackForm(),
                'M', MetaItems.CONVEYOR_MODULE_HV.getStackForm());

        ModHandler.addShapedRecipe(true, "large_engraver", TKCYAMetaTileEntities.LARGE_ENGRAVER.getStackForm(),
                "ECE", "PSP", "DWD",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'S', MetaTileEntities.LASER_ENGRAVER[IV].getStackForm(),
                'E', MetaItems.EMITTER_IV.getStackForm(),
                'P', MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
                'D', new UnificationEntry(plateDense, TantalumCarbide),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_sifter", TKCYAMetaTileEntities.LARGE_SIFTER.getStackForm(),
                "ACA", "PSP", "AWA",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'S', MetaTileEntities.SIFTER[IV].getStackForm(),
                'P', MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
                'A', new UnificationEntry(plate, HSLASteel),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "large_wiremill", TKCYAMetaTileEntities.LARGE_WIREMILL.getStackForm(),
                "ACA", "RSR", "MWM",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'S', MetaTileEntities.WIREMILL[IV].getStackForm(),
                'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm(),
                'R', new UnificationEntry(spring, HSLASteel),
                'A', new UnificationEntry(plate, HSLASteel),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe(true, "electric_implosion_compressor", TKCYAMetaTileEntities.ELECTRIC_IMPLOSION_COMPRESSOR.getStackForm(),
                "PCP", "FSF", "PCP",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM),
                'S', MetaTileEntities.IMPLOSION_COMPRESSOR.getStackForm(),
                'P', MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
                'F', MetaItems.FIELD_GENERATOR_IV.getStackForm());

        // todo replication
//        ModHandler.addShapedRecipe(true, "large_mass_fabricator", TKCYAMetaTileEntities.LARGE_MASS_FABRICATOR.getStackForm(),
//                "FCF", "ESE", "FWF",
//                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
//                'S', MetaTileEntities.MASS_FABRICATOR[ZPM].getStackForm(), //todo mid tier configs
//                'F', MetaItems.FIELD_GENERATOR_ZPM.getStackForm(),
//                'E', MetaItems.EMITTER_ZPM.getStackForm(),
//                'W', new UnificationEntry(cableGtDouble, VanadiumGallium));

        // todo replication
//        ModHandler.addShapedRecipe(true, "large_replicator", TKCYAMetaTileEntities.LARGE_REPLICATOR.getStackForm(),
//                "FCF", "ESE", "FWF",
//                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
//                'S', MetaTileEntities.REPLICATOR[ZPM].getStackForm(), //todo mid tier configs
//                'F', MetaItems.FIELD_GENERATOR_ZPM.getStackForm(),
//                'E', MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL),
//                'W', new UnificationEntry(cableGtDouble, VanadiumGallium));

        ModHandler.addShapedRecipe(true, "mega_blast_furnace", TKCYAMetaTileEntities.MEGA_BLAST_FURNACE.getStackForm(),
                "PCP", "FSF", "DWD",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                'S', MetaTileEntities.ELECTRIC_BLAST_FURNACE.getStackForm(),
                'F', MetaItems.FIELD_GENERATOR_UV.getStackForm(),
                'P', new UnificationEntry(spring, Neutronium),
                'D', new UnificationEntry(plateDense, Neutronium),
                'W', new UnificationEntry(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate));

        ModHandler.addShapedRecipe(true, "mega_vacuum_freezer", TKCYAMetaTileEntities.MEGA_VACUUM_FREEZER.getStackForm(),
                "PCP", "FSF", "DWD",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                'S', MetaTileEntities.VACUUM_FREEZER.getStackForm(),
                'F', MetaItems.FIELD_GENERATOR_UV.getStackForm(),
                'P', new UnificationEntry(pipeNormalFluid, Neutronium),
                'D', new UnificationEntry(plateDense, Neutronium),
                'W', new UnificationEntry(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate));

        ModHandler.addShapedRecipe(true, "steam_engine", TKCYAMetaTileEntities.STEAM_ENGINE.getStackForm(),
                "FPF", "PCP", "SGS",
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEAM_CASING),
                'S', new UnificationEntry(gearSmall, Bronze),
                'G', new UnificationEntry(gear, Steel),
                'F', new UnificationEntry(pipeSmallFluid, Potin),
                'P', new UnificationEntry(plate, Brass));

        ModHandler.addShapedRecipe(true, "large_circuit_assembler", TKCYAMetaTileEntities.LARGE_CIRCUIT_ASSEMBLER.getStackForm(),
                "RER", "CSC", "WPW",
                'R', MetaItems.ROBOT_ARM_LuV.getStackForm(),
                'E', MetaItems.EMITTER_LuV.getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV),
                'P', MetaItems.CONVEYOR_MODULE_LuV.getStackForm(),
                'S', MetaTileEntities.CIRCUIT_ASSEMBLER[LuV].getStackForm(),
                'W', new UnificationEntry(cableGtSingle, NiobiumTitanium));

        // Parallel Hatches
        ModHandler.addShapedRecipe(true, "parallel_hatch_iv", TKCYAMetaTileEntities.PARALLEL_HATCH[0].getStackForm(),
                "SCE", "CHC", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.LuV),
                'H', MetaTileEntities.HULL[IV].getStackForm(),
                'S', MetaItems.SENSOR_IV.getStackForm(),
                'E', MetaItems.EMITTER_IV.getStackForm(),
                'W', new UnificationEntry(cableGtDouble, Platinum));

        ModHandler.addShapedRecipe(true, "parallel_hatch_luv", TKCYAMetaTileEntities.PARALLEL_HATCH[LuV - IV].getStackForm(),
                "SCE", "CHC", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM),
                'H', MetaTileEntities.HULL[LuV].getStackForm(),
                'S', MetaItems.SENSOR_LuV.getStackForm(),
                'E', MetaItems.EMITTER_LuV.getStackForm(),
                'W', new UnificationEntry(cableGtDouble, NiobiumTitanium));

        ModHandler.addShapedRecipe(true, "parallel_hatch_zpm", TKCYAMetaTileEntities.PARALLEL_HATCH[ZPM - IV].getStackForm(),
                "SCE", "CHC", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV),
                'H', MetaTileEntities.HULL[ZPM].getStackForm(),
                'S', MetaItems.SENSOR_ZPM.getStackForm(),
                'E', MetaItems.EMITTER_ZPM.getStackForm(),
                'W', new UnificationEntry(cableGtDouble, VanadiumGallium));

        ModHandler.addShapedRecipe(true, "parallel_hatch_uv", TKCYAMetaTileEntities.PARALLEL_HATCH[UV - IV].getStackForm(),
                "SCE", "CHC", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                'H', MetaTileEntities.HULL[UV].getStackForm(),
                'S', MetaItems.SENSOR_UV.getStackForm(),
                'E', MetaItems.EMITTER_UV.getStackForm(),
                'W', new UnificationEntry(cableGtDouble, YttriumBariumCuprate));

        // Tiered Hatches
        MetaTileEntityLoader.registerMachineRecipe(TKCYAMetaTileEntities.TIERED_HATCH, "PPP", "PCP", "PPP", 'P', CraftingComponent.PLATE, 'C', CraftingComponent.BETTER_CIRCUIT);
        if (!HT) {
            ModHandler.addShapedRecipe(true, "tkcya.machine.tiered_hatch.uhv", TKCYAMetaTileEntities.TIERED_HATCH[UHV].getStackForm(),
                    "PPP", "PCP", "PPP",
                    'P', CraftingComponent.PLATE.getIngredient(UHV),
                    'C', CraftingComponent.CIRCUIT.getIngredient(UHV));
        }

    }
}
