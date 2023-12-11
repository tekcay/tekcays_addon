package tekcays_addon.api.detectors;

import lombok.*;
import net.minecraftforge.common.capabilities.Capability;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetectorWrapper<T> {

    protected Capability<T> capability;
    protected String uiTitle;
    protected String unit;
    protected String currentMeasureText;
}
