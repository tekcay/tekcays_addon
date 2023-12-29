package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static tekcays_addon.api.consts.ComponentsLists.*;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_EV;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_HV;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_IV;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_LV;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_LuV;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_MV;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_UV;
import static tekcays_addon.common.items.TKCYAMetaItems.CONVEYOR_MODULE_ZPM;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_EV;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_HV;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_IV;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_LV;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_LuV;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_MV;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_UV;
import static tekcays_addon.common.items.TKCYAMetaItems.ELECTRIC_PUMP_ZPM;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_EV;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_HV;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_IV;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_LUV;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_LV;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_MV;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_UV;
import static tekcays_addon.common.items.TKCYAMetaItems.FLUID_REGULATOR_ZPM;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_EV;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_HV;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_IV;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_LV;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_LuV;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_MV;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_UV;
import static tekcays_addon.common.items.TKCYAMetaItems.ROBOT_ARM_ZPM;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.NEW_ASSEMBLING;
import static tekcays_addon.loaders.recipe.handlers.GalvanizedSteel.*;

import java.util.Map;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import tekcays_addon.api.consts.ComponentsLists;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.loaders.recipe.removals.RecipesRemovalHandler;

public class ComponentsHandler {

    public static void init() {
        remove();
        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel)
            RecipesRemovalHandler.recipeMapRecipesRemoval(NEW_ASSEMBLING, ELECTRIC_PISTON_LV.getStackForm());

        final Map<String, Material> rubberMaterials = new Object2ObjectOpenHashMap<>();
        rubberMaterials.put("rubber", Rubber);
        rubberMaterials.put("silicone_rubber", SiliconeRubber);
        rubberMaterials.put("styrene_butadiene_rubber", StyreneButadieneRubber);

