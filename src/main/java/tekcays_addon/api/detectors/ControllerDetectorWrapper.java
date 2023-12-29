package tekcays_addon.api.detectors;

import net.minecraftforge.common.capabilities.Capability;

import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.Getter;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;

@Getter
public class ControllerDetectorWrapper extends DetectorWrapper {

    private final Capability<?> capability;
    private final Capability<IContainerDetector> controllerCapability;
    private final SimpleOverlayRenderer textures;

    public ControllerDetectorWrapper(DetectorWrapper wrapper, SimpleOverlayRenderer textures) {
        this.unit = wrapper.getUnit();
        this.currentMeasureText = wrapper.getCurrentMeasureText();
        this.uiTitle = wrapper.uiTitle;
        this.capability = wrapper.getCapability();
        this.controllerCapability = TKCYATileCapabilities.CAPABILITY_CONTAINER_DETECTOR;
        this.textures = textures;
    }
}
