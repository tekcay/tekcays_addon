package tekcays_addon.api.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.unification.TKCYAMaterials;

import javax.annotation.Nullable;
import java.util.*;

import static tekcays_addon.api.utils.TKCYAValues.ELECTRIC_PUMPS;

public class MiscMethods {

    public static boolean isFuel(@Nullable FluidStack fluid) {
        if (fluid == null) return false;
        if (fluid.isFluidEqual(new FluidStack(TKCYAMaterials.Fuel.getFluid(), 1))) return true;

        return false;
    }

    /**
     * Checks if a {@code FluidStack} is made of a certain {@code Fluid}.
     * This can avoid certain {@code NullPointer} exceptions.
     * <br /><br />
     * @param fluidStack the {@code FluidStack} that is checked.
     * @param fluid the {@code Fluid} to compare to.
     * @return true if condition is met.
     */
    public static boolean isSameFluid(FluidStack fluidStack, Fluid fluid) {
        if (fluidStack == null) return false;
        if (fluidStack.isFluidEqual(new FluidStack(fluid, 1))) return true;

        return false;
    }



    public static Map<Integer, Integer> getPumpPressureMap() {
        Map<Integer, Integer> map = new HashMap<>();
        int tier = 0;

        for (MetaItem.MetaValueItem pump : ELECTRIC_PUMPS) {
            tier++;
            map.put(tier * 10, tier);
        }
        return map;
    }

    public static Material getMaterialFromUnlocalizedName(String unlocalizedName) {
        for (Material m : GregTechAPI.MATERIAL_REGISTRY) {
            if (m.getUnlocalizedName().equals(unlocalizedName)) return m;
        }
        return null;
    }


    public static List<MaterialStack> getMaterialStacksFromString(String formula) {

        List<String> compounds = Arrays.asList(formula.split(","));
        List<MaterialStack> list = new ArrayList<>();

        for (int i = 0; i < compounds.size(); i += 2) {
            list.add(new MaterialStack(getMaterialFromUnlocalizedName(compounds.get(i)), Integer.parseInt(compounds.get(i + 1))));
        }

        return list;
    }

    public static NBTTagCompound writeNBTtoDustMixture(List<MaterialStack> list) {
        NBTTagCompound nbt = new NBTTagCompound();
        final StringBuilder composition = new StringBuilder();

        list.forEach(ms -> composition.append(ms.material.getUnlocalizedName())
                                      .append(",")
                                      .append(ms.amount)
                                      .append(","));

        nbt.setString("Composition", composition.toString());
        return nbt;
    }



}