        for (Map.Entry<String, Material> materialEntry : rubberMaterials.entrySet()) {
            Material material = materialEntry.getValue();
            // String name = material.getKey();

            conveyors(material);
            pumps(material);

        }
        conveyors();
        pumps();
        motors();
        fluidRegulators();
        roboticArms();
        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) lvPistonGalavanizedRecipe();
    }

    public static void hideLvToIvLogisticComponents(IIngredientBlacklist ingredientBlacklist) {
        ComponentsLists.LV_TO_IV_LOGISTIC_COMPONENTS.forEach(ingredientBlacklist::addIngredientToBlacklist);
    }

    private static void pumps(Material material) {
        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Tin)
                .input(pipeNormalFluid, Bronze)
                .input(screw, Tin)
                .input(rotor, Tin)
                .input(ring, material, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 2))
                .inputs(ELECTRIC_MOTOR_LV.getStackForm())
                .outputs(ELECTRIC_PUMP_LV.getStackForm())
                .duration(100).EUt(VA[LV])
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Copper)
                .input(pipeNormalFluid, Steel)
                .input(screw, Bronze)
                .input(rotor, Bronze)
                .input(ring, material, 2)
                .inputs(ELECTRIC_MOTOR_MV.getStackForm())
                .fluidInputs(SolderingAlloy.getFluid(L))
                .outputs(ELECTRIC_PUMP_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Gold)
                .input(pipeNormalFluid, StainlessSteel)
                .input(screw, Steel)
                .input(rotor, Steel)
                .input(ring, material, 2)
                .inputs(ELECTRIC_MOTOR_HV.getStackForm())
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .outputs(ELECTRIC_PUMP_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Aluminium)
                .input(pipeNormalFluid, Titanium)
                .input(screw, StainlessSteel)
                .input(rotor, StainlessSteel)
                .input(ring, material, 2)
                .inputs(ELECTRIC_MOTOR_EV.getStackForm())
                .fluidInputs(SolderingAlloy.getFluid(L * 3))
                .outputs(ELECTRIC_PUMP_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        if (!material.equals(Rubber))
            NEW_ASSEMBLING.recipeBuilder()
                    .input(cableGtSingle, Tungsten)
                    .input(pipeNormalFluid, TungstenSteel)
                    .input(screw, TungstenSteel)
                    .input(rotor, TungstenSteel)
                    .input(ring, material, 2)
                    .inputs(ELECTRIC_MOTOR_IV.getStackForm())
                    .fluidInputs(SolderingAlloy.getFluid(L * 4))
                    .outputs(ELECTRIC_PUMP_IV.getStackForm())
                    .duration(100).EUt(VA[IV]).buildAndRegister();
    }

    private static void pumps() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ELECTRIC_MOTOR_LuV)
                .input(pipeSmallFluid, NiobiumTitanium)
                .input(plate, HSSS, 2)
                .input(screw, HSSS, 8)
                .input(ring, SiliconeRubber, 4)
                .input(rotor, HSSS)
                .input(cableGtSingle, NiobiumTitanium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .fluidInputs(Lubricant.getFluid(250))
                .output(ELECTRIC_PUMP_LuV)
                .scannerResearch(ELECTRIC_PUMP_IV.getStackForm())
                .duration(600).EUt(6000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ELECTRIC_MOTOR_ZPM)
                .input(pipeNormalFluid, Polybenzimidazole)
                .input(plate, Osmiridium, 2)
                .input(screw, Osmiridium, 8)
                .input(ring, SiliconeRubber, 8)
                .input(rotor, Osmiridium)
                .input(cableGtSingle, VanadiumGallium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .fluidInputs(Lubricant.getFluid(500))
                .output(ELECTRIC_PUMP_ZPM)
                .scannerResearch(b -> b
                        .researchStack(ELECTRIC_PUMP_LuV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(600).EUt(24000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ELECTRIC_MOTOR_UV)
                .input(pipeLargeFluid, Naquadah)
                .input(plate, Tritanium, 2)
                .input(screw, Tritanium, 8)
                .input(ring, SiliconeRubber, 16)
                .input(rotor, NaquadahAlloy)
                .input(cableGtSingle, YttriumBariumCuprate, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Lubricant.getFluid(1000))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .output(ELECTRIC_PUMP_UV)
                .stationResearch(b -> b
                        .researchStack(ELECTRIC_PUMP_ZPM.getStackForm())
                        .CWUt(32)
                        .EUt(VA[ZPM]))
                .duration(600).EUt(100000).buildAndRegister();
    }

    private static void conveyors(Material material) {
        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Tin)
                .inputs(ELECTRIC_MOTOR_LV.getStackForm(2))
                .input(plate, material, 6)
                .fluidInputs(SolderingAlloy.getFluid(72))
                .circuitMeta(1)
                .outputs(CONVEYOR_MODULE_LV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Copper)
                .inputs(ELECTRIC_MOTOR_MV.getStackForm(2))
                .input(plate, material, 6)
                .fluidInputs(SolderingAlloy.getFluid(72))
                .circuitMeta(1)
                .outputs(CONVEYOR_MODULE_MV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Gold)
                .inputs(ELECTRIC_MOTOR_HV.getStackForm(2))
                .input(plate, material, 6)
                .fluidInputs(SolderingAlloy.getFluid(72))
                .circuitMeta(1)
                .outputs(CONVEYOR_MODULE_HV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Aluminium)
                .inputs(ELECTRIC_MOTOR_EV.getStackForm(2))
                .input(plate, material, 6)
                .fluidInputs(SolderingAlloy.getFluid(72))
                .circuitMeta(1)
                .outputs(CONVEYOR_MODULE_EV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        if (!material.equals(Rubber))
            NEW_ASSEMBLING.recipeBuilder()
                    .input(cableGtSingle, Tungsten)
                    .inputs(ELECTRIC_MOTOR_IV.getStackForm(2))
                    .input(plate, material, 6)
                    .fluidInputs(SolderingAlloy.getFluid(72))
                    .circuitMeta(1)
                    .outputs(CONVEYOR_MODULE_IV.getStackForm())
                    .duration(100).EUt(VA[LV]).buildAndRegister();
    }

    private static void conveyors() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ELECTRIC_MOTOR_LuV, 2)
                .input(plate, HSSS, 2)
                .input(ring, HSSS, 4)
                .input(round, HSSS, 16)
                .input(screw, HSSS, 4)
                .input(cableGtSingle, NiobiumTitanium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .fluidInputs(Lubricant.getFluid(250))
                .fluidInputs(StyreneButadieneRubber.getFluid(L * 8))
                .output(CONVEYOR_MODULE_LuV)
                .scannerResearch(CONVEYOR_MODULE_IV.getStackForm())
                .duration(600).EUt(6000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ELECTRIC_MOTOR_ZPM, 2)
                .input(plate, Osmiridium, 2)
                .input(ring, Osmiridium, 4)
                .input(round, Osmiridium, 16)
                .input(screw, Osmiridium, 4)
                .input(cableGtSingle, VanadiumGallium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .fluidInputs(Lubricant.getFluid(500))
                .fluidInputs(StyreneButadieneRubber.getFluid(L * 16))
                .output(CONVEYOR_MODULE_ZPM)
                .scannerResearch(b -> b
                        .researchStack(CONVEYOR_MODULE_LuV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(600).EUt(24000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ELECTRIC_MOTOR_UV, 2)
                .input(plate, Tritanium, 2)
                .input(ring, Tritanium, 4)
                .input(round, Tritanium, 16)
                .input(screw, Tritanium, 4)
                .input(cableGtSingle, YttriumBariumCuprate, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Lubricant.getFluid(1000))
                .fluidInputs(StyreneButadieneRubber.getFluid(L * 24))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .output(CONVEYOR_MODULE_UV)
                .stationResearch(b -> b
                        .researchStack(CONVEYOR_MODULE_ZPM.getStackForm())
                        .CWUt(32)
                        .EUt(VA[ZPM]))
                .duration(600).EUt(100000).buildAndRegister();
    }

    private static void motors() {
        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            lvMotorGalavanizedRecipe();
        } else {

            NEW_ASSEMBLING.recipeBuilder()
                    .input(cableGtSingle, Tin, 2)
                    .input(stick, Steel, 2)
                    .input(stick, SteelMagnetic)
                    .input(wireGtSingle, Copper, 4)
                    .fluidInputs(SolderingAlloy.getFluid(L))
                    .outputs(ELECTRIC_MOTOR_LV.getStackForm())
                    .duration(100).EUt(VA[LV]).buildAndRegister();
        }

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Copper, 2)
                .input(stick, Aluminium, 2)
                .input(stick, SteelMagnetic)
                .input(wireGtDouble, Cupronickel, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .outputs(ELECTRIC_MOTOR_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtDouble, Silver, 2)
                .input(stick, StainlessSteel, 2)
                .input(stick, SteelMagnetic)
                .input(wireGtDouble, Electrum, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 3))
                .outputs(ELECTRIC_MOTOR_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtDouble, Aluminium, 2)
                .input(stick, Titanium, 2)
                .input(stick, NeodymiumMagnetic)
                .input(wireGtDouble, Kanthal, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .outputs(ELECTRIC_MOTOR_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtDouble, Tungsten, 2)
                .input(stick, TungstenSteel, 2)
                .input(stick, NeodymiumMagnetic)
                .input(wireGtDouble, Graphene, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 5))
                .outputs(ELECTRIC_MOTOR_IV.getStackForm())
                .duration(100).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, SamariumMagnetic)
                .input(stickLong, HSSS, 2)
                .input(ring, HSSS, 2)
                .input(round, HSSS, 4)
                .input(wireFine, Ruridit, 64)
                .input(cableGtSingle, NiobiumTitanium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(Lubricant.getFluid(250))
                .output(ELECTRIC_MOTOR_LuV)
                .scannerResearch(ELECTRIC_MOTOR_IV.getStackForm())
                .duration(600).EUt(6000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, SamariumMagnetic)
                .input(stickLong, Osmiridium, 4)
                .input(ring, Osmiridium, 4)
                .input(round, Osmiridium, 8)
                .input(wireFine, Europium, 64)
                .input(wireFine, Europium, 32)
                .input(cableGtSingle, VanadiumGallium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 7))
                .fluidInputs(Lubricant.getFluid(500))
                .output(ELECTRIC_MOTOR_ZPM)
                .scannerResearch(b -> b
                        .researchStack(ELECTRIC_MOTOR_LuV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(600).EUt(24000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, SamariumMagnetic)
                .input(stickLong, Tritanium, 4)
                .input(ring, Tritanium, 4)
                .input(round, Tritanium, 8)
                .input(wireFine, Americium, 64)
                .input(wireFine, Americium, 64)
                .input(cableGtSingle, YttriumBariumCuprate, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Lubricant.getFluid(1000))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .output(ELECTRIC_MOTOR_UV)
                .stationResearch(b -> b
                        .researchStack(ELECTRIC_MOTOR_ZPM.getStackForm())
                        .CWUt(32)
                        .EUt(VA[ZPM]))
                .duration(600).EUt(100000).buildAndRegister();
    }

    private static void fluidRegulators() {
        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_LV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.LV, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 2))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_LV.getStackForm())
                .EUt(VA[LV])
                .duration(400)
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_MV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.MV, 2)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_MV.getStackForm())
                .EUt(VA[MV])
                .duration(350)
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_HV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.HV, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 2))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_HV.getStackForm())
                .EUt(VA[HV])
                .duration(300)
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_EV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 4))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_EV.getStackForm())
                .EUt(VA[EV])
                .duration(250)
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_IV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 5))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_IV.getStackForm())
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_LuV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.LuV, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 6))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_LUV.getStackForm())
                .EUt(VA[LuV])
                .duration(150)
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_ZPM.getStackForm())
                .input(circuit, MarkerMaterials.Tier.ZPM, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 7))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_ZPM.getStackForm())
                .EUt(VA[ZPM])
                .duration(100)
                .buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_UV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.UV, 2)
                .fluidInputs(SolderingAlloy.getFluid(L / 8))
                .circuitMeta(1)
                .outputs(FLUID_REGULATOR_UV.getStackForm())
                .EUt(VA[UV])
                .duration(50)
                .buildAndRegister();
    }

    private static void roboticArms() {
        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            lvRobotArmGalvanizedRecipe();
        } else {

            NEW_ASSEMBLING.recipeBuilder()
                    .input(cableGtSingle, Tin, 3)
                    .input(stick, Steel, 2)
                    .inputs(ELECTRIC_MOTOR_LV.getStackForm(2))
                    .inputs(ELECTRIC_PISTON_LV.getStackForm())
                    .input(circuit, MarkerMaterials.Tier.LV)
                    .fluidInputs(SolderingAlloy.getFluid(L / 2))
                    .outputs(ROBOT_ARM_LV.getStackForm())
                    .duration(100).EUt(VA[LV]).buildAndRegister();
        }

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Copper, 3)
                .input(stick, Aluminium, 2)
                .inputs(ELECTRIC_MOTOR_MV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_MV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.MV)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .outputs(ROBOT_ARM_MV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Gold, 3)
                .input(stick, StainlessSteel, 2)
                .inputs(ELECTRIC_MOTOR_HV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_HV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.HV)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .outputs(ROBOT_ARM_HV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Aluminium, 3)
                .input(stick, Titanium, 2)
                .inputs(ELECTRIC_MOTOR_EV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_EV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.EV)
                .fluidInputs(SolderingAlloy.getFluid(L * 3))
                .outputs(ROBOT_ARM_EV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        NEW_ASSEMBLING.recipeBuilder()
                .input(cableGtSingle, Tungsten, 3)
                .input(stick, TungstenSteel, 2)
                .inputs(ELECTRIC_MOTOR_IV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_IV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.IV)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .outputs(ROBOT_ARM_IV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, HSSS, 4)
                .input(gear, HSSS)
                .input(gearSmall, HSSS, 3)
                .input(ELECTRIC_MOTOR_LuV, 2)
                .input(ELECTRIC_PISTON_LUV)
                .input(circuit, MarkerMaterials.Tier.LuV)
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .input(circuit, MarkerMaterials.Tier.EV, 4)
                .input(cableGtSingle, NiobiumTitanium, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Lubricant.getFluid(250))
                .output(ROBOT_ARM_LuV)
                .scannerResearch(ROBOT_ARM_IV.getStackForm())
                .duration(600).EUt(6000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, Osmiridium, 4)
                .input(gear, Osmiridium)
                .input(gearSmall, Osmiridium, 3)
                .input(ELECTRIC_MOTOR_ZPM, 2)
                .input(ELECTRIC_PISTON_ZPM)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .input(circuit, MarkerMaterials.Tier.LuV, 2)
                .input(circuit, MarkerMaterials.Tier.IV, 4)
                .input(cableGtSingle, VanadiumGallium, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Lubricant.getFluid(500))
                .output(ROBOT_ARM_ZPM)
                .scannerResearch(b -> b
                        .researchStack(ROBOT_ARM_LuV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(600).EUt(24000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, Tritanium, 4)
                .input(gear, Tritanium)
                .input(gearSmall, Tritanium, 3)
                .input(ELECTRIC_MOTOR_UV, 2)
                .input(ELECTRIC_PISTON_UV)
                .input(circuit, MarkerMaterials.Tier.UV)
                .input(circuit, MarkerMaterials.Tier.ZPM, 2)
                .input(circuit, MarkerMaterials.Tier.LuV, 4)
                .input(cableGtSingle, YttriumBariumCuprate, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 12))
                .fluidInputs(Lubricant.getFluid(1000))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .output(ROBOT_ARM_UV)
                .stationResearch(b -> b
                        .researchStack(ROBOT_ARM_ZPM.getStackForm())
                        .CWUt(32)
                        .EUt(VA[ZPM]))
                .duration(600).EUt(100000).buildAndRegister();
    }

    private static void remove() {
        LV_TO_IV_COMPONENTS_TO_REMOVE_RECIPES.forEach(ModHandler::removeRecipeByOutput);
        RecipesRemovalHandler.recipeMapRecipesRemoval(ASSEMBLER_RECIPES, LV_TO_IV_MOTORS);
        RecipesRemovalHandler.recipeMapRecipesRemoval(ASSEMBLER_RECIPES, LV_TO_IV_LOGISTIC_COMPONENTS);
        RecipesRemovalHandler.recipeMapRecipesRemoval(ASSEMBLY_LINE_RECIPES, LUV_TO_UV_LOGISTIC_COMPONENTS);
        RecipesRemovalHandler.recipeMapRecipesRemoval(RESEARCH_STATION_RECIPES, UV_LOGISTIC_COMPONENTS);
    }
}
