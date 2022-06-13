package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;


import static tekcays_addon.api.unification.TKCYAMaterialFlagAddition.MOLD_MATERIALS;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.*;

public class ShapedCraftingRecipes{

    public static void molds() {

        for (Material m : MOLD_MATERIALS) {

            ModHandler.addShapedRecipe("mold_empty", OreDictUnifier.get(moldEmpty, m), "hf", "PP", "PP", 'P', new UnificationEntry(OrePrefix.plate, m));
            
            ModHandler.addShapedRecipe("mold_rotor", OreDictUnifier.get(moldEmpty, m), "  h", " S ", "   ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_gear_small", OreDictUnifier.get(moldGearSmall, m), "   ", "   ", "h S", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_cylinder", OreDictUnifier.get(moldCylinder, m), "  S", "   ", "  h", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_ball", OreDictUnifier.get(moldBall, m), "   ", " S ", "h  ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_ingot", OreDictUnifier.get(moldIngot, m), "   ", " S ", " h ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_bolt", OreDictUnifier.get(moldBolt, m), "   ", " S ", "  h", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_gear", OreDictUnifier.get(moldGear, m), "   ", " Sh", "   ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_plate", OreDictUnifier.get(moldPlate, m), " h ", " S ", "   ", 'S', OreDictUnifier.get(moldEmpty, m));
        
        }

    }

}
