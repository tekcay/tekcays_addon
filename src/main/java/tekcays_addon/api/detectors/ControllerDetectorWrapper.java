package tekcays_addon.api.detectors;

import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.Getter;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;


@Getter
public class ControllerDetectorWrapper<T> extends DetectorWrapper<T> {

    private final Capability<T> capability;
    private final Capability<IContainerDetector> controllerCapability;
    private final SimpleOverlayRenderer textures;


    public ControllerDetectorWrapper(DetectorWrapper<T> wrapper, SimpleOverlayRenderer textures) {
        this.unit = wrapper.getUnit();
        this.currentMeasureText = wrapper.getCurrentMeasureText();
        this.uiTitle = wrapper.uiTitle;
        this.capability = wrapper.getCapability();
        this.controllerCapability = TKCYATileCapabilities.CAPABILITY_CONTAINER_DETECTOR;
        this.textures = textures;
    }
}
