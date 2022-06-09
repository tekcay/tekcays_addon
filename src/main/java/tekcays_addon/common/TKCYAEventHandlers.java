package tekcays_addon.common;

import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.api.unification.material.properties.TKCYAPropertyAddition;
import tekcays_addon.TekCaysAddon;
import gregtech.api.GregTechAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TekCaysAddon.MODID)
public class TKCYAEventHandlers {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMaterials(GregTechAPI.MaterialEvent event) {
        TKCYAMaterials.init();
        TKCYAPropertyAddition.init();
    }

    @SubscribeEvent
    public static void registerMaterialsPost(GregTechAPI.PostMaterialEvent event) {

    }
}
