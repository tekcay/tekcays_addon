package tekcays_addon.api.fluids.fluidType;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.fluids.fluidType.FluidType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.tkcya.material.FluidTypes")
@ZenRegister
public class TKCYAFluidTypes {

    @ZenProperty
    public static final FluidType MOLTEN = new FluidTypeMolten("molten", "molten", null, "tkcya.fluid.molten");
}
