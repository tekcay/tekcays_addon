package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix;

import java.util.HashMap;
import java.util.Map;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static tekcays_addon.gtapi.consts.TKCYAValues.MOLD_MATERIALS;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.moldEmpty;


public class CastingRecipeHandler {

    public static final Map<OrePrefix, OrePrefix> MOLD_PRODUCTION = new HashMap<>();


    public static void init(){

        misc();
        moldShaped();

        /*
        MOLD_PRODUCTION.put(OrePrefix.ingot, TKCYAOrePrefix.moldIngot);
        MOLD_PRODUCTION.put(OrePrefix.plate, TKCYAOrePrefix.moldPlate);
        MOLD_PRODUCTION.put(OrePrefix.stick, TKCYAOrePrefix.moldStick);
        MOLD_PRODUCTION.put(OrePrefix.stickLong, TKCYAOrePrefix.moldStickLong);
        MOLD_PRODUCTION.put(OrePrefix.gear, TKCYAOrePrefix.moldGear);
        MOLD_PRODUCTION.put(OrePrefix.gearSmall, TKCYAOrePrefix.moldGearSmall);
        MOLD_PRODUCTION.put(OrePrefix.bolt, TKCYAOrePrefix.moldBolt);
        //MOLD_PRODUCTION.put(OrePrefix.ball, TKCYAOrePrefix.moldBall);
        //MOLD_PRODUCTION.put(OrePrefix.bot, TKCYAOrePrefix.moldCylinder);
        //MOLD_PRODUCTION.put(OrePrefix.casing, TKCYAOrePrefix.moldCasing);
        //MOLD_PRODUCTION.put(OrePrefix.rotor, TKCYAOrePrefix.moldRotor);
        MOLD_PRODUCTION.put(OrePrefix.ring, TKCYAOrePrefix.moldRing);
        MOLD_PRODUCTION.put(OrePrefix.block, TKCYAOrePrefix.moldBlock);



        MOLD_PRODUCTION.keySet().forEach(prefix ->
        prefix.addProcessingHandler(PropertyKey.INGOT, TKCYAPartsRecipeHandler::processCasting));


         */
    }

    private static void misc() {

        //Phenolic Circuit Board
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Wood)
                .fluidInputs(Materials.Glue.getFluid(50))
                .notConsumable(TKCYAOrePrefix.moldPlate, MarkerMaterials.Empty)
                .output(MetaItems.PHENOLIC_BOARD)
                .duration(30)
                .EUt(7)
                .buildAndRegister();

    }

    private static void moldShaped() {

        for (Material m : MOLD_MATERIALS) {

            ModHandler.addShapedRecipe("mold_empty" + m.getUnlocalizedName(), OreDictUnifier.get(moldEmpty, m),
                    "hf", "PP", "PP", 'P', new UnificationEntry(plate, m));

            ModHandler.addShapedRecipe("mold_ingot" + m.getUnlocalizedName(), OreDictUnifier.get(moldIngot, m),
                    "   ", " S ", " h ", 'S', new UnificationEntry(moldEmpty, m));

            ModHandler.addShapedRecipe("mold_bolt" + m.getUnlocalizedName(), OreDictUnifier.get(moldBolt, m),
                    "   ", " S ", "  h", 'S', new UnificationEntry(moldEmpty, m));

            ModHandler.addShapedRecipe("mold_gear" + m.getUnlocalizedName(), OreDictUnifier.get(moldGear, m),
                    "   ", " Sh", "   ", 'S', new UnificationEntry(moldEmpty, m));

            ModHandler.addShapedRecipe("mold_gear_small" + m.getUnlocalizedName(), OreDictUnifier.get(moldGearSmall, m),
                    "   ", " S ", "h  ", 'S', new UnificationEntry(moldEmpty, m));

            ModHandler.addShapedRecipe("mold_plate" + m.getUnlocalizedName(), OreDictUnifier.get(moldPlate, m),
                    " h ", " S ", "   ", 'S', new UnificationEntry(moldEmpty, m));

            ModHandler.addShapedRecipe("mold_ring" + m.getUnlocalizedName(), OreDictUnifier.get(moldRing, m),
                    " h ", " S ", " x ", 'S', new UnificationEntry(moldEmpty, m));

            /*
                        ModHandler.addShapedRecipe("mold_block" + m.getUnlocalizedName(), OreDictUnifier.get(moldBlock, m),
                    "   ", "hS ", "   ", 'S', new UnificationEntry(moldEmpty, m));
             */
        }
    }
    
}
