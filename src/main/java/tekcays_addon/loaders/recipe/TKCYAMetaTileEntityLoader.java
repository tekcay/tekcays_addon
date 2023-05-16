package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.ModHandler;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.loaders.recipe.MetaTileEntityLoader;
import tekcays_addon.common.TKCYAConfigHolder;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.ModHandler.removeTieredRecipeByName;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.WOOD_WALL;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;
import static net.minecraft.init.Items.BRICK;

public class TKCYAMetaTileEntityLoader {

    public static void init() {
        if (TKCYAConfigHolder.miscOverhaul.enableFoilOverhaul) {
            MetaTileEntityLoader.registerMachineRecipe(CLUSTER_MILL,
                    "MMM", "CHC", "MMM",
                    'M', MOTOR, 'C', CIRCUIT, 'H', HULL);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            MetaTileEntityLoader.registerMachineRecipe(ELECTRIC_CASTING_TABLE,
                    "NPN", "CHC", "NPN",
                    'P', PUMP, 'N', PIPE_NORMAL, 'H', HULL, 'C', CIRCUIT);

            /*
            ModHandler.addShapedRecipe(true, "casting_table", CASTING_TABLE.getStackForm(),
                    "PPP", "PhP", "PPP", 'P', MetaItems.COKE_OVEN_BRICK);
            ModHandler.addShapedRecipe(true, "primitive_fermenter", PRIMITIVE_FERMENTER.getStackForm(),
                    " h ", " P ", " w ", 'P', MetaBlocks.STEAM_CASING.getItemVariant(WOOD_WALL));

             */
        }

        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            removeTieredRecipeByName("gregtech:gregtech.machine.polarizer.", LV, UV); //removed recipes for GTCEu polarizers
            MetaTileEntityLoader.registerMachineRecipe(ADVANCED_POLARIZER,
                    "ZSZ", "WMW", "ZSZ",
                    'M', HULL, 'S', STICK_ELECTROMAGNETIC, 'Z', COIL_ELECTRIC, 'W', CABLE);

            if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
                ModHandler.addShapedRecipe(true, "primitive_bath", PRIMITIVE_BATH.getStackForm(),
                        "BBB", "BhB", "BBB", 'B', BRICK);
            }

        }
    }

}
