package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.api.unification.TKCYAMaterials;

import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.curvedPlate;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;

public class PressureHandler {

    public static void init() {
        ModHandler.addShapedRecipe(true, "pressure_controller_lv", PRESSURE_CONTROLLER.getStackForm(),
                "CPC", "SMS", "PRP",
                'P', new UnificationEntry(curvedPlate, Materials.Steel),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV),
                'M', MetaTileEntities.HULL[1].getStackForm(),
                'S', MetaItems.SENSOR_LV.getStackForm(),
                'R', new UnificationEntry(OrePrefix.stickLong, Materials.Steel));

        //TODO ULV components
        ModHandler.addShapedRecipe(true, "electric_compressor_ulv", PRESSURE_COMPRESSOR[0].getStackForm(),
                "CPC", "MHM", "SRS",
                'M', MetaItems.ELECTRIC_PISTON_LV,
                'P', new UnificationEntry(OrePrefix.spring, Materials.Steel),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV),
                'H', MetaTileEntities.HULL[1].getStackForm(),
                'S', MetaItems.SENSOR_LV.getStackForm(),
                'R', MetaItems.FLUID_CELL_LARGE_STEEL);

        ModHandler.addShapedRecipe(true, "electric_compressor_lv", PRESSURE_COMPRESSOR[1].getStackForm(),
                "CPC", "MHM", "SRS",
                'M', MetaItems.ELECTRIC_PISTON_LV,
                'P', new UnificationEntry(OrePrefix.spring, TKCYAMaterials.GalvanizedSteel),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV),
                'H', MetaTileEntities.HULL[1].getStackForm(),
                'S', MetaItems.SENSOR_LV.getStackForm(),
                'R', MetaItems.FLUID_CELL_LARGE_STEEL);

        ModHandler.addShapedRecipe(true, "electric_compressor_mv", PRESSURE_COMPRESSOR[2].getStackForm(),
                "CPC", "MHM", "SRS",
                'M', MetaItems.ELECTRIC_PISTON_MV,
                'P', new UnificationEntry(OrePrefix.spring, Materials.Aluminium),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV),
                'H', MetaTileEntities.HULL[2].getStackForm(),
                'S', MetaItems.SENSOR_MV.getStackForm(),
                'R', MetaItems.FLUID_CELL_LARGE_ALUMINIUM);

        ModHandler.addShapedRecipe(true, "electric_compressor_hv", PRESSURE_COMPRESSOR[3].getStackForm(),
                "CPC", "MHM", "SRS",
                'M', MetaItems.ELECTRIC_PISTON_HV,
                'P', new UnificationEntry(OrePrefix.spring, Materials.StainlessSteel),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'S', MetaItems.SENSOR_HV.getStackForm(),
                'R', MetaItems.FLUID_CELL_LARGE_STAINLESS_STEEL);

        ModHandler.addShapedRecipe(true, "electric_compressor_ev", PRESSURE_COMPRESSOR[4].getStackForm(),
                "CPC", "MHM", "SRS",
                'M', MetaItems.ELECTRIC_PISTON_EV,
                'P', new UnificationEntry(OrePrefix.spring, Materials.Titanium),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'S', MetaItems.SENSOR_EV.getStackForm(),
                'R', MetaItems.FLUID_CELL_LARGE_TITANIUM);
    }
}
