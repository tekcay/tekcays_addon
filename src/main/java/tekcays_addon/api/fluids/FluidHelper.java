package tekcays_addon.api.fluids;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraftforge.fluids.FluidRegistry;

import gregtech.api.fluids.GTFluid;

public class FluidHelper {

    public static List<GTFluid.GTMaterialFluid> getAllGTMaterialFluids() {
        return FluidRegistry.getBucketFluids()
                .stream()
                .filter(fluid -> fluid instanceof GTFluid.GTMaterialFluid)
                .map(fluid -> (GTFluid.GTMaterialFluid) fluid)
                .collect(Collectors.toList());
    }
}
