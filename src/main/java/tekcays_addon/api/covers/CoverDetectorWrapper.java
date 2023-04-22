package tekcays_addon.api.covers;

import gregtech.api.cover.ICoverable;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.function.BiFunction;

@Getter
@AllArgsConstructor
@Builder
public class CoverDetectorWrapper {

    private SimpleOverlayRenderer textures;
    private Capability<?> capability;
    private String uiTitle;
    private String unit;
    private String currentMeasureText;
    private BiFunction<ICoverable, EnumFacing, Integer> valueRetrievingFunction;
}
