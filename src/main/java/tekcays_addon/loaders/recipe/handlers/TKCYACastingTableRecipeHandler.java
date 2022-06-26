package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import tekcays_addon.api.unification.material.ore.TKCYAOrePrefix;

import java.util.HashMap;
import java.util.Map;


public class TKCYACastingTableRecipeHandler {

    public static final Map<OrePrefix, OrePrefix> MOLD_PRODUCTION = new HashMap<>();


    public static void init(){

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
        MOLD_PRODUCTION.put(OrePrefix.rotor, TKCYAOrePrefix.moldRotor);
        MOLD_PRODUCTION.put(OrePrefix.ring, TKCYAOrePrefix.moldRing);



        MOLD_PRODUCTION.keySet().forEach(prefix ->
        prefix.addProcessingHandler(PropertyKey.INGOT, TKCYAPartsRecipeHandler::processCasting));



    }
    
}
