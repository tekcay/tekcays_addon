package tekcays_addon.api.recipeproperties;

import lombok.Value;
import net.minecraftforge.fluids.Fluid;

@Value
public class FluidOutputTime {

    private Fluid outputFluid;
    private int outputTime;

    public boolean isNotValid() {
        return this.outputFluid == null || outputTime <= 0;
    }

}
