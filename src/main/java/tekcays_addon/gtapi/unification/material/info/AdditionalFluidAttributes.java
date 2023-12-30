package tekcays_addon.gtapi.unification.material.info;

import static gregtech.api.util.GTUtility.gregtechId;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidRegistry;

import gregtech.api.fluids.GTFluid;
import gregtech.api.fluids.attribute.FluidAttribute;
import gregtech.api.unification.material.info.MaterialFlag;
import tekcays_addon.api.fluids.FluidProperties;

public class AdditionalFluidAttributes {

    public static FluidAttribute TOXIC_ATTRIBUTE = build(FluidProperties.TOXIC);
    public static FluidAttribute FLAMMABLE_ATTRIBUTE = build(FluidProperties.FLAMMABLE);

    private static FluidAttribute build(FluidProperties fluidProperties) {
        return new FluidAttribute(gregtechId(fluidProperties.getName()),
                list -> list.add(I18n.format("gregtech.fluid.type_" + fluidProperties.getName() + ".tooltip")),
                list -> list.add(I18n.format("gregtech.fluid_pipe.acid_proof")));
    }

    public static void addFluidAttributeToMaterials(FluidAttribute fluidAttribute, MaterialFlag flag) {
        FluidRegistry.getBucketFluids().stream()
                .filter(fluid -> fluid instanceof GTFluid.GTMaterialFluid)
                .map(fluid -> (GTFluid.GTMaterialFluid) fluid)
                .filter(gtMaterialFluid -> gtMaterialFluid.getMaterial().hasFlag(flag))
                .forEach(gtMaterialFluid -> gtMaterialFluid.addAttribute(fluidAttribute));
    }
}
