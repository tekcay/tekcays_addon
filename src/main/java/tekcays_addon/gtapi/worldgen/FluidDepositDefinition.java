package tekcays_addon.gtapi.worldgen;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.IWorldgenDefinition;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.WorldConfigUtils;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

import static tekcays_addon.gtapi.consts.DepositValues.*;

@Getter
@ToString
public class FluidDepositDefinition implements IWorldgenDefinition {

    private static final String NAME = "name";

    private final String depositName;
    private int weight; // weight value for determining which vein will appear
    private String assignedName; // vein name for JEI display
    private String description; // vein description for JEI display
    private int minDepth;
    private int maxDepth;
    private int maxFluidAmount;
    private int minFluidAmount;
    private Fluid fluid; // the fluid which the vein contains
    private Predicate<WorldProvider> dimensionFilter = OreDepositDefinition.PREDICATE_SURFACE_WORLD; // filtering of dimensions

    public FluidDepositDefinition(String depositName) {
        this.depositName = depositName;
    }
    private static int getIntFromConfig(@Nonnull JsonObject configRoot, String configField) {
        TKCYALog.logger.info(configField + configRoot.get(configField).getAsJsonObject().get(configField).getAsInt());
        return Math.max(0, Math.min(100, configRoot.get(configField).getAsJsonObject().get(configField).getAsInt()));
    }

    @Override
    public boolean initializeFromConfig(@Nonnull JsonObject configRoot) {
        // the weight value for determining which vein will appear
        this.weight = getIntFromConfig(configRoot, WEIGHT);
        this.minFluidAmount = getIntFromConfig(configRoot, MIN_FLUID_AMOUNT);
        this.maxFluidAmount = getIntFromConfig(configRoot, MAX_FLUID_AMOUNT);
        this.minDepth = getIntFromConfig(configRoot, MIN_DEPTH);
        this.maxDepth = getIntFromConfig(configRoot, MAX_DEPTH);

        // the fluid which the vein contains
        Fluid fluid = FluidRegistry.getFluid(configRoot.get(FLUID).getAsString());
        if (fluid != null) {
            this.fluid = fluid;
        } else {
            TKCYALog.logger.error("Fluid Deposit Vein {} cannot have a null fluid!", this.depositName, new RuntimeException());
            return false;
        }
        // vein name for JEI display
        if (configRoot.has(NAME)) {
            this.assignedName = configRoot.get(NAME).getAsString();
        }
        // vein description for JEI display
        if (configRoot.has("description")) {
            this.description = configRoot.get("description").getAsString();
        }

        // filtering of dimensions to determine where the vein can generate
        if (configRoot.has("dimension_filter")) {
            this.dimensionFilter = WorldConfigUtils.createWorldPredicate(configRoot.get("dimension_filter"));
        }
        FluidDepositHandler.addFluidDeposit(this);
        return true;
    }

    //This is the file name

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FluidDepositDefinition))
            return false;

        FluidDepositDefinition objDeposit = (FluidDepositDefinition) obj;
        if (this.weight != objDeposit.getWeight())
            return false;

        if (this.minFluidAmount != objDeposit.getMinFluidAmount())
            return false;

        if (this.maxFluidAmount != objDeposit.getMaxFluidAmount())
            return false;

        if (!this.fluid.equals(objDeposit.getFluid()))
            return false;

        if ((this.assignedName == null && objDeposit.getAssignedName() != null) ||
                (this.assignedName != null && objDeposit.getAssignedName() == null) ||
                (this.assignedName != null && objDeposit.getAssignedName() != null && !this.assignedName.equals(objDeposit.getAssignedName())))
            return false;

        if ((this.description == null && objDeposit.getDescription() != null) ||
                (this.description != null && objDeposit.getDescription() == null) ||
                (this.description != null && objDeposit.getDescription() != null && !this.description.equals(objDeposit.getDescription())))
            return false;

        if ((this.dimensionFilter == null && objDeposit.getDimensionFilter() != null) ||
                (this.dimensionFilter != null && objDeposit.getDimensionFilter() == null) ||
                (this.dimensionFilter != null && objDeposit.getDimensionFilter() != null && !this.dimensionFilter.equals(objDeposit.getDimensionFilter())))
            return false;

        return super.equals(obj);
    }
}
