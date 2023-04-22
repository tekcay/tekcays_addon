package tekcays_addon.api.detectors;

import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.Getter;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IContainerControl;


@Getter
public class ControllerDetectorWrapper extends DetectorWrapper {

    private final Capability<?> capability;
    private final Capability<IContainerControl> controllerCapability;
    private final SimpleOverlayRenderer textures;


    public ControllerDetectorWrapper(DetectorWrapper wrapper, SimpleOverlayRenderer textures) {
        this.unit = wrapper.getUnit();
        this.currentMeasureText = wrapper.getCurrentMeasureText();
        this.uiTitle = wrapper.uiTitle;
        this.capability = wrapper.getCapability();
        this.controllerCapability = TKCYATileCapabilities.CAPABILITY_CONTAINER_CONTROL;
        this.textures = textures;
    }
}
