package tekcays_addon.api.detectors;

import gregtech.api.cover.CoverableView;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.Getter;
import net.minecraft.util.EnumFacing;

import java.util.function.BiFunction;

@Getter
public class CoverDetectorWrapper extends DetectorWrapper {

    private final BiFunction<CoverableView, EnumFacing, Integer> valueRetrievingFunction;
    private final SimpleOverlayRenderer textures;


    public CoverDetectorWrapper(DetectorWrapper wrapper, SimpleOverlayRenderer textures, BiFunction<CoverableView, EnumFacing, Integer> valueRetrievingFunction) {
        this.valueRetrievingFunction = valueRetrievingFunction;
        this.textures = textures;
        this.unit = wrapper.getUnit();
        this.currentMeasureText = wrapper.getCurrentMeasureText();
        this.uiTitle = wrapper.uiTitle;
        this.capability = wrapper.getCapability();

    }
}
