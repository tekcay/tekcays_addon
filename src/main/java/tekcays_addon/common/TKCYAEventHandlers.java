package tekcays_addon.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.unification.material.event.MaterialEvent;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.gtapi.unification.material.ore.OreDictAdditions;
import tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefixAdditions;
import tekcays_addon.gtapi.unification.material.properties.TKCYAPropertyAddition;

@Mod.EventBusSubscriber(modid = TekCaysAddon.MODID)
public class TKCYAEventHandlers {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMaterials(MaterialEvent event) {
        TKCYAMetaBlocks.init();
        TKCYAMaterials.init();
        TKCYAPropertyAddition.init();
        TKCYAOrePrefixAdditions.miscInit();

        OreDictAdditions.misc();

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            TKCYAOrePrefixAdditions.moldsInit();
            TKCYAOrePrefixAdditions.moldsTooltip();
        }

        TKCYAOrePrefixAdditions.componentsInit();
    }
    /*
     * @SubscribeEvent(priority = EventPriority.LOW)
     * public static void registerMaterialsPost(PostMaterialEvent event) {
     * }
     */
}
