package tekcays_addon.common.covers;

import gregtech.api.GTValues;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.common.covers.CoverBehaviors.registerBehavior;

public class Covers {

    public static void init() {
        TKCYALog.logger.info("Registering cover behaviors...");

        registerBehavior(150, new ResourceLocation(GTValues.MODID, "temperature_detector"), TKCYAMetaItems.COVER_TEMPERATURE_DETECTOR, CoverDetectorTemperature::new);

    }
}
