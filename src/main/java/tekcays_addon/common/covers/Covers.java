package tekcays_addon.common.covers;

import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.gtapi.utils.TKCYALog;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.common.covers.CoverBehaviors.registerBehavior;

public class Covers {

    public static void init() {
        TKCYALog.logger.info("Registering cover behaviors...");

        registerBehavior(150, new ResourceLocation(TekCaysAddon.MODID, "temperature_detector"), TKCYAMetaItems.COVER_TEMPERATURE_DETECTOR, CoverDetectorTemperature::new);

    }
}
