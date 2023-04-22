package tekcays_addon.api.detectors;

import lombok.*;
import net.minecraftforge.common.capabilities.Capability;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetectorWrapper {

    protected Capability<?> capability;
    protected String uiTitle;
    protected String unit;
    protected String currentMeasureText;
}
