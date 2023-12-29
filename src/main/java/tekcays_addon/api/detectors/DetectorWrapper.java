package tekcays_addon.api.detectors;

import net.minecraftforge.common.capabilities.Capability;

import lombok.*;

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
